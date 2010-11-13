package jwbfs.ui.handlers;

import jwbfs.model.Model;
import jwbfs.model.beans.SettingsBean;
import jwbfs.model.utils.Constants;
import jwbfs.ui.utils.GuiUtils;
import jwbfs.ui.utils.Utils;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;

public class UpdateGameListHandler extends AbstractHandler {
	
	private SettingsBean settingsBean;
	public static final String ID = "updateGameList";

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		settingsBean = (SettingsBean) Model.getBeans().get(SettingsBean.INDEX);

//	    ((CoverView) GuiUtils.getView(CoverView.ID)).getProgressBar().setMaximum(getProgress());
		//((CoverView) GuiUtils.getView(CoverView.ID)).getProgressBar().setSelection(0);

		try {

			Model.setGames(Model.listGames(settingsBean.getDiskPath()));

			Utils.getHandlerService(Constants.MAINVIEW_ID).executeCommand(Constants.COMMAND_CHECKDISK_ID, null);

			GuiUtils.getManagerTableViewer().refresh();

		} catch (NotDefinedException e) {
			e.printStackTrace();
		} catch (NotEnabledException e) {
			e.printStackTrace();
		} catch (NotHandledException e) {
			e.printStackTrace();
		}
	
		return null;
	}



}
