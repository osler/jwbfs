package jwbfs.ui.views;


import java.util.LinkedHashMap;

import jwbfs.model.beans.ModelObject;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.ui.part.ViewPart;

public class MainView extends ViewPart {
	
	public static final String ID = "jwbfs.ui.ConvertView";

	private LinkedHashMap<Integer, ModelObject> tabs = new LinkedHashMap<Integer, ModelObject>();
	
	public void createPartControl(Composite parent) {
		
//		Model model = new Model();
		
		parent = WidgetCreator.formatComposite(parent);
		
		TabFolder tabFolder = WidgetCreator.createTabFolder(parent);
		
//		tabs.put(ConvertTabView.INDEX, new ConvertTabView(tabFolder));
//		tabs.put(SettingsView.INDEX, new SettingsView(tabFolder));
			
	}



	@Override
	public void setFocus() {
		
	}

	public LinkedHashMap<Integer,ModelObject> getTabs(){
		
		return tabs;		
	}

	
}