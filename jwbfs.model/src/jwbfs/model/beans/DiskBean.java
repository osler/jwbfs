package jwbfs.model.beans;

import java.util.ArrayList;
import java.util.List;

public class DiskBean extends ModelObject {

	private String diskPath;
	private List<GameBean> games = new ArrayList<GameBean>();
	
	public DiskBean(String diskPath){
		this.diskPath =  diskPath;
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
}
