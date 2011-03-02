package jwbfs.ui.handlers;

import jwbfs.model.ModelStore;
import jwbfs.model.beans.GameBean;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;


public class FileDialogImportHandler extends AbstractHandler {
	

	public FileDialogImportHandler() {
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {

		GameBean bean = (GameBean) ModelStore.getSelectedGame();
		if(bean != null){

		FileDialog d = new FileDialog(new Shell()) ;


			d.setFilterExtensions(new String[]{"*.iso;*.ISO;*.wbfs;*.WBFS"});	

		
//			String oldValue = bean.getFilePath();
			String line = d.open();
			
			//If cancel, set the old value
			if(line == null || line.equals("")){
				return false;
			}
			
			bean.setFilePath(line);			
		}
		

		return true;
	}
}
