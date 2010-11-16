package jwbfs.model.beans;

public class CoverSettings extends ModelObject{

	private boolean coverTypeUSBLoaderGX = false;
	private boolean coverTypeUSBLoaderCFG = false;
	
	public void setCoverTypeUSBLoaderCFG(boolean coverTypeUSBLoaderCFG) {
		propertyChangeSupport.firePropertyChange("coverTypeUSBLoaderCFG", this.coverTypeUSBLoaderCFG,
		this.coverTypeUSBLoaderCFG = coverTypeUSBLoaderCFG);
	}
	public boolean isCoverTypeUSBLoaderCFG() {
		return coverTypeUSBLoaderGX;
	}
	
	public void setCoverTypeUSBLoaderGX(boolean coverTypeUSBLoaderGX) {
		propertyChangeSupport.firePropertyChange("coverTypeUSBLoaderGX", this.coverTypeUSBLoaderGX,
		this.coverTypeUSBLoaderGX = coverTypeUSBLoaderGX);
	}
	public boolean isCoverTypeUSBLoaderGX() {
		return coverTypeUSBLoaderGX;
	}
	

}
