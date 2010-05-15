package jwbfs.ui.handlers;

import jwbfs.model.Model;
import jwbfs.model.beans.ProcessBean;
import jwbfs.model.beans.SettingsBean;
import jwbfs.ui.utils.GuiUtils;
import jwbfs.ui.utils.Utils;
import jwbfs.ui.views.ManagerView;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;

public class UpdateGameListHandler extends AbstractHandler {
	private ProcessBean processBean;
	private SettingsBean settingsBean;
	public static final String ID = "updateGameList";

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		processBean = (ProcessBean) Model.getTabs().get(ProcessBean.INDEX);
		settingsBean = (SettingsBean) Model.getTabs().get(SettingsBean.INDEX);

//	    ((CoverView) GuiUtils.getView(CoverView.ID)).getProgressBar().setMaximum(getProgress());
		//((CoverView) GuiUtils.getView(CoverView.ID)).getProgressBar().setSelection(0);

		try {

			Model.setGames(Model.listGames(settingsBean.getDiskPath()));

			((SettingsBean) Model.getTabs().get(SettingsBean.INDEX)).setManagerMode(true);

			Utils.getHandlerService(ManagerView.ID).executeCommand(CheckDiscHandler.ID, null);

			GuiUtils.getManagerTableViewer().refresh();
//			((ManagerView)GuiUtils.getView(ManagerView.ID)).getTv().setInput(Model.getGames());
		} catch (NotDefinedException e) {
			e.printStackTrace();
		} catch (NotEnabledException e) {
			e.printStackTrace();
		} catch (NotHandledException e) {
			e.printStackTrace();
		}
	
		return null;
	}

	private int getProgress() {

		int tot = 1;
		
		if(settingsBean.isCover3D()){
			tot++;
		}
		if(settingsBean.isCoverDiscs()){
			tot++;
		}
	
		return tot;
	}


}
