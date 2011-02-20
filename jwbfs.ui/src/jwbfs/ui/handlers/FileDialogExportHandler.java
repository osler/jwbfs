package jwbfs.ui.handlers;

import jwbfs.model.ModelStore;
import jwbfs.model.beans.GameBean;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;


public class FileDialogExportHandler extends AbstractHandler {

	public FileDialogExportHandler() {
	}


	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		GameBean bean = ModelStore.getExportGameBean();
		bean.clean();

		if(bean != null){

		FileDialog d = new FileDialog(new Shell()) ;

			d.setFilterExtensions(new String[]{"*.iso;*.ISO;*.wbfs;*.WBFS"});	

			String line = d.open();
			
			//If cancel, set the old value
			if(line == null || line.equals("")){
				return false;
			}
			
			if(!line.endsWith(".iso")){
				line = line+".iso";
			}
			bean.setFilePath(line);			
		}
		

		return true;
	}
}
