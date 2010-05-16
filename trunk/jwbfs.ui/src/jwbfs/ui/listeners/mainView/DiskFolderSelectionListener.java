package jwbfs.ui.listeners.mainView;

import jwbfs.ui.handlers.FolderDiskDialogHandler;
import jwbfs.ui.handlers.UpdateGameListHandler;
import jwbfs.ui.utils.Utils;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class DiskFolderSelectionListener extends SelectionAdapter {

	private String viewID;

	public DiskFolderSelectionListener(String viewID){
		this.viewID = viewID;
	}
	
	@Override
	public void widgetSelected(SelectionEvent e) {

		System.out.println("Launching Folder Selection Dialog");
		try {
			Utils.getHandlerService(viewID).executeCommand(FolderDiskDialogHandler.ID, null);
			Utils.getHandlerService(viewID).executeCommand(UpdateGameListHandler.ID, null);
			
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
