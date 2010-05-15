package jwbfs.model;

import java.io.File;
import java.util.LinkedHashMap;

import jwbfs.model.beans.ModelObject;
import jwbfs.model.beans.ProcessBean;
import jwbfs.model.beans.SettingsBean;
import jwbfs.ui.utils.FileUtils;

public class Model {
	
	private ProcessBean isoToWbfsBean = new ProcessBean();
	private SettingsBean settingsBean = new SettingsBean();
	private static ProcessBean[] games = null;
	
	protected static LinkedHashMap<Integer, ModelObject> tabs = new LinkedHashMap<Integer, ModelObject>();
	
	public Model(){
		
		tabs.put(ProcessBean.INDEX, isoToWbfsBean);
		tabs.put(SettingsBean.INDEX, settingsBean);
		games = Model.listGames(settingsBean.getDiskPath());

	}
	
	public static LinkedHashMap<Integer, ModelObject> getTabs(){
		return tabs;
	}

	public static void setGames(ProcessBean[] games) {
		Model.games = games;
	}

	public static ProcessBean[] getGames() {
		return games;
	}

	
	public static ProcessBean[] listGames(String folder) {
		ProcessBean[] game = null;
		
		FileUtils.checkAndCreateFolder(folder);
		
		File f = new File(folder);
		File[] folderList = f.listFiles();
		
		game = new ProcessBean[folderList.length];
		
		for(int x = 0; x<folderList.length;x++){
			File f2 = folderList[x];
			if(f2.isDirectory()){
				File[] files = f2.listFiles();
				for(int fl = 0; fl < files.length;fl++){
					if(files[fl].getName().endsWith(".wbfs")){
						game[x] = new ProcessBean();
						game[x].setFilePath(files[fl].getAbsolutePath());
					
						System.out.println(game[x].getFilePath());
					}
				}
			}
		}
		
		return game;
	}
	
}
