package jwbfs.model.beans;

import java.util.LinkedHashMap;



public class GameBean extends ModelObject  {

	public static final String INDEX = "gameBean";
	private boolean isoToWbfs = true;
	private boolean wbfsToIso = false;
	//PATH
	private String filePath;
	private String folderPath;
	//INFOS
	private String id;
	private String title;
	private String scrubGb;
	private String region;
	
	public GameBean(){
		this.addPropertyChangeListener(this);
		setFolderPath(System.getProperty("wbfs.convert.folder"));
	}
	
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		propertyChangeSupport.firePropertyChange("region", this.region,
		this.region = region);
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		propertyChangeSupport.firePropertyChange("id", this.id,
				this.id = id);
		
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		propertyChangeSupport.firePropertyChange("title", this.title,
				this.title = title);
	}

	public String getScrubGb() {
		return scrubGb;
	}

	public void setScrubGb(String scrubGb) {
		propertyChangeSupport.firePropertyChange("scrubGb", this.scrubGb,
				this.scrubGb = scrubGb);
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		propertyChangeSupport.firePropertyChange("filePath", this.filePath,
				this.filePath = filePath);
	}

	

	protected ModelObject getBean() {
		return (ModelObject) ((LinkedHashMap<String, Object>)getModel()).get(GameBean.INDEX);
	}

	public boolean isIsoToWbfs() {
		return isoToWbfs || filePath.endsWith(".iso") || filePath.endsWith(".ISO");
	}

	public boolean isWbfsToIso() {
		return wbfsToIso || filePath.endsWith(".wbfs") || filePath.endsWith(".WBFS");
	}
	
	public void setIsoToWbfs(boolean isoToWbfs) {
		//Reset filePath
		setFilePath("");
		setId("");
		setTitle("");
		setScrubGb("");
		propertyChangeSupport.firePropertyChange("isoToWbfs", this.isoToWbfs,
		this.isoToWbfs = isoToWbfs);
	}

	public void setWbfsToIso(boolean wbfsToIso) {
		//Reset filePath
		setFilePath("");
		setId("");
		setTitle("");
		setScrubGb("");
		propertyChangeSupport.firePropertyChange("wbfsToIso", this.wbfsToIso,
		this.wbfsToIso = wbfsToIso);
	}

	public void clean() {
		filePath = null;
		id = null;
		scrubGb = null;
		title = null;
		region = null;
		folderPath = null;
	}
	
	public void setFolderPath(String folderPath) {
		propertyChangeSupport.firePropertyChange("folderPath", this.folderPath,
		this.folderPath = folderPath);
	}

	public String getFolderPath() {
		return folderPath;
	}

}
