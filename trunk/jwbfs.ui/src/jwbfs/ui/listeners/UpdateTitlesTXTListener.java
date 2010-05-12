package jwbfs.ui.listeners;

import jwbfs.model.Model;
import jwbfs.model.beans.SettingsBean;
import jwbfs.ui.handlers.UpdateCoverHandler;
import jwbfs.ui.handlers.UpdateTitlesTXTHandler;
import jwbfs.ui.utils.Utils;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class UpdateTitlesTXTListener extends SelectionAdapter {

	private String viewID;
	
	public UpdateTitlesTXTListener(String viewID) {
		this.viewID = viewID;
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		try {
		
			Utils.getHandlerService(viewID).executeCommand(UpdateTitlesTXTHandler.ID, null);
		
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
