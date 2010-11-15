package jwbfs.ui.listeners.mainView;

import jwbfs.model.Model;
import jwbfs.model.utils.CoreConstants;
import jwbfs.ui.utils.GuiUtils;
import jwbfs.ui.utils.PlatformUtils;

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
		
		PlatformUtils.getHandlerService(viewID).executeCommand(CoreConstants.COMMAND_GAMELIST_UPDATE_ID, null);

		Model.getExportGameBean().clean();

		} catch (Exception ex) {
			GuiUtils.showInfo(ex.getMessage(),SWT.ERROR);
			ex.printStackTrace();
			throw new RuntimeException("Command not Found");
		}
	
	}


}
