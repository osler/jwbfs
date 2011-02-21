package jwbfs.ui.handlers;

import jwbfs.model.ModelStore;
import jwbfs.model.beans.GameBean;
import jwbfs.model.utils.CoreConstants;
import jwbfs.ui.exceptions.FileNotSelectedException;
import jwbfs.ui.utils.GuiUtils;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public class ExportGameHandler extends AbstractHandler {


	public ExportGameHandler() {
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {

		String viewID = GuiUtils.getActiveViewID();
		
		try {
			
			GameBean gameSelected = ModelStore.getSelectedGame();

			if(gameSelected.getFilePath() == null 
					|| gameSelected.getFilePath().equals("")){
				 
				throw new FileNotSelectedException();

			}
			
			boolean ok = (Boolean) GuiUtils.executeCommand(viewID, CoreConstants.COMMAND_FILE_EXPORT_DIALOG_ID, null);

			if(!ok){
				return false;
			}

			GuiUtils.executeCommand(viewID, CoreConstants.COMMAND_TOISO_ID, null);
		}catch(FileNotSelectedException e){
			return false;
		}
		
		return true;
	}
}
