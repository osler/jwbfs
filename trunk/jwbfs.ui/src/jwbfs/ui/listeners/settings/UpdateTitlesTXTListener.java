package jwbfs.ui.listeners.settings;

import jwbfs.model.utils.PlatformUtils;
import jwbfs.ui.handlers.UpdateTitlesTXTHandler;

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
		
			PlatformUtils.getHandlerService(viewID).executeCommand(UpdateTitlesTXTHandler.ID, null);
		
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
