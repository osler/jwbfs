package jwbfs.ui.exceptions;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import jwbfs.ui.utils.GuiUtils;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class WBFSFileExistsException extends WBFSException {

	private static final long serialVersionUID = 1L;
	private File file;
	private String line;

	public WBFSFileExistsException(String line,File file, IRunnableWithProgress operation) {
		this.file = file;
		this.line = line;
		
		openFolderExistsDialog(operation);
	}
	
	public WBFSFileExistsException(String line,File file) {
		this.file = file;
		this.line = line;
		
		openFolderExistsDialog();
	}
	
	public WBFSFileExistsException(String line, File file,
			Job operation) {
		this.file = file;
		this.line = line;
		
		openFolderExistsDialog();
	}

	boolean confirm = false;
	
	public boolean openFolderExistsDialog(final IRunnableWithProgress operation) {


		Display.getDefault().asyncExec(								
				new Runnable(){
					public void run() {

						String message =  "Do you want to delete the file?";
						confirm = GuiUtils.showConfirmDialog(line+"\n"+message);

						if(confirm){

							confirm = GuiUtils.showConfirmDialog("Are you Sure?");
							if(confirm){
								file.delete();
							} 

							System.out.println("removing: "+file);
							
							retryLastCommand(operation);
						}

					}
				});
		
		return confirm;


	}

	public boolean openFolderExistsDialog() {


		Display.getDefault().asyncExec(								
				new Runnable(){
					public void run() {

						String message =  "File exist, skipping.";
						GuiUtils.showError(line+"\n"+message);

					}
				});
		
		return confirm;


	}
	protected void retryLastCommand(IRunnableWithProgress operation) {
		if(operation == null){
			return;
		}
		Shell sh = new Shell();
		try {
			new ProgressMonitorDialog(sh).run(true, true, operation);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


}
