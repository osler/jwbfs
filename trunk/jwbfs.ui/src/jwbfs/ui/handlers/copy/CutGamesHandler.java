package jwbfs.ui.handlers.copy;

import java.util.ArrayList;

import jwbfs.model.ModelStore;
import jwbfs.model.beans.CopyBean;
import jwbfs.model.beans.GameBean;

public class CutGamesHandler extends CopyGamesHandler {


	public CutGamesHandler() {
	}

	protected void addSelectedGamesToModel(ArrayList<GameBean> gamesTo,
			String diskFrom) {

		CopyBean copyBean = new CopyBean(gamesTo,diskFrom);
		copyBean.setCut(true);

		ModelStore.setCopyBean(copyBean);
	}
}
