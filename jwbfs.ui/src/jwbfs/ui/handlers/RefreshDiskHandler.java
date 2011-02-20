package jwbfs.ui.handlers;

import java.io.File;
import java.util.LinkedHashMap;

import jwbfs.model.ModelStore;
import jwbfs.model.utils.CoreConstants;
import jwbfs.ui.utils.CoverUtils;
import jwbfs.ui.utils.GuiUtils;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public class RefreshDiskHandler extends AbstractHandler {


	public RefreshDiskHandler() {
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {

		String diskID = event.getParameter("diskID");
		
		if(diskID.trim().equals("activeDiskID")){
			diskID = GuiUtils.getActiveViewID();
		}
		
		if(ModelStore.getDiskPath(diskID).trim().equals("")
				|| !new File(ModelStore.getDiskPath(diskID)).exists()){

			GuiUtils.executeCommand(
					diskID, 
					CoreConstants.COMMAND_FOLDER_DISK_DIALOG_ID, 
					null);
		}else{				
			CoverUtils.setCoversPathFromDiskPath();	
		}


		LinkedHashMap<String,String> parametri = new LinkedHashMap<String,String>();
		parametri.put("diskID",diskID);
		GuiUtils.executeParametrizedCommand(CoreConstants.COMMAND_GAMELIST_UPDATE_ID,parametri,null);
		
		GuiUtils.getManagerTableViewer(diskID).setInput(ModelStore.getGames(diskID));
		
		GuiUtils.getManagerTableViewer(diskID).refresh();
		return true;
	}
}
