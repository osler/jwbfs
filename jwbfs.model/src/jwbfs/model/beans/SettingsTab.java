package jwbfs.model.beans;

import java.util.LinkedHashMap;

import jwbfs.model.SettingsTabConstants;

public class SettingsTab extends AbstractTab {
	
	public static final int INDEX = 2;
	
	private String splitSize = SettingsTabConstants.SPLITSIZE_Text[0];
	private String copyPartitions = SettingsTabConstants.COPY_PARTITIONS_Text[0];
	private String enableTXT = SettingsTabConstants.ENABLE_TXT_CREATION_Text[0];
	private String txtLayout = SettingsTabConstants.TXT_LAYOUT_Text[0];
	
	public SettingsTab(){
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
	
	protected AbstractTab getTabBean() {
		return (AbstractTab) ((LinkedHashMap<Integer, AbstractTab>)getModel()).get(this.INDEX);
	}
	
}
