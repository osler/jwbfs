package jwbfs.ui.views.folder;

import jwbfs.model.Constants;
import jwbfs.model.CoverConstants;
import jwbfs.model.Model;
import jwbfs.model.beans.SettingsBean;
import jwbfs.ui.listeners.coverView.FolderCoverDialogListener;
import jwbfs.ui.listeners.settings.UpdateTitlesTXTListener;
import jwbfs.ui.views.WidgetCreator;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

public class SettingsView extends ViewPart{
	
	public static final String ID = "SettingsView";
	
	protected SettingsBean bean = null;
	
	public SettingsView() {
		bean = (SettingsBean) getTabBean() ;
	}

	@Override
	public void createPartControl(Composite parent) {

		Composite composite = WidgetCreator.createComposite(parent);
		
		Group group = WidgetCreator.createGroup(composite, "Partitions");
		@SuppressWarnings("unused")
		Label label = WidgetCreator.createLabel(group,"Split Size");
		@SuppressWarnings("unused")
		Combo combo =  WidgetCreator.createCombo(group, Constants.SPLITSIZE_Text, (SettingsBean) getTabBean(), "splitSize");
		label = WidgetCreator.createLabel(group,"Copy Partitions");
		combo =  WidgetCreator.createCombo(group, Constants.COPY_PARTITIONS_Text, (SettingsBean) getTabBean(), "copyPartitions");
	
		group = WidgetCreator.createGroup(composite, "TXT file Creation");
		label = WidgetCreator.createLabel(group,"");
		Button button = WidgetCreator.createCheck(group, "Enable txt file creation", getTabBean(), "enableTXT");
		label = WidgetCreator.createLabel(group,"txt file layout");
		combo =  WidgetCreator.createCombo(group, Constants.TXT_LAYOUT_Text, (SettingsBean) getTabBean(), "txtLayout");
		
		
		group = WidgetCreator.createGroup(composite, "Cover Settings",3);

		button = WidgetCreator.createCheck(group, "Enable Cover Download", getTabBean(), "automaticCoverDownload");
		button = WidgetCreator.createCheck(group, "3D Cover Download", getTabBean(), "cover3D");
		button = WidgetCreator.createCheck(group, "Disc Cover Download", getTabBean(), "coverDiscs");

		label = WidgetCreator.createLabel(group,"Region");
		combo =  WidgetCreator.createCombo(group, CoverConstants.REGIONS, (SettingsBean) getTabBean(), "region");
		label = WidgetCreator.createLabel(group,"");
		
		label = WidgetCreator.createLabel(group,"titles.TXT");
		button = WidgetCreator.createButton(group,"Update");
		addHandlerUpdateTXT(button);
		label = WidgetCreator.createLabel(group,"");
		
		label = WidgetCreator.createLabel(group,"Cover save Path",3);
		label = WidgetCreator.createLabel(group,"2D");
		Text text =  WidgetCreator.createText(group, false, (SettingsBean) getTabBean(), "coverPath2d");
		button = WidgetCreator.createButton(group,"open");	
		addCoverListener(button,"2d");
		
		label = WidgetCreator.createLabel(group,"3D");
		text =  WidgetCreator.createText(group, false, (SettingsBean) getTabBean(), "coverPath3d");
		button = WidgetCreator.createButton(group,"open");	
		addCoverListener(button,"3d");
		
		label = WidgetCreator.createLabel(group,"Disc");
		text =  WidgetCreator.createText(group, false, (SettingsBean) getTabBean(), "coverPathDisc");
		button = WidgetCreator.createButton(group,"open");	
		addCoverListener(button,"disc");
	}

	private void addCoverListener(Button button, String type) {
		button.addSelectionListener(new FolderCoverDialogListener(ID, type));
		
	}

	private void addHandlerUpdateTXT(Button button) {
		button.addSelectionListener(new UpdateTitlesTXTListener(ID));
		
	}

	private SettingsBean getTabBean() {
		return Model.getSettingsBean();
	}

	@Override
	public void setFocus() {
		
	}

			
}
