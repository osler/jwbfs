package jwbfs.ui.views.tabs;

import jwbfs.model.beans.ConvertTab;
import jwbfs.ui.exceptions.FileNotSelectedException;
import jwbfs.ui.exceptions.NotValidDiscException;
import jwbfs.ui.interfaces.ITabView;
import jwbfs.ui.utils.Utils;
import jwbfs.ui.views.WidgetCreator;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

public class ConvertTabView extends ConvertTab implements ITabView{
	
	protected TabFolder parent;
	protected TabItem tabItem;
	private ProgressBar progressBar;
	protected ConvertTab bean = null;

	public ConvertTabView(TabFolder parent) {	
		this.parent = parent;
		tabItem = WidgetCreator.createTabItem(parent,"WBFS Conversion");
		tabItem.setControl(createView());
		bean = (ConvertTab) getTabBean() ;
	}
	
	public Control createView() {

		Composite composite  = WidgetCreator.createComposite(parent);
		Group group = WidgetCreator.createGroup(composite, "Conversion Type");
		Button button = WidgetCreator.createRadio(group, "ISO to WBFS",getTabBean(),"isoToWbfs");
		button = WidgetCreator.createRadio(group, "WBFS to ISO",getTabBean(),"wbfsToIso");
		
		group = WidgetCreator.createGroup(composite, "File Selection");
		@SuppressWarnings("unused")
		Text text = WidgetCreator.createText(group,false,getTabBean(),"filePath");
		button = WidgetCreator.createButton(group, "open");
		addHandlerFileDialog(button);

		group = WidgetCreator.createGroup(composite, "Output Folder Selection");
		text = WidgetCreator.createText(group,false,getTabBean(),"folderPath");
		button = WidgetCreator.createButton(group,"open");
		addHandlerFolder(button);
		
		
		group = WidgetCreator.createGroup(composite, "Process");
		button = WidgetCreator.createButton(group,"convert");
		addHandlerConvert(button);
		
		group = WidgetCreator.createGroup(composite, "Game Info");
		text = WidgetCreator.createText(group,false,getTabBean(),"id");
		text = WidgetCreator.createText(group,false,getTabBean(),"title");
		text = WidgetCreator.createText(group,false,getTabBean(),"scrubGb");
				
		progressBar = WidgetCreator.createProgressBar(composite);
		
		return composite;

	}

	
	private void addHandlerConvert(Button button) {
		
		button.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
	
				try {
				
				if(bean.getFilePath() == null || bean.getFilePath().equals("")){
					 
					throw new FileNotSelectedException();
	
				}
				
				if(bean.getId().equals("not a wii disc")){
					throw new NotValidDiscException();

				}

				if(bean.isIsoToWbfs()){
					Utils.getHandlerService().executeCommand("jwbfs.ui.commands.toWbfs", null);
				}else{
					Utils.getHandlerService().executeCommand("jwbfs.ui.commands.toIso", null);

				}
				

				} catch (NotValidDiscException e1) {
			
				} catch (FileNotSelectedException e1) {
			
				} catch (Exception ex) {
					throw new RuntimeException("Command not Found");
				}
			}
		});

		
	}

	private void addHandlerFolder(Button button) {

			button.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
				public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {

					try {
						Utils.getHandlerService().executeCommand("jwbfs.ui.commands.folderDialog", null);
					} catch (ExecutionException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (NotDefinedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (NotEnabledException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (NotHandledException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

	
				}
			});
	}
	
	public void addHandlerFileDialog(Button button){

		button.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {

				try {
					Utils.getHandlerService().executeCommand("jwbfs.ui.commands.fileDialog", null);
				} catch (ExecutionException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NotDefinedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NotEnabledException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NotHandledException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
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
