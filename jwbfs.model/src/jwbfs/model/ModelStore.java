package jwbfs.model;

import java.util.LinkedHashMap;
import java.util.List;

import jwbfs.model.beans.DiskBean;
import jwbfs.model.beans.GameBean;
import jwbfs.model.beans.SettingsBean;
import jwbfs.model.utils.CoreConstants;

public class ModelStore {
	
	private static GameBean exportBean = new GameBean();
	private static GameBean selectedGame = new GameBean();
	
	private static SettingsBean settingsBean = new SettingsBean();
	
	private static int numDisk;
	
	private static LinkedHashMap<String,DiskBean> disks = new LinkedHashMap<String, DiskBean>();
	
	
	public ModelStore(){
		numDisk = System.getProperty("wbfs.disks").trim().equals("")?1:Integer.parseInt(System.getProperty("wbfs.disks"));
		initDisks();
	}
	
	private void initDisks() {
		disks.put(CoreConstants.VIEW_DISK_1_ID,
				new DiskBean(System.getProperty("wbfs.disk.path1")));
		disks.put(CoreConstants.VIEW_DISK_2_ID,
				new DiskBean(System.getProperty("wbfs.disk.path2")));
		disks.put(CoreConstants.VIEW_DISK_3_ID,
				new DiskBean(System.getProperty("wbfs.disk.path3")));
		disks.put(CoreConstants.VIEW_DISK_4_ID,
				new DiskBean(System.getProperty("wbfs.disk.path4")));
		disks.put(CoreConstants.VIEW_DISK_5_ID,
				new DiskBean(System.getProperty("wbfs.disk.path5")));
		disks.put(CoreConstants.VIEW_DISK_6_ID,
				new DiskBean(System.getProperty("wbfs.disk.path6")));
	}
	

	public static void setSelectedGame(GameBean selectedGame) {
		ModelStore.selectedGame = selectedGame;
	}


	public static GameBean getSelectedGame() {
		return selectedGame;
	}
	
	public static SettingsBean getSettingsBean() {
		return ModelStore.settingsBean;
	}
	
	public static GameBean getExportGameBean() {
		return exportBean;
	}

	public static void setExportGameBean(GameBean exportGame) {
		ModelStore.exportBean = exportGame;
	}
	
	public static void cleanSelectedGame() {
		setSelectedGame(new GameBean());
	}
	public static void cleanExportGame() {
		setExportGameBean(new GameBean());
	}
	
	//DISKS
	public static LinkedHashMap<String, DiskBean> getDisks() {
		return disks;
	}
	public static DiskBean getDisk(String diskKey) {
		return disks.get(diskKey);
	}

	public static void setDisk(String diskKey, DiskBean disk) {
		disks.put(diskKey, disk);
	}

	public static String getDiskPath(String diskKey) {
		return disks.get(diskKey).getDiskPath();
	}

	public static void setDiskPath(String diskKey, String diskPath) {
		disks.get(diskKey).setDiskPath(diskPath);
	}
	
	//Games
	public static void setGames(String diskKey, List<GameBean> games) {
		getDisk(diskKey).setGames(games);
	}

	public static List<GameBean> getGames(String diskKey) {
		return getDisk(diskKey).getGames();
	}

	public static void setNumDisk(int numDisk) {
		ModelStore.numDisk = numDisk;
	}

	public static int getNumDisk() {
		return numDisk;
	}
}
