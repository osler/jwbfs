package jwbfs.ui.exceptions;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class FileNotSelectedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileNotSelectedException() {

		MessageBox msg = new MessageBox(new Shell(), SWT.ERROR);
		msg.setMessage("Select a File");
		msg.setText("Error");
		msg.open();

	}

	
}
