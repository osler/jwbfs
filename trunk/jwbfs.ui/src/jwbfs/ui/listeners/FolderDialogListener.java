package jwbfs.ui.listeners;

import jwbfs.ui.handlers.FolderDialogHandler;
import jwbfs.ui.utils.Utils;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class FolderDialogListener extends SelectionAdapter {

	private String viewID;

	public FolderDialogListener(String viewID){
		this.viewID = viewID;
	}
	
	@Override
	public void widgetSelected(SelectionEvent e) {

		System.out.println("Launching Folder Selection Dialog");
		try {
			Utils.getHandlerService(viewID).executeCommand(FolderDialogHandler.ID, null);
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
