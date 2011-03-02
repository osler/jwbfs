package jwbfs.ui.jobs;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import jwbfs.model.ModelStore;
import jwbfs.model.beans.DiskBean;
import jwbfs.model.beans.GameBean;
import jwbfs.model.utils.CoreConstants;
import jwbfs.model.utils.FileUtils;
import jwbfs.ui.utils.GuiUtils;

public class UpdateGameListOperation implements Runnable {

	private String diskID;

	public UpdateGameListOperation(String name, String diskID) {
		this.diskID = diskID;
	}

	@Override
	public void run() {
		
		DiskBean diskbean = (DiskBean) ModelStore.getDisk(diskID);

		ModelStore.setGames(diskID,listGames(diskbean.getDiskPath()));

		ModelStore.cleanExportGame();
		ModelStore.cleanSelectedGame();
		
		if(GuiUtils.viewExist(diskID)){
			try {
				GuiUtils.getManagerTableViewer(diskID).refresh();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		GuiUtils.setDefaultCovers();
		
	}


	public  List<GameBean> listGames(String folder) {
		List<GameBean> game = new ArrayList<GameBean>();
		
		if(folder.trim().equals("")){
			return game;
		}
		
		FileUtils.checkAndCreateFolder(folder);
		
		File f = new File(folder);
		File[] folderList = f.listFiles();
		if(folderList == null){ //NO GAMES in folder
			return game;
		}
				
		for(int x = 0; x<folderList.length;x++){
			File f2 = folderList[x];
			if(f2.isDirectory()){
				File[] files = f2.listFiles();
				if(files != null){
					for(int j = 0; j < files.length; j++){
						game = checkFile(files,j,game);
	
					}
				}
			}else{
				game = checkFile(folderList,x,game);
			}
		}
		
		return game;
	}

	private static List<GameBean> checkFile(File[] files, int j, List<GameBean> game) {
		if(files[j].getName().endsWith(".wbfs")){
			GameBean g = new GameBean();
			g.setFilePath(files[j].getAbsolutePath());
			System.out.println(g.getFilePath());
	
			ModelStore.setSelectedGame(g);
	
			boolean infoOK = true;
			
			LinkedHashMap<String,String> parametri = new LinkedHashMap<String,String>();
			parametri.put("wbfs","true");
			infoOK = (Boolean) GuiUtils.executeParametrizedCommand(CoreConstants.COMMAND_CHECKDISK_ID, parametri, null);
					
			if(infoOK){
				game.add(g);	
			}
		}
		return game;
	}


}
