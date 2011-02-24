package jwbfs.ui.handlers;

import java.util.LinkedHashMap;

import jwbfs.model.ModelStore;
import jwbfs.model.beans.GameBean;
import jwbfs.model.utils.CoreConstants;
import jwbfs.ui.utils.GuiUtils;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.SWT;

public class DeleteGameHandler extends AbstractHandler {


	public DeleteGameHandler() {
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		String viewId = GuiUtils.getActiveViewID();
		
		GameBean selectedGame =  ModelStore.getSelectedGame();
		
		if(selectedGame.getId() == null || selectedGame.getId().equals("")){
			GuiUtils.showInfo("Select a Game", SWT.ERROR);
			return false;
		}
		
		GuiUtils.executeCommand(viewId, CoreConstants.COMMAND_FILE_DELETE_DIALOG_ID, null);

		LinkedHashMap<String,String> parametri = new LinkedHashMap<String,String>();
		parametri.put("diskID",viewId);
		GuiUtils.executeParametrizedCommand(CoreConstants.COMMAND_GAMELIST_UPDATE_ID,parametri,null);
		

		GuiUtils.setDefaultCovers();

		return true;
	}
}
