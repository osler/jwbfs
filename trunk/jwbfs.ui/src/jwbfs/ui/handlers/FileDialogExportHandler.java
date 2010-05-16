package jwbfs.ui.handlers;

import jwbfs.model.Model;
import jwbfs.model.beans.GameBean;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;


public class FileDialogExportHandler extends AbstractHandler {
	
	public static final String ID = "fileDialogExport";

	public FileDialogExportHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		

		GameBean bean = Model.getConvertGameBean();
		if(bean != null){

		FileDialog d = new FileDialog(new Shell()) ;

//		if(bean.isIsoToWbfs() && !bean.isWbfsToIso()){
			d.setFilterExtensions(new String[]{"*.iso;*.ISO;*.wbfs;*.WBFS"});	
//		}else {
//			d.setFilterExtensions(new String[]{"*.wbfs","*.WBFS"});	
//		}
		
			String oldValue = bean.getFilePath();
			String line = d.open();
			
			//If cancel, set the old value
			if(line == null || line.equals("")){
				line = oldValue;
			}
			
			if(!line.endsWith(".iso")){
				line = line+".iso";
			}
			bean.setFilePath(line);			
		}
		

		return null;
	}
}
