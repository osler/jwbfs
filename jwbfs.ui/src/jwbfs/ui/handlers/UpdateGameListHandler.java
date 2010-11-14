package jwbfs.ui.handlers;

import jwbfs.model.Model;
import jwbfs.model.beans.SettingsBean;
import jwbfs.ui.utils.GuiUtils;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public class UpdateGameListHandler extends AbstractHandler {
	
	private SettingsBean settingsBean;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		settingsBean = (SettingsBean) Model.getBeans().get(SettingsBean.INDEX);

		Model.setGames(Model.listGames(settingsBean.getDiskPath()));

		GuiUtils.getManagerTableViewer().refresh();
		GuiUtils.setDefaultCovers();

		return null;
	}



}
