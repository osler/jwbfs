package jwbfs.model.beans;

import java.util.ArrayList;
import java.util.List;

import jwbfs.model.utils.ConfigUtils;
import jwbfs.model.utils.PlatformUtils;

public class DiskBean extends ModelObject {

	private String diskPath;
	private List<GameBean> games = new ArrayList<GameBean>();
	
	private CoverSettings coverSettings;
	private String diskID;
	private String fileProps; 
	
	
	public DiskBean(String diskID, String fileProps){
		this.setDiskID(diskID);
		this.fileProps = fileProps;
		diskPath = ConfigUtils.getProperty("wbfs.disk.path", fileProps);
		
		coverSettings = new CoverSettings(diskID,fileProps);
	}
	
	public void setDiskPath(String diskPath) {
		propertyChangeSupport.firePropertyChange("diskPath", this.diskPath,
		this.diskPath = diskPath);
	}

	public String getDiskPath() {
		return diskPath;
	}
	
	
	public void setGames(List<GameBean> games) {
		this.games = games;
	}

	public List<GameBean> getGames() {
		return games;
	}
	
	public void setCoverSettings(CoverSettings coverSettings) {
		this.coverSettings = coverSettings;
	}

	public CoverSettings getCoverSettings() {
		if(coverSettings == null){
			coverSettings = new CoverSettings(diskID,fileProps);
		}
		return coverSettings;
	}

	public void setDiskID(String diskID) {
		propertyChangeSupport.firePropertyChange("diskID", this.diskID,
				this.diskID = diskID);
	}

	public String getDiskID() {
		return diskID;
	}
}
