package jwbfs.ui.listeners.mainView;

import jwbfs.model.Model;
import jwbfs.model.beans.GameBean;
import jwbfs.model.utils.CoreConstants;
import jwbfs.model.utils.PlatformUtils;
import jwbfs.ui.utils.GuiUtils;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class DeleteButtonListener extends SelectionAdapter {


	private String viewID;

	public DeleteButtonListener(String viewID){
		this.viewID = viewID;
	}


	@Override
	public void widgetSelected(SelectionEvent e) {
	
		GameBean selectedGame =  Model.getSelectedGame();
		
		if(selectedGame.getId() == null || selectedGame.getId().equals("")){
			GuiUtils.showInfo("Select a Game", SWT.ERROR);
			return;
		}
		
		
		try {
			PlatformUtils.getHandlerService(viewID).executeCommand(CoreConstants.COMMAND_FILE_DELETE_DIALOG_ID, null);
			PlatformUtils.getHandlerService(viewID).executeCommand(CoreConstants.COMMAND_GAMELIST_UPDATE_ID, null);
			GuiUtils.setDefaultCovers();
			
		} catch (ExecutionException e1) {
			e1.printStackTrace();
		} catch (NotDefinedException e1) {
			e1.printStackTrace();
		} catch (NotEnabledException e1) {
			e1.printStackTrace();
		} catch (NotHandledException e1) {
			e1.printStackTrace();
		}


	}

}
