package jwbfs.ui.handlers;

import java.io.File;

import jwbfs.model.ModelStore;
import jwbfs.model.beans.GameBean;
import jwbfs.model.utils.FileUtils;
import jwbfs.ui.utils.GuiUtils;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public class DeleteFileHandler extends AbstractHandler {

	public DeleteFileHandler() {
	}


	public Object execute(ExecutionEvent event) throws ExecutionException {

		GameBean bean = (GameBean) ModelStore.getSelectedGame();
		if(bean != null){

			boolean res = GuiUtils.showConfirmDialog("Delete '"+bean.getTitle()+"' ?");

		
				
				return res;
		}

		

		return false;
	}
}
