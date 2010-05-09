package jwbfs.ui.views.folder;

import jwbfs.model.Model;
import jwbfs.model.beans.ProcessBean;
import jwbfs.ui.listeners.ConvertButtonListener;
import jwbfs.ui.listeners.FileDialogListener;
import jwbfs.ui.listeners.FolderDialogListener;
import jwbfs.ui.views.WidgetCreator;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

public class ProcessView extends ViewPart {
	
	public static final String ID = "ProcessView";
	
	private ProgressBar progressBar;
	protected ProcessBean bean = null;
	

	public ProcessView() {
		bean = (ProcessBean) getTabBean() ;
	}
	public ProgressBar getProgressBar() {
		
		return progressBar;
	}	
	
	
	private ProcessBean getTabBean() {
		return (ProcessBean) Model.getTabs().get(ProcessBean.INDEX);
	}

	@Override
	public void createPartControl(Composite parent) {

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

}
