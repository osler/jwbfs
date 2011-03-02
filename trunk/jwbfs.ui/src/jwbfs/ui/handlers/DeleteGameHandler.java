package jwbfs.ui.handlers;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import jwbfs.model.beans.GameBean;
import jwbfs.model.utils.CoreConstants;
import jwbfs.model.utils.FileUtils;
import jwbfs.ui.utils.GuiUtils;
import jwbfs.ui.views.dialogs.ConfirmOverwriteDialog;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.IDialogConstants;

public class DeleteGameHandler extends AbstractHandler {


	public DeleteGameHandler() {
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		String diskFrom = GuiUtils.getActiveViewID();
		
		ArrayList<GameBean> gamesTo = new ArrayList<GameBean>();
		try {
			gamesTo = GuiUtils.getSelectedGames(diskFrom);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		if(gamesTo.size() == 0 /*|| selectedGame.getId() == null || selectedGame.getId().equals("")*/){
			GuiUtils.showError("Select at least a game");
			return false;
		}
		boolean oneGame = gamesTo.size() == 1;
		
		boolean yesAll = false;
		for(int i = 0; i<gamesTo.size();i++){
//			boolean ret = (Boolean)GuiUtils.executeCommand(viewId, CoreConstants.COMMAND_FILE_DELETE_DIALOG_ID, null);

			GameBean bean = gamesTo.get(i);

//			boolean delete = true;
			if(!yesAll && !oneGame){
				
				String message = "Delete '"+bean.getTitle()+"' ?";
				int ret  = new ConfirmOverwriteDialog(message).open();

				switch (ret) {
				case IDialogConstants.NO_ID: continue; //go on cycling
				case IDialogConstants.NO_TO_ALL_ID: return true; //exit now
				case IDialogConstants.YES_ID: break;
				case IDialogConstants.YES_TO_ALL_ID:  yesAll = true; break;

				}
			}
			if(oneGame){
				boolean res = GuiUtils.showConfirmDialog("Delete '"+bean.getTitle()+"' ?");
				if(!res){
					return true;
				}
			}
			
				File fileWbfs = new File(bean.getFilePath());
				File fileTxt = new File(FileUtils.getTxtFile(fileWbfs));
				File folder = new File(bean.getFilePath().replace(fileWbfs.getName(), ""));
				
				if(fileWbfs.exists()){
					System.out.println("Deleting "+fileWbfs.getAbsolutePath());
					fileWbfs.delete();
					System.out.println("Deleted!");
				}
				if(fileTxt.exists()){
					System.out.println("Deleting "+fileTxt.getAbsolutePath());
					fileTxt.delete();
					System.out.println("Deleted!");
				}
				if(folder.exists() && folder.isDirectory() && folder.getAbsolutePath().contains(bean.getId())){
					System.out.println("Deleting Folder"+folder.getAbsolutePath());
					folder.delete();
					System.out.println("Deleted!");
				}
		}

		LinkedHashMap<String,String> parametri = new LinkedHashMap<String,String>();
		parametri.put("diskID",diskFrom);
		GuiUtils.executeParametrizedCommand(CoreConstants.COMMAND_GAMELIST_UPDATE_ID,parametri,null);
		

		GuiUtils.setDefaultCovers();

		return true;
	}
}
