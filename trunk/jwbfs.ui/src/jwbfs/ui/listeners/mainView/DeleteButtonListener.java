package jwbfs.ui.listeners.mainView;

import jwbfs.model.Constants;
import jwbfs.model.Model;
import jwbfs.model.beans.GameBean;
import jwbfs.ui.handlers.DeleteFileHandler;
import jwbfs.ui.handlers.UpdateGameListHandler;
import jwbfs.ui.utils.GuiUtils;
import jwbfs.ui.utils.Utils;

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
			Utils.getHandlerService(viewID).executeCommand(DeleteFileHandler.ID, null);
			Utils.getHandlerService(viewID).executeCommand(UpdateGameListHandler.ID, null);
			GuiUtils.setCover(Constants.NOIMAGE);
			GuiUtils.setCover3d(Constants.NOIMAGE3D);
			GuiUtils.setCoverDisc(Constants.NODISC);
			
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
