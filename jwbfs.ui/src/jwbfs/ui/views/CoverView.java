package jwbfs.ui.views;


import jwbfs.model.Constants;
import jwbfs.model.Model;
import jwbfs.model.beans.ProcessBean;
import jwbfs.model.beans.SettingsBean;
import jwbfs.ui.listeners.FolderCoverDialogListener;
import jwbfs.ui.listeners.UpdateCoverListener;
import jwbfs.ui.utils.GuiUtils;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

public class CoverView extends ViewPart {

	public static final String ID = "CoverView";
	protected ProcessBean processBean = null;
	protected SettingsBean settingsBean = null;
	protected Button imageButton ;
	protected ProgressBar progressBar;
	
	
	public CoverView() {
		processBean = (ProcessBean) getProcessBean() ;
		settingsBean = (SettingsBean) getSettingsBean() ;

	}

	private SettingsBean getSettingsBean() {
		return (SettingsBean) Model.getTabs().get(SettingsBean.INDEX);
	}

	private ProcessBean getProcessBean() {
		return (ProcessBean) Model.getTabs().get(ProcessBean.INDEX);
	}

	@Override
	public void createPartControl(Composite parent) {
		
		parent = WidgetCreator.formatComposite(parent);

		Group group = WidgetCreator.createGroup(parent, "Cover Settings - (wiitdb)");
		
		Button button = WidgetCreator.createCheck(group, "3D Cover Download", getSettingsBean(), "cover3D");
		button = WidgetCreator.createCheck(group, "Disc Cover Download", getSettingsBean(), "coverDiscs");
		
		Label label = WidgetCreator.createLabel(group,"Cover save Path");
		button = WidgetCreator.createButton(group,"open");
		addHandlerFolder(button);
		Text text =  WidgetCreator.createText(group, false, (SettingsBean) getSettingsBean(), "coverPath",2);
		
		group =  WidgetCreator.createGroup(parent, "Cover",1);
		
		Image image = new Image( GuiUtils.getDisplay(),Constants.NOIMAGE);
		imageButton = WidgetCreator.createImage(group);
		imageButton.setImage(image);
		addHandlerUpdate(imageButton);
		progressBar = WidgetCreator.createProgressBar(parent);
		
		
	}

	private void addHandlerFolder(Button button) {
		
		button.addSelectionListener(new FolderCoverDialogListener(ID));
}
	
	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	private void addHandlerUpdate(Button button) {
		
		button.addSelectionListener(new UpdateCoverListener(ID,processBean));
	
		
	}

	public Button getImageButton() {
		return imageButton;
	}

	public void setImageButton(Button imageButton) {
		this.imageButton = imageButton;
	}

	public ProgressBar getProgressBar() {
		return progressBar;
	}

	public void setProgressBar(ProgressBar progressBar) {
		this.progressBar = progressBar;
	}

}
