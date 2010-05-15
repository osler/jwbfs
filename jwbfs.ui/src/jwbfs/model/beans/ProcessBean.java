package jwbfs.model.beans;

import java.util.LinkedHashMap;



public class ProcessBean extends ModelObject  {

	public static final int INDEX = 0;
	private boolean isoToWbfs = true;
	private boolean wbfsToIso = false;
	private String filePath;
	private String folderPath;
	private String id;
	private String title;
	private String scrubGb;
	private String region;
	
	public ProcessBean(){
		this.addPropertyChangeListener(this);
		folderPath = System.getProperty("wbfs.convert.folder");
	}
	
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		propertyChangeSupport.firePropertyChange("region", this.region,
		region = region);
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

	public String getFolderPath() {
		return folderPath;
	}

	public void setFolderPath(String folderPath) {
		propertyChangeSupport.firePropertyChange("folderPath", this.folderPath,
				this.folderPath = folderPath);
	}

	protected ModelObject getBean() {
		return (ModelObject) ((LinkedHashMap<Integer, ModelObject>)getModel()).get(ProcessBean.INDEX);
	}

	public boolean isIsoToWbfs() {
		return isoToWbfs;
	}

	public boolean isWbfsToIso() {
		return wbfsToIso;
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
	

}
