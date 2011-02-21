package jwbfs.ui.handlers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jwbfs.model.ModelStore;
import jwbfs.model.beans.GameBean;
import jwbfs.model.utils.CoreConstants;
import jwbfs.model.utils.FileUtils;
import jwbfs.ui.utils.GuiUtils;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public class CopyGamesHandler extends AbstractHandler {


	public CopyGamesHandler() {
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {

		GuiUtils.showError("in development");
		
		String diskFrom = event.getParameter("diskFrom").trim();
		String diskTO = event.getParameter("diskTo").trim();
		
		if(diskFrom.trim().equals("activeID")){
			diskFrom = GuiUtils.getActiveViewID();
		}
		if(diskFrom.equals(diskTO)){
			GuiUtils.showError("Destination disk and source disk are the same");
		}
		
//		List<GameBean> gamesFrom= ModelStore.getDisk(diskFrom).getGames();
//		ArrayList<GameBean> gamesTo = new ArrayList<GameBean>();
//		
//		for(int i=0;i<gamesFrom.size();i++){
//			GameBean g = gamesFrom.get(i);
//			if(g.isSelected()){
//				gamesTo.add(g);
//			}
//		}
//		
//		for(int i=0;i<gamesTo.size();i++){
//			GameBean g = gamesTo.get(i);
//			String pathFrom = new File(g.getFilePath()).getParent();
//			String pathTo = ModelStore.getDisk(diskTO).getDiskPath();
//			
//			String folderName = pathFrom.replaceAll(ModelStore.getDisk(diskFrom).getDiskPath(),"");
//			
//			File newFolder = new File(pathTo+File.separatorChar+folderName);
//			if(!newFolder.exists()){
//				//TODO
//			}
//		}
//		
//		
//		//Update all
//		GuiUtils.executeCommand(diskFrom,CoreConstants.COMMAND_REFRESH_ALL_DISK_ID,null);
//		
//		GuiUtils.getManagerTableViewer(diskFrom).setInput(ModelStore.getGames(diskFrom));
//		//reset
//		for(int i=0;i<gamesFrom.size();i++){
//			GameBean g = gamesFrom.get(i);
//			if(g.isSelected()){
//				g.setSelected(false);
//			}
//		}
//		GuiUtils.getManagerTableViewer(diskFrom).refresh();
		return true;
	}
}
