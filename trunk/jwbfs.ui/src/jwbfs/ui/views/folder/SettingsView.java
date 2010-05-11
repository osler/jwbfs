package jwbfs.ui.views.folder;

import jwbfs.model.Constants;
import jwbfs.model.Model;
import jwbfs.model.beans.SettingsBean;
import jwbfs.ui.listeners.FolderCoverDialogListener;
import jwbfs.ui.listeners.FolderDialogListener;
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
		label = WidgetCreator.createLabel(group,"Enable txt file creation");
		combo =  WidgetCreator.createCombo(group, Constants.ENABLE_TXT_CREATION_Text, (SettingsBean) getTabBean(), "enableTXT");
		label = WidgetCreator.createLabel(group,"txt file layout");
		combo =  WidgetCreator.createCombo(group, Constants.TXT_LAYOUT_Text, (SettingsBean) getTabBean(), "txtLayout");
		
		
		group = WidgetCreator.createGroup(composite, "Cover Settings - (wiitdb)");
		label = WidgetCreator.createLabel(group,"Region");
		combo =  WidgetCreator.createCombo(group, Constants.REGIONS, (SettingsBean) getTabBean(), "region");
		label = WidgetCreator.createLabel(group,"Cover save Path");
		Button button = WidgetCreator.createButton(group,"open");
		addHandlerFolder(button);
		Text text =  WidgetCreator.createText(group, false, (SettingsBean) getTabBean(), "coverPath",2);
		
	}

	private SettingsBean getTabBean() {
		return (SettingsBean) Model.getTabs().get(SettingsBean.INDEX);
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		
	}

	private void addHandlerFolder(Button button) {
	
			button.addSelectionListener(new FolderCoverDialogListener(ID));
	}
	
		
}
