package jwbfs.model;

import java.util.LinkedHashMap;

import jwbfs.model.beans.AbstractTab;
import jwbfs.model.beans.ProcessBean;
import jwbfs.model.beans.SettingsBean;

public class Model {
	
	private ProcessBean isoToWbfsBean = new ProcessBean();
	private SettingsBean settingsBean = new SettingsBean();
	
	protected static LinkedHashMap<Integer, AbstractTab> tabs = new LinkedHashMap<Integer, AbstractTab>();
	
	public Model(){
		
		tabs.put(ProcessBean.INDEX, isoToWbfsBean);
		tabs.put(SettingsBean.INDEX, settingsBean);
		
	}
	
	public static LinkedHashMap<Integer, AbstractTab> getTabs(){
		return tabs;
	}
	
}
