package jwbfs.ui.handlers;

import jwbfs.model.Model;
import jwbfs.model.beans.GameBean;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;


public class FileDialogAddHandler extends AbstractHandler {
	

	public static final String ID = "fileDialog";

	public FileDialogAddHandler() {
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {

		GameBean bean = (GameBean) Model.getConvertGameBean();
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
			
			bean.setFilePath(line);			
		}
		

		return null;
	}
}
