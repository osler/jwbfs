package jwbfs.model.beans;

import java.util.LinkedHashMap;

import jwbfs.model.Constants;
import jwbfs.ui.utils.Utils;

public class SettingsBean extends AbstractTab {
	
	public static final int INDEX = 2;
	
	//WBFS_FILE SETTINGS
	private String splitSize = Constants.SPLITSIZE_Text[0];
	private String copyPartitions = Constants.COPY_PARTITIONS_Text[0];
	private String enableTXT = Constants.ENABLE_TXT_CREATION_Text[0];
	private String txtLayout = Constants.TXT_LAYOUT_Text[0];
	
	//WIITDB SETTINGS
	private String region = Constants.REGIONS[0];
	private String coverPath = System.getProperty("java.io.tmpdir");
	
	
	protected AbstractTab getTabBean() {
		return (AbstractTab) ((LinkedHashMap<Integer, AbstractTab>)getModel()).get(SettingsBean.INDEX);
	}
	
	public SettingsBean(){
		this.addPropertyChangeListener(this);
	}
	
	public String getSplitSize() {
		return splitSize;
	}
	public void setSplitSize(String splitSize) {
		propertyChangeSupport.firePropertyChange("splitSize", this.splitSize,
		this.splitSize = splitSize);
	}
	public String getCopyPartitions() {
		return copyPartitions;
	}
	public void setCopyPartitions(String copyPartitions) {
		propertyChangeSupport.firePropertyChange("copyPartitions", this.copyPartitions,
		this.copyPartitions = copyPartitions);
	}
	public String getEnableTXT() {
		return enableTXT;
	}
	public void setEnableTXT(String enableTXT) {
		propertyChangeSupport.firePropertyChange("enableTXT", this.enableTXT,
		this.enableTXT = enableTXT);
	}
	public String getTxtLayout() {
		return txtLayout;
	}
	public void setTxtLayout(String txtLayout) {
		propertyChangeSupport.firePropertyChange("txtLayout", this.txtLayout,
				this.txtLayout = txtLayout);	
	}

	public void setCoverPath(String coverPath) {
		propertyChangeSupport.firePropertyChange("coverPath", this.coverPath,
				this.coverPath = coverPath);
	}

	public String getCoverPath() {
		return coverPath;
	}

	public void setRegion(String region) {
		propertyChangeSupport.firePropertyChange("region", this.region,
		this.region = region);
	}

	public String getRegion() {
		return region;
	}
}
