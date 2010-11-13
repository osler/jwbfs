package jwbfs.ui.handlers;

import jwbfs.model.Model;
import jwbfs.model.beans.SettingsBean;
import jwbfs.ui.views.dialogs.DialogSelectDisk;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.Shell;

public class FolderDiskDialogHandler extends AbstractHandler {


	public static final String ID = "folderDialog";

	public FolderDiskDialogHandler() {
	}


	public Object execute(ExecutionEvent event) throws ExecutionException {

		SettingsBean bean =  Model.getSettingsBean();
		if(bean != null){

			DialogSelectDisk sel = new DialogSelectDisk(new Shell());
			sel.open();
						
		}
		
		return null;
	}
}
