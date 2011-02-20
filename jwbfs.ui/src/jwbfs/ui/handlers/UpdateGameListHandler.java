package jwbfs.ui.handlers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jwbfs.model.ModelStore;
import jwbfs.model.beans.DiskBean;
import jwbfs.model.beans.GameBean;
import jwbfs.model.utils.CoreConstants;
import jwbfs.model.utils.FileUtils;
import jwbfs.model.utils.PlatformUtils;
import jwbfs.ui.utils.GuiUtils;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.ui.handlers.IHandlerService;

public class UpdateGameListHandler extends AbstractHandler {
	


	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		String diskID = event.getParameter("diskID");
		
		if(diskID.trim().equals("activeDiskID")){
			diskID = GuiUtils.getActiveViewID();
		}

		DiskBean diskbean = (DiskBean) ModelStore.getDisk(diskID);

		ModelStore.setGames(diskID,listGames(diskbean.getDiskPath()));

		ModelStore.cleanExportGame();
		ModelStore.cleanSelectedGame();
		
		GuiUtils.getManagerTableViewer(diskID).refresh();
		GuiUtils.setDefaultCovers();

		return null;
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
	
			IHandlerService handlerService = PlatformUtils.getHandlerService();
	
			boolean infoOK = true;
			try {
				infoOK = (Boolean) handlerService.executeCommand(CoreConstants.COMMAND_CHECKDISK_ID, null);
			} catch (ExecutionException e) {
				e.printStackTrace();
			} catch (NotDefinedException e) {
				e.printStackTrace();
			} catch (NotEnabledException e) {
				e.printStackTrace();
			} catch (NotHandledException e) {
				e.printStackTrace();
			}
	
			if(infoOK){
				game.add(g);	
			}
		}
		return game;
	}



}
