package jwbfs.rcp.views;


import java.util.LinkedHashMap;

import jwbfs.model.Model;
import jwbfs.model.beans.AbstractTab;
import jwbfs.rcp.views.tabs.ISOtoWBFSTabView;
import jwbfs.rcp.views.tabs.SettingsTabView;
import jwbfs.rcp.views.tabs.WBFStoISOView;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.ui.part.ViewPart;

public class MainView extends ViewPart {
	public static final String ID = "jwbfs.ui.view";

	private LinkedHashMap<Integer, AbstractTab> tabs = new LinkedHashMap<Integer, AbstractTab>();
	
	public void createPartControl(Composite parent) {
		
		Model model = new Model();
		
		parent = WidgetCreator.formatComposite(parent);
		
		TabFolder tabFolder = WidgetCreator.createTabFolder(parent);
		
		tabs.put(ISOtoWBFSTabView.INDEX, new ISOtoWBFSTabView(tabFolder));
		tabs.put(WBFStoISOView.INDEX, new WBFStoISOView(tabFolder));
		tabs.put(SettingsTabView.INDEX, new SettingsTabView(tabFolder));
			
	}



	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		
	}

	public LinkedHashMap<Integer,AbstractTab> getTabs(){
		
		return tabs;		
	}

	
}