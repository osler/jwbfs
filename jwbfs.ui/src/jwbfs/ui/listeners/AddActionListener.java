package jwbfs.ui.listeners;

import jwbfs.model.Model;
import jwbfs.ui.handlers.CheckDiscHandler;
import jwbfs.ui.handlers.FileDialogAddHandler;
import jwbfs.ui.handlers.UpdateCoverHandler;
import jwbfs.ui.utils.Utils;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class AddActionListener extends SelectionAdapter {


	private String viewID;

	public AddActionListener(String viewID){
		this.viewID = viewID;
	}


	@Override
	public void widgetSelected(SelectionEvent e) {
	

		
		try {
			Utils.getHandlerService(viewID).executeCommand(FileDialogAddHandler.ID, null);
			CheckDiscHandler.index = -1;
			Model.getSettingsBean().setManagerMode(false);
			Utils.getHandlerService(viewID).executeCommand(CheckDiscHandler.ID, null);
			Utils.getHandlerService(viewID).executeCommand(UpdateCoverHandler.ID, null);

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
