package jwbfs.ui.handlers;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import jwbfs.model.Model;
import jwbfs.model.beans.ConvertTab;
import jwbfs.ui.controls.ErrorHandler;
import jwbfs.ui.exceptions.WBFSException;
import jwbfs.ui.utils.Utils;
import jwbfs.ui.views.MainView;
import jwbfs.ui.views.tabs.ConvertTabView;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class FileDialogHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public FileDialogHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		
		

		ConvertTab bean = (ConvertTab) Model.getTabs().get(ConvertTab.INDEX);
		if(bean != null){

		FileDialog d = new FileDialog(new Shell()) ;

		if(bean.isIsoToWbfs()){
			d.setFilterExtensions(new String[]{"*.iso","*.ISO"});	
		}else {
			d.setFilterExtensions(new String[]{"*.wbfs","*.WBFS"});	
		}
		

			String line = d.open();
			bean.setFilePath(line);
			  try {
				  String[] info = 
						(String[]) Utils.getHandlerService()
						.executeCommand("jwbfs.ui.commands.checkDisk", null);

				bean.setId(info[0]);
				bean.setTitle(info[1]);
				bean.setScrubGb(info[2]);
				bean.setFilePath(line);
			
				
			} catch (NotDefinedException e) {
					e.printStackTrace();
			} catch (NotEnabledException e) {
				e.printStackTrace();
			} 	catch (NotHandledException e) {
				e.printStackTrace();
			}
			
		}
		

		return null;
	}
}
