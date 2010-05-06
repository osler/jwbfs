package jwbfs.model;

import java.util.LinkedHashMap;

import jwbfs.model.beans.AbstractTab;
import jwbfs.model.beans.ISOtoWBFSTab;
import jwbfs.model.beans.SettingsTab;
import jwbfs.model.beans.WBFStoISOTab;

public class Model {
	
	private ISOtoWBFSTab isoToWbfsBean = new ISOtoWBFSTab();
	private WBFStoISOTab wbfsToIsoBean = new WBFStoISOTab();
	private SettingsTab settingsBean = new SettingsTab();
	
	protected static LinkedHashMap<Integer, AbstractTab> tabs = new LinkedHashMap<Integer, AbstractTab>();
	
	public Model(){
		
		tabs.put(ISOtoWBFSTab.INDEX, isoToWbfsBean);
		tabs.put(WBFStoISOTab.INDEX, wbfsToIsoBean);
		tabs.put(SettingsTab.INDEX, settingsBean);
		
	}
	
	public static LinkedHashMap<Integer, AbstractTab> getTabs(){
		return tabs;
	}
	
}
