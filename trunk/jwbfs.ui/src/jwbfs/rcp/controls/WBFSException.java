package jwbfs.rcp.controls;

import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.internal.progress.ErrorInfo;

public class WBFSException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WBFSException(String line) {
		MessageBox msg = new MessageBox(new Shell(), SWT.ERROR);
		msg.setMessage(line);
		msg.setText("Error");
		msg.open();
		
	}

}
