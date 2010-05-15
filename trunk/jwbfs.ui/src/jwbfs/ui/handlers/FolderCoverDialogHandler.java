package jwbfs.ui.handlers;

import jwbfs.model.Model;
import jwbfs.model.beans.GameBean;
import jwbfs.model.beans.SettingsBean;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Shell;

public class FolderCoverDialogHandler extends AbstractHandler {


	public static final String ID = "folderCoverDialog";

	public FolderCoverDialogHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {

		SettingsBean bean = (SettingsBean) Model.getBeans().get(SettingsBean.INDEX);
		if(bean != null){

		DirectoryDialog d = new DirectoryDialog(new Shell());
		
		if(bean != null){
			String open = d.open();
			bean.setCoverPath(open);
		}

		
			
		}
		
		return null;
	}
}
