package jwbfs.ui.exceptions;

import java.io.File;

import jwbfs.model.utils.CoreConstants;
import jwbfs.model.utils.FileUtils;
import jwbfs.ui.utils.GuiUtils;
import jwbfs.ui.utils.PlatformUtils;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.swt.widgets.Display;

public class WBFSException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int FILE_EXISTS = 1;
	public static final int USER_CANCEL = 99;
	public static final int FOLDER_EXISTS = 2;

	public WBFSException(String line) {
	
		openDialogError(line);
		
	}

	public WBFSException(String line,int type) {
		
		if(type == USER_CANCEL){
			openCancelDialog(line);
			return;
		}else if(type == FILE_EXISTS){
			openFileExistsDialog(line);
			return;
		}else if(type == FOLDER_EXISTS){
			openFolderExistsDialog(line);
			return;
		}else{
			openDialogError(line);
		}
		
		
	}

	private void openFolderExistsDialog(String line) {
		

		boolean confirm = GuiUtils.showConfirmDialog(line+"\n DO YOU WANT TO DELETE FILES AND PARENT FOLDER?",true);
		if(confirm){
			String del = line.split("exists:")[1].trim();
			confirm = GuiUtils.showConfirmDialog("Are you Sure?",true);
			if(confirm){
				new File(del).delete();
			
				//LAUNCH AGAIN THE PROCESS
				try {
					PlatformUtils.getHandlerService(CoreConstants.MAINVIEW_ID).executeCommand(CoreConstants.COMMAND_TOWBFS_ID, null);
				} catch (ExecutionException e) {
					e.printStackTrace();
					throw new RuntimeException("Command not Found");
				} catch (NotDefinedException e) {
					e.printStackTrace();
					throw new RuntimeException("Command not Found");
				} catch (NotEnabledException e) {
					e.printStackTrace();
					throw new RuntimeException("Command not Found");
				} catch (NotHandledException e) {
					e.printStackTrace();
					throw new RuntimeException("Command not Found");
				}
			}
			
			System.out.println("removing: "+del);
		}
	

}

	private void openCancelDialog(String line) {
		
		final String hhh = line;
		Display.getDefault().asyncExec(								
			new Runnable(){
				public void run() {
					boolean confirm = GuiUtils.showConfirmDialog(hhh+"\n DO YOU WANT TO DELETE TEMP FILES");
					if(confirm){
						String del = hhh.split("exists:")[1].trim();
						confirm = GuiUtils.showConfirmDialog("Are you Sure?");
						if(confirm){
							new File(del).delete();
						}
						
						System.out.println("removing: "+del);
					}
				}
			}
		);
	}
	
	private void openFileExistsDialog(String line) {
		

					boolean confirm = GuiUtils.showConfirmDialog(line+"\n DO YOU WANT TO DELETE IT?",true);
					if(confirm){
						String del = line.split("exists:")[1].trim();
						confirm = GuiUtils.showConfirmDialog("Are you Sure?",true);
						if(confirm){
							new File(del).delete();
						
							//LAUNCH AGAIN THE PROCESS
							try {
								PlatformUtils.getHandlerService(CoreConstants.MAINVIEW_ID).executeCommand(CoreConstants.COMMAND_TOWBFS_ID, null);
							} catch (ExecutionException e) {
								e.printStackTrace();
								throw new RuntimeException("Command not Found");
							} catch (NotDefinedException e) {
								e.printStackTrace();
								throw new RuntimeException("Command not Found");
							} catch (NotEnabledException e) {
								e.printStackTrace();
								throw new RuntimeException("Command not Found");
							} catch (NotHandledException e) {
								e.printStackTrace();
								throw new RuntimeException("Command not Found");
							}
						}
						
						System.out.println("removing: "+del);
					}
				

	}

	private void openDialogError(String line) {
	
		final String hhh = line;
		Display.getDefault().asyncExec(								
			new Runnable(){
				public void run() {
					GuiUtils.showError("Error: "+hhh);
				}
			}
		);
		
	}
}
