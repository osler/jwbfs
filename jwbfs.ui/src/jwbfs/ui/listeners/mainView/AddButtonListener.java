package jwbfs.ui.listeners.mainView;

import jwbfs.model.Model;
import jwbfs.model.utils.CoreConstants;
import jwbfs.ui.exceptions.FileNotSelectedException;
import jwbfs.ui.exceptions.NotValidDiscException;
import jwbfs.ui.utils.GuiUtils;
import jwbfs.ui.utils.PlatformUtils;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class AddButtonListener extends SelectionAdapter {


	private String viewID;

	public AddButtonListener(String viewID){
		this.viewID = viewID;
	}


	@Override
	public void widgetSelected(SelectionEvent e) {
	
		Model.getSelectedGame().clean();
		
		try {
			PlatformUtils.getHandlerService(viewID).executeCommand(CoreConstants.COMMAND_FILE_IMPORT_DIALOG_ID, null);
		
			PlatformUtils.getHandlerService(viewID).executeCommand(CoreConstants.COMMAND_CHECKDISK_ID, null);
			
			if(Model.getSelectedGame().getFilePath() == null || Model.getSelectedGame().getFilePath().equals("")){
				GuiUtils.setDefaultCovers();
				throw new FileNotSelectedException();
			}
			
			if(Model.getSelectedGame().getId().contains("not a wii disc")){
				GuiUtils.setDefaultCovers();
				throw new NotValidDiscException();

			}

			PlatformUtils.getHandlerService(viewID).executeCommand(CoreConstants.COMMAND_COVER_UPDATE_ID, null);
			if(Model.getSelectedGame().isIsoToWbfs()){
				PlatformUtils.getHandlerService(viewID).executeCommand(CoreConstants.COMMAND_TOWBFS_ID, null);
				PlatformUtils.getHandlerService(viewID).executeCommand(CoreConstants.COMMAND_GAMELIST_UPDATE_ID, null);
				GuiUtils.setDefaultCovers();
			}

		} catch (ExecutionException e1) {
			e1.printStackTrace();
		} catch (NotDefinedException e1) {
			e1.printStackTrace();
		} catch (NotEnabledException e1) {
			e1.printStackTrace();
		} catch (NotHandledException e1) {
			e1.printStackTrace();
		} catch (FileNotSelectedException e1) {
			e1.printStackTrace();
		} catch (NotValidDiscException e1) {
			e1.printStackTrace();
		}




	

	}

}
