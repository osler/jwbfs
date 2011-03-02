package jwbfs.ui.handlers.copy;

import java.util.ArrayList;

import jwbfs.model.ModelStore;
import jwbfs.model.beans.CopyBean;
import jwbfs.model.beans.GameBean;
import jwbfs.ui.utils.GuiUtils;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public class CopyGamesHandler extends AbstractHandler {


	public CopyGamesHandler() {
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
	
		String diskFrom = event.getParameter("diskFrom").trim();
		
		if(diskFrom.trim().equals("activeID")){
			diskFrom = GuiUtils.getActiveViewID();
		}
		
		ArrayList<GameBean> gamesTo = new ArrayList<GameBean>();
		try {
			gamesTo = GuiUtils.getSelectedGames(diskFrom);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		if(gamesTo.size() == 0){
			GuiUtils.showError("Select at least a game");
			addSelectedGamesToModel(gamesTo,diskFrom);
			return false;
		}
		
		addSelectedGamesToModel(gamesTo,diskFrom);
	
		return true;
	}

	protected void addSelectedGamesToModel(ArrayList<GameBean> gamesTo,
			String diskFrom) {
		
		CopyBean copyBean = new CopyBean(gamesTo,diskFrom);
		
		ModelStore.setCopyBean(copyBean);
	}


}
