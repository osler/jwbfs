package jwbfs.model.beans;

public class SystemSettings extends ModelObject{

	private boolean offlineMode = false;

	
	public void setOfflineMode(boolean offlineMode) {
		propertyChangeSupport.firePropertyChange("offlineMode", this.offlineMode,
		this.offlineMode = offlineMode);
	}
	public boolean isOfflineMode() {
		return offlineMode;
	}
	

}
