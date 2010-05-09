package jwbfs.ui.views.folder;

import jwbfs.model.Model;
import jwbfs.model.SettingsTabConstants;
import jwbfs.model.beans.SettingsBean;
import jwbfs.ui.views.WidgetCreator;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
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
		Combo combo =  WidgetCreator.createCombo(group, SettingsTabConstants.SPLITSIZE_Text, (SettingsBean) getTabBean(), "splitSize");
		label = WidgetCreator.createLabel(group,"Copy Partitions");
		combo =  WidgetCreator.createCombo(group, SettingsTabConstants.COPY_PARTITIONS_Text, (SettingsBean) getTabBean(), "copyPartitions");
	
		group = WidgetCreator.createGroup(composite, "TXT file Creation");
		label = WidgetCreator.createLabel(group,"Enable txt file creation");
		combo =  WidgetCreator.createCombo(group, SettingsTabConstants.ENABLE_TXT_CREATION_Text, (SettingsBean) getTabBean(), "enableTXT");
		label = WidgetCreator.createLabel(group,"txt file layout");
		combo =  WidgetCreator.createCombo(group, SettingsTabConstants.TXT_LAYOUT_Text, (SettingsBean) getTabBean(), "txtLayout");
		
	}

	private SettingsBean getTabBean() {
		return (SettingsBean) Model.getTabs().get(SettingsBean.INDEX);
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		
	}
	
		
}
