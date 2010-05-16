package jwbfs.ui.listeners.coverView;

import jwbfs.ui.handlers.FolderCoverDialogHandler;
import jwbfs.ui.handlers.FolderDiskDialogHandler;
import jwbfs.ui.utils.Utils;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class FolderCoverDialogListener extends SelectionAdapter {

	private String viewID;

	public FolderCoverDialogListener(String viewID){
		this.viewID = viewID;
	}
	
	@Override
	public void widgetSelected(SelectionEvent e) {

		System.out.println("Launching Cover Folder Selection Dialog");
		try {
			Utils.getHandlerService(viewID).executeCommand(FolderCoverDialogHandler.ID, null);
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
