package jwbfs.ui.views;


import jwbfs.model.Constants;
import jwbfs.model.Model;
import jwbfs.model.beans.ProcessBean;
import jwbfs.model.beans.SettingsBean;
import jwbfs.ui.listeners.ConvertButtonListener;
import jwbfs.ui.listeners.UpdateCoverListener;
import jwbfs.ui.utils.GuiUtils;
import jwbfs.ui.utils.Utils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.ViewPart;

public class CoverView extends ViewPart {

	public static final String ID = "CoverView";
	protected ProcessBean processBean = null;
	protected SettingsBean settingsBean = null;
	protected Button imageButton ;
	
	
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

		Group group =  WidgetCreator.createGroup(parent, "Cover Controls",3);
		@SuppressWarnings("unused")
		Label text = WidgetCreator.createLabel(group, "last updated:");
		text = WidgetCreator.createLabel(group, "never");
		@SuppressWarnings("unused")
		Button button = WidgetCreator.createButton(group, "update");
		addHandlerUpdate(button);

		group =  WidgetCreator.createGroup(parent, "Cover",1);
		
		Image image = new Image( GuiUtils.getDisplay(),Constants.NOIMAGE);
		imageButton = WidgetCreator.createImage(group);
		imageButton.setImage(image);
		addHandlerUpdate(imageButton);
		
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

}
