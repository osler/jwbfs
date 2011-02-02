package jwbfs.model.beans;


public class CoverPaths extends ModelObject{
	
	public static final String CFG 	= "CFG";
	public static final String GX 	= "GX";
	public static final String WIIFLOW = "FLOW";
	
	private String cover2d;
	private String cover3d;
	private String coverDisc;
	private String coverFull;
	
	private boolean support2d	 = false;
	private boolean support3d 	 = false;
	private boolean supportDisc  = false;
	private boolean supportFull  = false;
	
	public String getCover2d() {
		return cover2d;
	}
	public void setCover2d(String cover2d) {
		propertyChangeSupport.firePropertyChange("cover2d", this.cover2d,
				this.cover2d = cover2d);
	}
	public String getCover3d() {
		return cover3d;
	}
	public void setCover3d(String cover3d) {
		propertyChangeSupport.firePropertyChange("cover3d", this.cover3d,
				this.cover3d = cover3d);
	}
	public String getCoverDisc() {
		return coverDisc;
	}
	public void setCoverDisc(String coverDisc) {
		propertyChangeSupport.firePropertyChange("coverDisc", this.coverDisc,
				this.coverDisc = coverDisc);
	}
	public String getCoverFull() {
		return coverFull;
	}
	public void setCoverFull(String coverFull) {
		propertyChangeSupport.firePropertyChange("coverFull", this.coverFull,
				this.coverFull = coverFull);
	}
	public boolean isSupport2d() {
		return support2d;
	}
	public void setSupport2d(boolean support2d) {
		propertyChangeSupport.firePropertyChange("support2d", this.support2d,
				this.support2d = support2d);
	}
	public boolean isSupport3d() {
		return support3d;
	}
	public void setSupport3d(boolean support3d) {
		propertyChangeSupport.firePropertyChange("support3d", this.support3d,
				this.support3d = support3d);
	}
	public boolean isSupportDisc() {
		return supportDisc;
	}
	public void setSupportDisc(boolean supportDisc) {
		propertyChangeSupport.firePropertyChange("supportDisc", this.supportDisc,
				this.supportDisc = supportDisc);
	}
	public boolean isSupportFull() {
		return supportFull;
	}
	public void setSupportFull(boolean supportFull) {
		propertyChangeSupport.firePropertyChange("supportFull", this.supportFull,
				this.supportFull = supportFull);
	}


	
}
