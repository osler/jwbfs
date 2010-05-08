package jwbfs.model;

import java.util.LinkedHashMap;

import jwbfs.model.beans.AbstractTab;
import jwbfs.model.beans.ConvertTab;
import jwbfs.model.beans.SettingsTab;

public class Model {
	
	private ConvertTab isoToWbfsBean = new ConvertTab();
	private SettingsTab settingsBean = new SettingsTab();
	
	protected static LinkedHashMap<Integer, AbstractTab> tabs = new LinkedHashMap<Integer, AbstractTab>();
	
	public Model(){
		
		tabs.put(ConvertTab.INDEX, isoToWbfsBean);
		tabs.put(SettingsTab.INDEX, settingsBean);
		
	}
	
	public static LinkedHashMap<Integer, AbstractTab> getTabs(){
		return tabs;
	}
	
}
