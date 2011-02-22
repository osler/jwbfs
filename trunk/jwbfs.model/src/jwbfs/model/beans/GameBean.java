package jwbfs.model.beans;

import java.util.ArrayList;



public class GameBean extends ModelObject  {

	public static final String INDEX = "gameBean";
	private boolean isoToWbfs = true;
	private boolean wbfsToIso = false;
	//PATH
	private String filePath = "";
	private String folderPath = "";
	//INFOS
	private String id = "";
	private String title = "";
	private String scrubGb = "";
	private String region = "";
	private long scrubSize = 0;

	private ArrayList<String> gameAlternativeTitles;
	
	public String toString(){
		return id +" - "+ title;
	}
	
	public boolean isEmpty(){
		return id.trim().equals("");
	}
	
	public GameBean(){
		this.addPropertyChangeListener(this);
//		setFolderPath(System.getProperty("wbfs.convert.folder"));
	}
	
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		propertyChangeSupport.firePropertyChange("region", this.region,
		this.region = region);
	}
	
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

	public long getScrubSize() {
		return scrubSize;
	}

	
	public void setScrubSize(long scrubSize) {
		propertyChangeSupport.firePropertyChange("scrubSize", this.scrubSize,
				this.scrubSize = scrubSize);
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		propertyChangeSupport.firePropertyChange("filePath", this.filePath,
				this.filePath = filePath);
	}


	public boolean isIsoToWbfs() {
		return isoToWbfs || filePath.endsWith(".iso") || filePath.endsWith(".ISO");
	}

	public boolean isWbfsToIso() {
		return wbfsToIso || filePath.endsWith(".wbfs") || filePath.endsWith(".WBFS");
	}
	
	public void setIsoToWbfs(boolean isoToWbfs) {
		//Reset filePath
		setFilePath("");
		setId("");
		setTitle("");
		setScrubGb("");
		propertyChangeSupport.firePropertyChange("isoToWbfs", this.isoToWbfs,
		this.isoToWbfs = isoToWbfs);
	}

	public void setWbfsToIso(boolean wbfsToIso) {
		//Reset filePath
		setFilePath("");
		setId("");
		setTitle("");
		setScrubGb("");
		propertyChangeSupport.firePropertyChange("wbfsToIso", this.wbfsToIso,
		this.wbfsToIso = wbfsToIso);
	}

	public void clean() {
		filePath = "";
		id = "";
		scrubGb = "";
		scrubSize = 0;
		title = "";
		region = "";
		folderPath = "";
	}
	
	public void setFolderPath(String folderPath) {
		propertyChangeSupport.firePropertyChange("folderPath", this.folderPath,
		this.folderPath = folderPath);
	}

	public String getFolderPath() {
		return folderPath;
	}

	public void setGameAlternativeTitles(ArrayList<String> gameTitles) {
		this.gameAlternativeTitles = gameTitles;
	}

	public ArrayList<String> getGameAlternativeTitles() {

		
		ArrayList<String> gameTitlesMod = new ArrayList<String>();
		
		if(gameAlternativeTitles.contains(title.trim())){
			gameTitlesMod.add(title);
		}
		
		
		for (int i = 0; i < gameAlternativeTitles.size(); i++) {
			String string = gameAlternativeTitles.get(i);
			gameTitlesMod.add(string);
			
		}
		return gameTitlesMod;
	}
	
	public String[] getGameAlternativeTitlesAsArray() {
		
		ArrayList<String> names = getGameAlternativeTitles();
		
		String[] ret = new String[names.size()];
		for (int j = 0; j < ret.length; j++) {
			String string = names.get(j);
			ret[j] = string;
		}
		
		return ret;
		
	}

	boolean selected = false;
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean b){
		this.selected = b;
	}

	@Override
	public boolean equals(Object obj) {		
		if(!(obj instanceof GameBean)){
			return false;
		}
		return this.id.trim().equals(((GameBean)obj).getId());
	}
}
