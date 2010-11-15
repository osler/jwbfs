package jwbfs.ui.exceptions;

import java.io.File;

import jwbfs.model.utils.FileUtils;
import jwbfs.ui.utils.GuiUtils;

import org.eclipse.swt.widgets.Display;

public class MonitorCancelException extends Exception {

	private static final long serialVersionUID = 4831703760827894063L;

	File file;
	String line;
	
	public MonitorCancelException(String line, File file) {
		this.line = line;
		this.file = file;
		openCancelDialog();
	}

	private void openCancelDialog() {
		

		Display.getDefault().asyncExec(								
			new Runnable(){
				public void run() {
					boolean confirm = GuiUtils.showConfirmDialog(line+"\n DO YOU WANT TO DELETE TEMP FILES");
					if(confirm){
						
						confirm = GuiUtils.showConfirmDialog("Are you Sure?");
						if(confirm){
							FileUtils.deleteWBFSFileAndTXT(file);
						}
						
						System.out.println("removing: "+file.getAbsolutePath());
					}
				}
			}
		);
	}

}
