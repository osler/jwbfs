package jwbfs.ui.handlers;

import jwbfs.model.Model;
import jwbfs.model.beans.SettingsBean;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Shell;

public class FolderDiskDialogHandler extends AbstractHandler {


	public static final String ID = "folderDialog";

	public FolderDiskDialogHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {

		SettingsBean bean =  Model.getSettingsBean();
		if(bean != null){

		DirectoryDialog d = new DirectoryDialog(new Shell());
		
		if(bean != null){
			String open = d.open();
			bean.setDiskPath(open);
		}

		
			
		}
		
		return null;
	}
}
