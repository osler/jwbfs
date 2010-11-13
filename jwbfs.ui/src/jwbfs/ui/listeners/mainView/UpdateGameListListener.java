package jwbfs.ui.listeners.mainView;

import jwbfs.ui.handlers.UpdateGameListHandler;
import jwbfs.ui.utils.GuiUtils;
import jwbfs.ui.utils.Utils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class UpdateGameListListener extends SelectionAdapter {

	private String viewID;


	public UpdateGameListListener(String viewID){
		this.viewID = viewID;
	}
	
	@Override
	public void widgetSelected(SelectionEvent e) {

		
		try {
		
		Utils.getHandlerService(viewID).executeCommand(UpdateGameListHandler.ID, null);
		

		} catch (Exception ex) {
			GuiUtils.showInfo(ex.getMessage(),SWT.ERROR);
			ex.printStackTrace();
			throw new RuntimeException("Command not Found");
		}
	
	}


}
