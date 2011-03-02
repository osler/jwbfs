package jwbfs.ui.handlers;

import jwbfs.model.ModelStore;
import jwbfs.model.beans.DiskBean;
import jwbfs.model.utils.CoreConstants;
import jwbfs.ui.utils.GuiUtils;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public class UpdateCoverForcedHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		DiskBean diskBean =  ModelStore.getDisk(GuiUtils.getActiveViewID());
	
		diskBean.getCoverSettings().setUpdateCover(true);
		
		Boolean ret = (Boolean) GuiUtils.executeCommand(
				GuiUtils.getActiveViewID(), 
				CoreConstants.COMMAND_COVER_UPDATE_ID,
				null,
				true);

		return ret;
	}

}
