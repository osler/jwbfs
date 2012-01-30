package jwbfs.ui.handlers;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import jwbfs.model.ModelStore;
import jwbfs.model.beans.CopyBean;
import jwbfs.model.beans.GameBean;
import jwbfs.model.utils.CoreConstants;
import jwbfs.ui.exceptions.NotValidDiscException;
import jwbfs.ui.utils.GuiUtils;

import org.eclipse.core.commands.ExecutionEvent;

public class AddGameHandler extends JwbfsAbstractHandler {


	public AddGameHandler() {
	}

	public Object executeJwbfs(ExecutionEvent event) throws Exception{

		String viewId = GuiUtils.getActiveViewID();

		boolean ok = (Boolean) GuiUtils.executeCommand(viewId, CoreConstants.COMMAND_FILE_IMPORT_DIALOG_ID, null);

		if(!ok){
			return false;
		}

		LinkedHashMap<String,String> parametri = new LinkedHashMap<String,String>();
		parametri.put(CoreConstants.PARAM_WBFS,"true");
		ok = (Boolean) GuiUtils.executeParametrizedCommand(CoreConstants.COMMAND_CHECKDISK_ID, parametri, null);

		if(!ok){
			return false;
		}

		if(ModelStore.getSelectedGame().getId().contains(CoreConstants.NOT_A_WII_DISK)){
			GuiUtils.setDefaultCovers();
			throw new NotValidDiscException();
		}

		ok = (Boolean) GuiUtils.executeCommand(viewId, CoreConstants.COMMAND_COVER_UPDATE_ID, null);
		if(ModelStore.getSelectedGame().isIsoToWbfs()){
			ok = (Boolean) 	GuiUtils.executeCommand(viewId, CoreConstants.COMMAND_TOWBFS_ID, null);

			if(!ok){
				GuiUtils.setDefaultCovers();
				return false;
			}

			parametri = new LinkedHashMap<String,String>();
			parametri.put(CoreConstants.PARAM_DISK_ID,viewId);
			ok = (Boolean) 		GuiUtils.executeParametrizedCommand(CoreConstants.COMMAND_GAMELIST_UPDATE_ID,parametri,null);

			if(!ok){
				GuiUtils.setDefaultCovers();
				return false;
			}
			GuiUtils.setDefaultCovers();
		}else 	
			//file selected is a wbfs then it's a copy from folder
			if(ModelStore.getSelectedGame().isWbfsToIso()){

				//game bean in copy bean model
				ArrayList<GameBean> gamesTo = new ArrayList<GameBean>();
				gamesTo.add(ModelStore.getSelectedGame());
				CopyBean copyBean = new CopyBean(gamesTo,null);
				ModelStore.setCopyBean(copyBean);	

				parametri = new LinkedHashMap<String,String>();
				parametri.put(CoreConstants.PARAM_DISK_TO,viewId);

				//import the wbfs file to the disk folder
				GuiUtils.executeParametrizedCommand(CoreConstants.COMMAND_IMPORT_WBFS_GAME,parametri, null);


				parametri = new LinkedHashMap<String,String>();
				parametri.put(CoreConstants.PARAM_DISK_ID,viewId);
				ok = (Boolean) 		GuiUtils.executeParametrizedCommand(CoreConstants.COMMAND_GAMELIST_UPDATE_ID,parametri,null);

				if(!ok){
					GuiUtils.setDefaultCovers();
					return false;
				}
				GuiUtils.setDefaultCovers();
			}


		return true;
	}
}
