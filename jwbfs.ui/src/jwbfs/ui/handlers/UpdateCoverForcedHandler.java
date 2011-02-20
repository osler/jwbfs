package jwbfs.ui.handlers;

import jwbfs.model.ModelStore;
import jwbfs.model.utils.CoreConstants;
import jwbfs.ui.utils.GuiUtils;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public class UpdateCoverForcedHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		ModelStore.getSettingsBean().getCoverSettings().setUpdateCover(true);
		
		Boolean ret = (Boolean) GuiUtils.executeCommand(
				CoreConstants.VIEW_DISK_0_ID, 
				CoreConstants.COMMAND_COVER_UPDATE_ID,
				null,
				true);

		return ret;
	}

}
