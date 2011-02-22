package jwbfs.model.beans;

import java.util.ArrayList;

public class CopyBean extends ModelObject {

	protected ArrayList<GameBean> gamesToCopy;
	protected String diskIdFrom;
	protected boolean cut = false;

	public CopyBean() {
		gamesToCopy = new ArrayList<GameBean>();  
		diskIdFrom = "";
	}
	
	public CopyBean(ArrayList<GameBean> gamesToCopy, String diskIdFrom) {
		this.gamesToCopy = gamesToCopy;
		this.diskIdFrom = diskIdFrom;
	}

	public String getDiskIdFrom() {
		return diskIdFrom;
	}

	public void setDiskIdFrom(String diskIdFrom) {
		propertyChangeSupport.firePropertyChange("diskIdFrom", this.diskIdFrom,
				this.diskIdFrom = diskIdFrom);
	}

	public ArrayList<GameBean> getGamesToCopy() {
		return gamesToCopy;
	}

	public void setGamesToCopy(ArrayList<GameBean> gamesToCopy) {
		propertyChangeSupport.firePropertyChange("gamesToCopy", this.gamesToCopy,
				this.gamesToCopy = gamesToCopy);
	}

	public boolean isCut() {
		return cut;
	}

	public void setCut(boolean cut) {
		propertyChangeSupport.firePropertyChange("cut", this.cut,
				this.cut = cut);
	}
}
