package jwbfs.ui.handlers;

import jwbfs.model.Model;
import jwbfs.model.beans.ProcessBean;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Shell;

public class FolderDialogHandler extends AbstractHandler {


	public static final String ID = "folderDialog";

	public FolderDialogHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {

		ProcessBean bean = (ProcessBean) Model.getTabs().get(ProcessBean.INDEX);
		if(bean != null){

		DirectoryDialog d = new DirectoryDialog(new Shell());
		
		if(bean != null){
			String open = d.open();
			bean.setFolderPath(open);
		}

		
			
		}
		
		return null;
	}
}
