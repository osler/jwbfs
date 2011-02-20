package jwbfs.model.beans;

import java.beans.PropertyChangeEvent;


public class CoverSettings extends ModelObject{

	private boolean coverTypeUSBLoaderGX = false;
	private boolean coverTypeUSBLoaderCFG = false;
	private boolean coverTypeUSBLoaderWIIFLOW= false;
	private CoverPaths coverPaths = new CoverPaths();
	
	private boolean automaticCoverDownload;
	private boolean updateCover;
	private boolean cover3Denabled;
	private boolean coverDiscsEnabled;
	
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
			coverTypeUSBLoaderGX = false;
			coverTypeUSBLoaderCFG = false;
		}
		
		//TODO SAVE AND GET
		automaticCoverDownload = true;
		cover3Denabled = true;
		coverDiscsEnabled = true;
		
		updateCover = false;
	}
	
	public void setCoverTypeUSBLoaderCFG(boolean coverTypeUSBLoaderCFG) {
		this.coverTypeUSBLoaderGX = false;
		this.coverTypeUSBLoaderWIIFLOW = false;
		
		propertyChangeSupport.firePropertyChange("coverTypeUSBLoaderCFG", this.coverTypeUSBLoaderCFG,
		this.coverTypeUSBLoaderCFG = coverTypeUSBLoaderCFG);
	}
	public boolean isCoverTypeUSBLoaderCFG() {
		return coverTypeUSBLoaderCFG;
	}
	
	public void setCoverTypeUSBLoaderGX(boolean coverTypeUSBLoaderGX) {
		this.coverTypeUSBLoaderCFG = false;
		this.coverTypeUSBLoaderWIIFLOW = false;
		
		propertyChangeSupport.firePropertyChange("coverTypeUSBLoaderGX", this.coverTypeUSBLoaderGX,
		this.coverTypeUSBLoaderGX = coverTypeUSBLoaderGX);
	}
	public boolean isCoverTypeUSBLoaderGX() {
		return coverTypeUSBLoaderGX;
	}
	public void setCoverTypeUSBLoaderWIIFLOW(boolean coverTypeUSBLoaderWIIFLOW) {
		this.coverTypeUSBLoaderGX = false;
		this.coverTypeUSBLoaderCFG = false;
		
		propertyChangeSupport.firePropertyChange("coverTypeUSBLoaderWIIFLOW", this.coverTypeUSBLoaderWIIFLOW,
		this.coverTypeUSBLoaderWIIFLOW = coverTypeUSBLoaderWIIFLOW);
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
	
	public boolean isCoverTypeAnySelected(){	
		return coverTypeUSBLoaderGX || coverTypeUSBLoaderCFG || coverTypeUSBLoaderWIIFLOW;
	}
	
	public void propertyChange(PropertyChangeEvent evt) {
		super.propertyChange(evt);
		
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



}
