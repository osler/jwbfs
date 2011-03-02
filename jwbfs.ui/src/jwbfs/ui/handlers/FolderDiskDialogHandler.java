package jwbfs.ui.handlers;

import jwbfs.model.ModelStore;
import jwbfs.model.beans.SettingsBean;
import jwbfs.ui.utils.GuiUtils;
import jwbfs.ui.views.dialogs.DialogSelectDisk;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.Shell;

public class FolderDiskDialogHandler extends AbstractHandler {

	public FolderDiskDialogHandler() {
	}


	public Object execute(ExecutionEvent event) throws ExecutionException {

		SettingsBean bean =  ModelStore.getSettingsBean();
		if(bean != null){

			DialogSelectDisk sel = new DialogSelectDisk(new Shell(),GuiUtils.getActiveViewID());
			int ret = sel.open();
			if(ret == DialogSelectDisk.CANCEL){
				return false;
			}
						
		}
		
		return true;
	}
}
