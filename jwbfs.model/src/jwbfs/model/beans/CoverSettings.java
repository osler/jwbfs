package jwbfs.model.beans;


public class CoverSettings extends ModelObject{

	private boolean coverTypeUSBLoaderGX = false;
	private boolean coverTypeUSBLoaderCFG = false;
	private boolean coverTypeUSBLoaderWIIFLOW= false;
	private CoverPaths coverPaths = new CoverPaths();
	
	public CoverSettings(){
		
		String coverType = System.getProperty("cover.path.type").toString().trim();
		
		if (coverType == null || coverType.equals("null")){
			coverType = CoverPaths.GX; //TODO make pick one
		}
		
		if(coverType.equals(CoverPaths.GX)){
			coverTypeUSBLoaderGX = true;
			coverTypeUSBLoaderCFG = false;
			coverTypeUSBLoaderWIIFLOW = false;
		}else if(coverType.equals(CoverPaths.CFG)){
			coverTypeUSBLoaderGX = false;
			coverTypeUSBLoaderCFG = true;
			coverTypeUSBLoaderWIIFLOW = false;
		}else if(coverType.equals(CoverPaths.WIIFLOW)){
			coverTypeUSBLoaderWIIFLOW = true;
			coverTypeUSBLoaderGX = true;
			coverTypeUSBLoaderCFG = false;
		}
	}
	
	public void setCoverTypeUSBLoaderCFG(boolean coverTypeUSBLoaderCFG) {
		propertyChangeSupport.firePropertyChange("coverTypeUSBLoaderCFG", this.coverTypeUSBLoaderCFG,
		this.coverTypeUSBLoaderCFG = coverTypeUSBLoaderCFG);
		coverTypeUSBLoaderGX = false;
		coverTypeUSBLoaderWIIFLOW = false;
	}
	public boolean isCoverTypeUSBLoaderCFG() {
		return coverTypeUSBLoaderCFG;
	}
	
	public void setCoverTypeUSBLoaderGX(boolean coverTypeUSBLoaderGX) {
		propertyChangeSupport.firePropertyChange("coverTypeUSBLoaderGX", this.coverTypeUSBLoaderGX,
		this.coverTypeUSBLoaderGX = coverTypeUSBLoaderGX);
		coverTypeUSBLoaderCFG = false;
		coverTypeUSBLoaderWIIFLOW = false;
	}
	public boolean isCoverTypeUSBLoaderGX() {
		return coverTypeUSBLoaderGX;
	}
	public void setCoverTypeUSBLoaderWIIFLOW(boolean coverTypeUSBLoaderWIIFLOW) {
		propertyChangeSupport.firePropertyChange("coverTypeUSBLoaderWIIFLOW", this.coverTypeUSBLoaderWIIFLOW,
		this.coverTypeUSBLoaderWIIFLOW = coverTypeUSBLoaderWIIFLOW);
		coverTypeUSBLoaderGX = false;
		coverTypeUSBLoaderWIIFLOW = false;
	}
	public boolean isCoverTypeUSBLoaderWIIFLOW() {
		return coverTypeUSBLoaderWIIFLOW;
	}
	public void setCoverPaths(CoverPaths coverPaths) {
		this.coverPaths = coverPaths;
	}
	public CoverPaths getCoverPaths() {
		return coverPaths;
	}
	

}
