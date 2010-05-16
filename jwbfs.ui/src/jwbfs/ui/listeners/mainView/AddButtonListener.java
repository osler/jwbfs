package jwbfs.ui.listeners.mainView;

import jwbfs.model.Constants;
import jwbfs.model.Model;
import jwbfs.ui.exceptions.FileNotSelectedException;
import jwbfs.ui.exceptions.NotValidDiscException;
import jwbfs.ui.handlers.CheckDiscHandler;
import jwbfs.ui.handlers.FileDialogAddHandler;
import jwbfs.ui.handlers.ToWBFSConvertHandler;
import jwbfs.ui.handlers.UpdateCoverHandler;
import jwbfs.ui.handlers.UpdateGameListHandler;
import jwbfs.ui.utils.GuiUtils;
import jwbfs.ui.utils.Utils;
import jwbfs.ui.views.ManagerView;

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
	
		Model.getConvertGameBean().clean();
		
		try {
			Utils.getHandlerService(viewID).executeCommand(FileDialogAddHandler.ID, null);
//			CheckDiscHandler.index = -1;
			Model.getSettingsBean().setManagerMode(false);
			Utils.getHandlerService(viewID).executeCommand(CheckDiscHandler.ID, null);
			
			if(Model.getConvertGameBean().getFilePath() == null || Model.getConvertGameBean().getFilePath().equals("")){
				GuiUtils.setDefaultCovers();
				throw new FileNotSelectedException();
			}
			
			if(Model.getConvertGameBean().getId().contains("not a wii disc")){
				GuiUtils.setDefaultCovers();
				throw new NotValidDiscException();

			}

			Utils.getHandlerService(viewID).executeCommand(UpdateCoverHandler.ID, null);
			if(Model.getConvertGameBean().isIsoToWbfs()){
				Utils.getHandlerService(viewID).executeCommand(ToWBFSConvertHandler.ID, null);
				Utils.getHandlerService(viewID).executeCommand(UpdateGameListHandler.ID, null);
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
