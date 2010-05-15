package jwbfs.ui.views.folder;

import jwbfs.model.Model;
import jwbfs.model.beans.GameBean;
import jwbfs.model.beans.SettingsBean;
import jwbfs.ui.listeners.ConvertButtonListener;
import jwbfs.ui.listeners.FileDialogListener;
import jwbfs.ui.listeners.FolderDialogListener;
import jwbfs.ui.views.WidgetCreator;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

public class ProcessView extends ViewPart {
	
	public static final String ID = "ProcessView";
	
	private ProgressBar progressBar;
	protected GameBean bean = null;
	

	public ProcessView() {
		bean = (GameBean) Model.getGameBean() ;
	}
	public ProgressBar getProgressBar() {
		
		return progressBar;
	}	
	

	@Override
	public void createPartControl(Composite parent) {

		Composite composite  = WidgetCreator.createComposite(parent);
		Group group = WidgetCreator.createGroup(composite, "Conversion Type");
		Button button = WidgetCreator.createRadio(group, "ISO to WBFS",Model.getGameBean(),"isoToWbfs");
		button = WidgetCreator.createRadio(group, "WBFS to ISO",Model.getGameBean(),"wbfsToIso");
		
		group = WidgetCreator.createGroup(composite, "File Selection");
		@SuppressWarnings("unused")
		Text text = WidgetCreator.createText(group,false,Model.getGameBean(),"filePath");
		button = WidgetCreator.createButton(group, "open");
		addHandlerFileDialog(button);

		group = WidgetCreator.createGroup(composite, "Output Folder Selection");
		text = WidgetCreator.createText(group,false,Model.getSettingsBean(),"folderPath");
		button = WidgetCreator.createButton(group,"open");
		addHandlerFolder(button);
		
		
		group = WidgetCreator.createGroup(composite, "Process");
		button = WidgetCreator.createButton(group,"convert");
		addHandlerConvert(button);
		
		group = WidgetCreator.createGroup(composite, "Game Info",4);
		text = WidgetCreator.createText(group,false,null,"id:");
		text = WidgetCreator.createText(group,false,Model.getGameBean(),"id");
		text = WidgetCreator.createText(group,false,null,"title:");
		text = WidgetCreator.createText(group,false,Model.getGameBean(),"title");
		text = WidgetCreator.createText(group,false,null,"scrubbed size:");
		text = WidgetCreator.createText(group,false,Model.getGameBean(),"scrubGb");
				
		progressBar = WidgetCreator.createProgressBar(composite);
		
	}

	@Override
	public void setFocus() {
		
	}

	private void addHandlerConvert(Button button) {
		
		button.addSelectionListener(new ConvertButtonListener(ID,bean));
	
		
	}

	private void addHandlerFolder(Button button) {
	
			button.addSelectionListener(new FolderDialogListener(ID));
	}

	public void addHandlerFileDialog(Button button){
	
		button.addSelectionListener(new FileDialogListener(ID));
	
	}
	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.ViewPart#init(org.eclipse.ui.IViewSite)
	 */
	@Override
	public void init(IViewSite site) throws PartInitException {
		super.init(site);
		
		if(((SettingsBean) Model.getBeans().get(SettingsBean.INDEX)).isManagerMode()){
			((SettingsBean) Model.getBeans().get(SettingsBean.INDEX)).setManagerMode(false);
		}
	}

}
