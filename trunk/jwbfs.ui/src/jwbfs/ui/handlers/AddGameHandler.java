package jwbfs.ui.handlers;

import java.util.LinkedHashMap;

import jwbfs.model.ModelStore;
import jwbfs.model.utils.CoreConstants;
import jwbfs.ui.exceptions.NotValidDiscException;
import jwbfs.ui.utils.GuiUtils;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public class AddGameHandler extends AbstractHandler {


	public AddGameHandler() {
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		String viewId = GuiUtils.getActiveViewID();
		
		try {
			boolean ok = (Boolean) GuiUtils.executeCommand(viewId, CoreConstants.COMMAND_FILE_IMPORT_DIALOG_ID, null);

			if(!ok){
				return false;
			}

			LinkedHashMap<String,String> parametri = new LinkedHashMap<String,String>();
			parametri.put("wbfs","true");
			ok = (Boolean) GuiUtils.executeParametrizedCommand(CoreConstants.COMMAND_CHECKDISK_ID, parametri, null);
			
			if(!ok){
				return false;
			}

			if(ModelStore.getSelectedGame().getId().contains("not a wii disc")){
				GuiUtils.setDefaultCovers();
				throw new NotValidDiscException();
			}

			ok = (Boolean) GuiUtils.executeCommand(viewId, CoreConstants.COMMAND_COVER_UPDATE_ID, null);
			if(ModelStore.getSelectedGame().isIsoToWbfs()){
				ok = (Boolean) 	GuiUtils.executeCommand(viewId, CoreConstants.COMMAND_TOWBFS_ID, null);
				
				if(!ok){
					return false;
				}
				
				parametri = new LinkedHashMap<String,String>();
				parametri.put("diskID",viewId);
				ok = (Boolean) 		GuiUtils.executeParametrizedCommand(CoreConstants.COMMAND_GAMELIST_UPDATE_ID,parametri,null);
				
				if(!ok){
					return false;
				}
				GuiUtils.setDefaultCovers();
			}
			
		} catch (NotValidDiscException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}