package jwbfs.ui.handlers.copy;

import java.util.LinkedHashMap;

import jwbfs.model.ModelStore;
import jwbfs.model.utils.CoreConstants;
import jwbfs.ui.utils.GuiUtils;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;

public class CopyPasteGamesHandler extends AbstractHandler {

	String diskTO ="";
	String diskFrom ="";
	public CopyPasteGamesHandler() {
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {

		diskTO = event.getParameter("diskTo").trim();
		diskFrom = event.getParameter("diskFrom").trim();
		
		if(diskTO.equals("activeID")){
			diskTO = GuiUtils.getActiveViewID();
		}
		if(diskFrom.equals("activeID")){
			diskFrom = GuiUtils.getActiveViewID();
		}
		
		if(ModelStore.getDisk(diskTO).getDiskPath().trim().equals("")){
			return Status.OK_STATUS;
		}
		
		LinkedHashMap<String, String> parameter = new LinkedHashMap<String, String>();
		parameter.put("diskFrom", diskFrom);
		parameter.put("diskTo", diskTO);

		GuiUtils.executeParametrizedCommand(CoreConstants.COMMAND_COPY_GAMES_ID, parameter, null);

		GuiUtils.executeParametrizedCommand(CoreConstants.COMMAND_PASTE_GAMES_ID, parameter, null, true);
		
		GuiUtils.executeCommand(diskFrom,CoreConstants.COMMAND_REFRESH_ALL_DISK_ID,null,true);	
		
		return Status.OK_STATUS;
	}
	
}
