package jwbfs.ui.views.tabs;

import jwbfs.model.SettingsTabConstants;
import jwbfs.model.beans.SettingsTab;
import jwbfs.ui.interfaces.ITabView;
import jwbfs.ui.views.WidgetCreator;

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
		
		Group group = WidgetCreator.createGroup(composite, "Partitions");
		@SuppressWarnings("unused")
		Label label = WidgetCreator.createLabel(group,"Split Size");
		@SuppressWarnings("unused")
		Combo combo =  WidgetCreator.createCombo(group, SettingsTabConstants.SPLITSIZE_Text, (SettingsTab) getTabBean(), "splitSize");
		label = WidgetCreator.createLabel(group,"Copy Partitions");
		combo =  WidgetCreator.createCombo(group, SettingsTabConstants.COPY_PARTITIONS_Text, (SettingsTab) getTabBean(), "copyPartitions");
	
		group = WidgetCreator.createGroup(composite, "TXT file Creation");
		label = WidgetCreator.createLabel(group,"Enable txt file creation");
		combo =  WidgetCreator.createCombo(group, SettingsTabConstants.ENABLE_TXT_CREATION_Text, (SettingsTab) getTabBean(), "enableTXT");
		label = WidgetCreator.createLabel(group,"txt file layout");
		combo =  WidgetCreator.createCombo(group, SettingsTabConstants.TXT_LAYOUT_Text, (SettingsTab) getTabBean(), "txtLayout");
		
		return composite;

	}
		
	public TabFolder getParent() {
		return parent;
	}

	public TabItem getTabItem() {
		return tabItem;
	}

		
}
