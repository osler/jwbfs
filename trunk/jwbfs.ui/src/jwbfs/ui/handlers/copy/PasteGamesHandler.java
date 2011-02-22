package jwbfs.ui.handlers.copy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jwbfs.model.ModelStore;
import jwbfs.model.beans.CopyBean;
import jwbfs.model.beans.GameBean;
import jwbfs.model.utils.CoreConstants;
import jwbfs.ui.utils.GuiUtils;
import jwbfs.ui.views.dialogs.ConfirmOverwriteDialog;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.IDialogConstants;

public class PasteGamesHandler extends AbstractHandler {

	private ArrayList<GameBean> skipped;

	public PasteGamesHandler() {
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {

		String diskTO = event.getParameter("diskTo").trim();
	
		if(diskTO.equals("activeID")){
			diskTO = GuiUtils.getActiveViewID();
		}
		
		List<GameBean> gamesOnDestinationDisk = ModelStore.getGames(diskTO);
		
		CopyBean copyBean = ModelStore.getCopyBean();
		ArrayList<GameBean> gamesTo = copyBean.getGamesToCopy();
		
		String diskFrom = copyBean.getDiskIdFrom().trim();
		if(diskFrom.equals(diskTO)){
			GuiUtils.showError("Cannot paste into same disk");
			return false;
		}
		
		skipped = new ArrayList<GameBean>();
		boolean ok = processGames(gamesTo,gamesOnDestinationDisk,diskTO,diskFrom);
		
		if(!ok){
			return false;
		}
		
		if(skipped.size()>0){
			ok = processGames(gamesTo,gamesOnDestinationDisk,diskTO,diskFrom);
			if(!ok){
				return false;
			}
		}

		//Update all
		GuiUtils.executeCommand(diskFrom,CoreConstants.COMMAND_REFRESH_ALL_DISK_ID,null);	
		return true;
	}

	private boolean processGames(ArrayList<GameBean> gamesTo,
								List<GameBean> gamesOnDestinationDisk, 
								String diskTO, 
								String diskFrom) {
		
		boolean yesAll = false;
		boolean isSkipped = skipped.size()>0;
		
		for(int i=0;i<gamesTo.size();i++){
			
			GameBean g = gamesTo.get(i);
			
			if(gamesOnDestinationDisk.contains(g) && !isSkipped){
				 skipped.add(g);
				 continue;
			}
			
			if(isSkipped && !yesAll){
				String message = "Game is already present on destination disk:"
								+"\n\n"
								+g.toString()
								+"\n\n"
								+"Do you want to overwrite it?";
				int ret  = new ConfirmOverwriteDialog(message).open();
				
				switch (ret) {
				case IDialogConstants.NO_ID: continue; //go on cycling
				case IDialogConstants.NO_TO_ALL_ID: return true; //exit now
				case IDialogConstants.YES_ID: break;
				case IDialogConstants.YES_TO_ALL_ID:  yesAll = true; break;

				}
			}
				
			String pathFrom = new File(g.getFilePath()).getParent();
			String pathTo = ModelStore.getDisk(diskTO).getDiskPath().trim();
			if(pathTo.equals("")){
				GuiUtils.showError("Select a valid path on destination disk.");
				return false;
			}
			
			String folderName = pathFrom.replaceAll(ModelStore.getDisk(diskFrom).getDiskPath(),"");
			
			File newFolder = new File(pathTo+File.separatorChar+folderName);
			if(!newFolder.exists()){
				//TODO CREATE
			}
		}
		
		if(isSkipped){
			skipped = new ArrayList<GameBean>();
		}
		
		return true;
	}
}
