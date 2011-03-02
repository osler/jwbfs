package jwbfs.model;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;

import jwbfs.model.beans.CopyBean;
import jwbfs.model.beans.DiskBean;
import jwbfs.model.beans.GameBean;
import jwbfs.model.beans.SettingsBean;
import jwbfs.model.utils.CoreConstants;

public class ModelStore {
	
	private static GameBean exportBean = new GameBean();
	private static GameBean selectedGame = new GameBean();
	
	private static SettingsBean settingsBean = new SettingsBean();
	
	private static int numDisk;
	
	public static void setDisks(LinkedHashMap<String, DiskBean> disks) {
		ModelStore.disks = disks;
	}

	private static LinkedHashMap<String,DiskBean> disks = new LinkedHashMap<String, DiskBean>();
	
	
	public ModelStore(){
		numDisk = System.getProperty("wbfs.disks").trim().equals("")?1:Integer.parseInt(System.getProperty("wbfs.disks"));
		initDisks();
	}
	
	private void initDisks() {
		disks.put(CoreConstants.VIEW_DISK_1_ID,
				new DiskBean(CoreConstants.VIEW_DISK_1_ID,
						"configs"+File.separatorChar+"disk1.ini"));
		disks.put(CoreConstants.VIEW_DISK_2_ID,
				new DiskBean(CoreConstants.VIEW_DISK_2_ID,
						"configs"+File.separatorChar+"disk2.ini"));
		disks.put(CoreConstants.VIEW_DISK_3_ID,
				new DiskBean(CoreConstants.VIEW_DISK_3_ID,
						"configs"+File.separatorChar+"disk3.ini"));
		disks.put(CoreConstants.VIEW_DISK_4_ID,
				new DiskBean(CoreConstants.VIEW_DISK_4_ID,
						"configs"+File.separatorChar+"disk4.ini"));
		disks.put(CoreConstants.VIEW_DISK_5_ID,
				new DiskBean(CoreConstants.VIEW_DISK_5_ID,
						"configs"+File.separatorChar+"disk5.ini"));
		disks.put(CoreConstants.VIEW_DISK_6_ID,
				new DiskBean(CoreConstants.VIEW_DISK_6_ID,
						"configs"+File.separatorChar+"disk6.ini"));
	}
	

	public static synchronized void setSelectedGame(GameBean selectedGame) {
		ModelStore.selectedGame = selectedGame;
	}


	public static synchronized GameBean getSelectedGame() {
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
	public static DiskBean getDisk(String diskID) {
		return disks.get(diskID);
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

	protected static CopyBean copyBean;

	public static void setCopyBean(CopyBean copyBean) {
		ModelStore.copyBean =copyBean; 
	}
	
	public static CopyBean getCopyBean() {
		if(copyBean == null){
			copyBean = new CopyBean();
		}
		return copyBean;
	}

	
	private static String activeDiskID;
	
	public static String getActiveDiskID() {
		// TODO Auto-generated method stub
		return activeDiskID;
	}
	public static void setActiveDiskID(String diskID) {
		ModelStore.activeDiskID = diskID;
	}
}
