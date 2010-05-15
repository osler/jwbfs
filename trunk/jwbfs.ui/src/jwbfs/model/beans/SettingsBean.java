package jwbfs.model.beans;

import java.util.LinkedHashMap;

import jwbfs.model.Constants;
import jwbfs.model.CoverConstants;

public class SettingsBean extends ModelObject {
	
	public static final String INDEX = "settingsBean";
	
	//WBFS_FILE SETTINGS
	private String splitSize;
	private String copyPartitions;
	private boolean enableTXT;
	private String txtLayout;
	
	//WIITDB SETTINGS
	private String region;
	private String coverPath;
	private boolean automaticCoverDownload;
	private boolean updateCover;
	private boolean cover3D;
	private boolean coverDiscs;
	
	//MANAGER SETTINGS
	private String diskPath;
	private boolean managerMode = false;
	private String folderPath;
	
	public SettingsBean(){
		this.addPropertyChangeListener(this);
		
		splitSize = Constants.SPLITSIZE_Text[0];
		copyPartitions = Constants.COPY_PARTITIONS_Text[0];
		enableTXT = true;
		txtLayout = Constants.TXT_LAYOUT_Text[0];
		
		//WIITDB SETTINGS
		region = System.getProperty("cover.region");
		coverPath = System.getProperty("java.io.tmpdir");
		automaticCoverDownload = true;
		updateCover = false;
		cover3D = false;
		coverDiscs = false;
		
		//MANAGER
		setDiskPath(System.getProperty("wbfs.disk.path"));

	}
	
	public String getFolderPath() {
		if(isManagerMode()){
			return diskPath;
		}
		return folderPath;
	}

	public void setFolderPath(String folderPath) {
		if(isManagerMode()){
			propertyChangeSupport.firePropertyChange("diskPath", this.diskPath,
					this.diskPath = folderPath);
		}else{
			propertyChangeSupport.firePropertyChange("folderPath", this.folderPath,
					this.folderPath = folderPath);
		}
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

	public void setCoverPath(String coverPath) {
		propertyChangeSupport.firePropertyChange("coverPath", this.coverPath,
				this.coverPath = coverPath);
	}

	public String getCoverPath() {
		return coverPath;
	}

	public void setRegion(String region) {
		propertyChangeSupport.firePropertyChange("region", this.region,
		this.region = region);
	}

	public String getRegion() {
		return region;
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
		propertyChangeSupport.firePropertyChange("cover3D", this.cover3D,
		this.cover3D = cover3D);
	}

	public boolean isCover3D() {
		return cover3D;
	}

	public void setCoverDiscs(boolean coverDiscs) {
		propertyChangeSupport.firePropertyChange("coverDiscs", this.coverDiscs,
		this.coverDiscs = coverDiscs);
	}

	public boolean isCoverDiscs() {
		return coverDiscs;
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
