package jwbfs.rcp.views.tabs;

import java.beans.PropertyChangeEvent;
import java.io.File;

import jwbfs.model.SettingsTabConstants;
import jwbfs.model.beans.SettingsTab;
import jwbfs.rcp.utils.Utils;
import jwbfs.rcp.views.WidgetCreator;
import jwbfs.ui.interfaces.ITabView;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

public class SettingsTabView extends SettingsTab implements ITabView{

	protected TabFolder parent;
	protected TabItem tabItem;
	
	public SettingsTabView(TabFolder parent) {
		this.parent = parent;
		tabItem = WidgetCreator.createTabItem(parent,"Settings");
		tabItem.setControl(createView());

	}
	
	
	public Control createView() {

		Composite composite = WidgetCreator.createComposite(parent);
		
		Group splitGrp = WidgetCreator.createGroup(composite, "Partitions");
		Label fileLbl = WidgetCreator.createLabel(splitGrp,"Split Size");
		Combo combo =  WidgetCreator.createCombo(splitGrp, SettingsTabConstants.SPLITSIZE_Text, (SettingsTab) getTabBean(), "splitSize");
		fileLbl = WidgetCreator.createLabel(splitGrp,"Copy Partitions");
		combo =  WidgetCreator.createCombo(splitGrp, SettingsTabConstants.COPY_PARTITIONS_Text, (SettingsTab) getTabBean(), "copyPartitions");

		Group txtGroup = WidgetCreator.createGroup(composite, "TXT file Creation");
		fileLbl = WidgetCreator.createLabel(txtGroup,"Enable txt file creation");
		combo =  WidgetCreator.createCombo(txtGroup, SettingsTabConstants.ENABLE_TXT_CREATION_Text, (SettingsTab) getTabBean(), "enableTXT");
		fileLbl = WidgetCreator.createLabel(txtGroup,"txt file layout");
		combo =  WidgetCreator.createCombo(txtGroup, SettingsTabConstants.TXT_LAYOUT_Text, (SettingsTab) getTabBean(), "txtLayout");
		

		return composite;

	}
		
	public TabFolder getParent() {
		return parent;
	}

	public TabItem getTabItem() {
		return tabItem;
	}

		
}
