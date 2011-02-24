package jwbfs.ui.listeners.mainView;

import java.util.LinkedHashMap;

import jwbfs.model.utils.CoreConstants;
import jwbfs.model.utils.PlatformUtils;
import jwbfs.ui.utils.GuiUtils;

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
			PlatformUtils.getHandlerService(viewID).executeCommand(CoreConstants.COMMAND_FOLDER_DISK_DIALOG_ID, null);
			
			LinkedHashMap<String,String> parametri = new LinkedHashMap<String,String>();
			parametri.put("diskID",viewID);
			GuiUtils.executeParametrizedCommand(CoreConstants.COMMAND_GAMELIST_UPDATE_ID,parametri,null);
			
			
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
