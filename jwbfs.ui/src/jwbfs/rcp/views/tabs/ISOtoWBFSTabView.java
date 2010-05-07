package jwbfs.rcp.views.tabs;

import java.io.File;
import java.io.IOException;

import jwbfs.model.beans.ISOtoWBFSTab;
import jwbfs.rcp.controls.Exec;
import jwbfs.rcp.controls.FileNotSelectedException;
import jwbfs.rcp.controls.NotValidDiscException;
import jwbfs.rcp.controls.WBFSException;
import jwbfs.rcp.views.WidgetCreator;
import jwbfs.ui.interfaces.ITabView;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

public class ISOtoWBFSTabView extends ISOtoWBFSTab implements ITabView{
	
	protected TabFolder parent;
	protected TabItem tabItem;
	private ProgressBar progressBar;
	protected ISOtoWBFSTab bean = null;

	public ISOtoWBFSTabView(TabFolder parent) {	
		this.parent = parent;
		tabItem = WidgetCreator.createTabItem(parent,"Convert ISO to WBFS");
		tabItem.setControl(createView());
		bean = (ISOtoWBFSTab) getTabBean() ;
	}
	
	public Control createView() {

		Composite composite  = WidgetCreator.createComposite(parent);
		
		Group group = WidgetCreator.createGroup(composite, "File Selection");
		@SuppressWarnings("unused")
		Text text = WidgetCreator.createText(group,false,getTabBean(),"filePath");
		Button button = WidgetCreator.createButton(group, "open");
		addActionFile(button);

		group = WidgetCreator.createGroup(composite, "Output Folder Selection");
		text = WidgetCreator.createText(group,false,getTabBean(),"folderPath");
		button = WidgetCreator.createButton(group,"open");
		addActionFolder(button);
		
		
		group = WidgetCreator.createGroup(composite, "Process");
		button = WidgetCreator.createButton(group,"convert");
		addActionExec(button);
		
		group = WidgetCreator.createGroup(composite, "Game Info");
		text = WidgetCreator.createText(group,false,getTabBean(),"id");
		text = WidgetCreator.createText(group,false,getTabBean(),"title");
		text = WidgetCreator.createText(group,false,getTabBean(),"scrubGb");
				
		progressBar = WidgetCreator.createProgressBar(composite);
		
		return composite;

	}

	
	private void addActionExec(Button button1) {
		
		
		button1.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
	
				try {
				
				if(bean.getFilePath() == null || bean.getFilePath().equals("")){
					 
					throw new FileNotSelectedException();
	
				}
				
				if(bean.getId().equals("not a wii disc")){
						throw new NotValidDiscException();
			
				}
	
					Exec.convert(bean.getFilePath(),bean.getFolderPath(),false);
			
				} catch (NotValidDiscException e1) {
			
				} catch (FileNotSelectedException e1) {
			
				}

			}
		});

		
	}

	private void addActionFolder(Button button) {

			button.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
				public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
					DirectoryDialog d = new DirectoryDialog(new Shell());
					
					if(bean != null){
						String open = d.open();
						bean.setFolderPath(open);
					}
	
	
				}
			});
	}
	
	public void addActionFile(Button button){
		
		button.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				FileDialog d = new FileDialog(new Shell()) ;

					d.setFilterExtensions(new String[]{"*.iso","*.ISO"});

				
				if(bean != null){
					String line = d.open();
					bean.setFilePath(line);
					  try {
						  String[] info = Exec.checkIso(new File(line),false);
						bean.setId(info[0]);
						bean.setTitle(info[1]);
						bean.setScrubGb(info[2]);
						bean.setFilePath(line);
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (WBFSException e1) {
					}
				}
				
			}
		});
	
	}

	public ProgressBar getProgressBar() {
		
		return progressBar;
	}	
	
	public TabFolder getParent() {
		return parent;
	}

	public TabItem getTabItem() {
		return tabItem;
	}
	
}
