package jwbfs.model.beans;

import java.util.LinkedHashMap;



public class WBFStoISOTab extends AbstractTab  {
	
	
	public static final int INDEX = 1;

	private String filePath;
	private String folderPath;
	private String id;
	private String title;
	private String scrubGb;
	
	
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

	protected AbstractTab getTabBean() {
		return (AbstractTab) ((LinkedHashMap<Integer, AbstractTab>)getModel()).get(WBFStoISOTab.INDEX);
	}
	
}
