package jwbfs.model.beans;

import java.util.LinkedHashMap;

import jwbfs.model.Constants;
import jwbfs.ui.utils.FileUtils;

public class SettingsBean extends ModelObject {
	
	public static final String INDEX = "settingsBean";
	
	//WBFS_FILE SETTINGS
	private String splitSize;
	private String copyPartitions;
	private boolean enableTXT;
	private String txtLayout;
	
	//WIITDB SETTINGS
	private String region;
	private String coverPath2d;
	private String coverPath3d;
	private String coverPathDisc;
	
	private boolean automaticCoverDownload;
	private boolean updateCover;
	private boolean cover3Denabled;
	private boolean coverDiscsEnabled;
	
	//MANAGER SETTINGS
	private String diskPath;
	private boolean managerMode = false;
	private String folderPath;
	
	public SettingsBean(){
		this.addPropertyChangeListener(this);
		
		splitSize = Constants.SPLITSIZE_Text[0];
		copyPartitions = Constants.COPY_PARTITIONS_Text[0];
		enableTXT = true;
		txtLayout = System.getProperty("wbfs.txt.layout");
		
		//WIITDB SETTINGS
		region = System.getProperty("cover.region");
		coverPath2d = FileUtils.exist(System.getProperty("cover.path.2d"),true,false,false);
		coverPath3d = FileUtils.exist(System.getProperty("cover.path.3d"),false,true,false);
		coverPathDisc = FileUtils.exist(System.getProperty("cover.path.disc"),false,false,true);
		
	
		automaticCoverDownload = true;
		cover3Denabled = true;
		coverDiscsEnabled = true;
		
		updateCover = false;

		//MANAGER
		diskPath =  FileUtils.exist(System.getProperty("wbfs.disk.path"));

	}
	
	public String getFolderPath() {
		if(isManagerMode()){
			return diskPath;
		}
		return folderPath;
	}

	public void setFolderPath(String folderPath) {

			propertyChangeSupport.firePropertyChange("folderPath", this.folderPath,
					this.folderPath = folderPath);
		
	}
	

	public void setRegion(String region) {
		propertyChangeSupport.firePropertyChange("region", this.region,
		this.region = region);
	}

	public String getRegion() {
		return region;
	}
	
	protected ModelObject getBean() {
		return (ModelObject) ((LinkedHashMap<String, Object>)getModel()).get(SettingsBean.INDEX);
	}

	public String getSplitSize() {
		return splitSize;
	}
	public void setSplitSize(String splitSize) {
		propertyChangeSupport.firePropertyChange("splitSize", this.splitSize,
		this.splitSize = splitSize);
	}
	public String getCopyPartitions() {
		return copyPartitions;
	}
	public void setCopyPartitions(String copyPartitions) {
		propertyChangeSupport.firePropertyChange("copyPartitions", this.copyPartitions,
		this.copyPartitions = copyPartitions);
	}
	public boolean isEnableTXT() {
		return enableTXT;
	}
	public void setEnableTXT(boolean enableTXT) {
		propertyChangeSupport.firePropertyChange("enableTXT", this.enableTXT,
		this.enableTXT = enableTXT);
	}
	public String getTxtLayout() {
		return txtLayout;
	}
	public void setTxtLayout(String txtLayout) {
		propertyChangeSupport.firePropertyChange("txtLayout", this.txtLayout,
				this.txtLayout = txtLayout);	
	}

	public String getCoverPath2d() {
		return coverPath2d;
	}
	
	public void setCoverPath2d(String coverPath2d) {
		propertyChangeSupport.firePropertyChange("coverPath2d", this.coverPath2d,
				this.coverPath2d = coverPath2d);
	}
	
	public void setCoverPath3d(String coverPath3d) {
		propertyChangeSupport.firePropertyChange("coverPath3d", this.coverPath3d,
				this.coverPath3d = coverPath3d);
	}

	public String getCoverPath3d() {
		return coverPath3d;
	}

	public void setCoverPathDisc(String coverPathDisc) {
		propertyChangeSupport.firePropertyChange("coverPathDisc", this.coverPathDisc,
				this.coverPathDisc = coverPathDisc);
	}

	public String getCoverPathDisc() {
		return coverPathDisc;
	}
	
	public void setAutomaticCoverDownload(boolean automaticCoverDownload) {
		propertyChangeSupport.firePropertyChange("automaticCoverDownload", this.automaticCoverDownload,
		this.automaticCoverDownload = automaticCoverDownload);
	}

	public boolean isAutomaticCoverDownload() {
		return automaticCoverDownload;
	}

	public void setUpdateCover(boolean updateCover) {
		this.updateCover = updateCover;
	}

	public boolean isUpdateCover() {
		return updateCover;
	}

	public void setCover3D(boolean cover3D) {
		propertyChangeSupport.firePropertyChange("cover3Denabled", this.cover3Denabled,
		this.cover3Denabled = cover3D);
	}

	public boolean isCover3D() {
		return cover3Denabled;
	}

	public void setCoverDiscs(boolean coverDiscs) {
		propertyChangeSupport.firePropertyChange("coverDiscsEnabled", this.coverDiscsEnabled,
		this.coverDiscsEnabled = coverDiscs);
	}

	public boolean isCoverDiscs() {
		return coverDiscsEnabled;
	}



	public void setDiskPath(String diskPath) {
		propertyChangeSupport.firePropertyChange("diskPath", this.diskPath,
		this.diskPath = diskPath);
	}



	public String getDiskPath() {
		return diskPath;
	}
	
	public void setManagerMode(boolean managerMode) {
		this.managerMode = managerMode;
	}

	public boolean isManagerMode() {
		return managerMode;
	}

}
