package jwbfs.ui.exceptions;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class NotCorrectDiscFormatException extends Exception {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1693788657710440187L;

	public NotCorrectDiscFormatException() {
	MessageBox msg = new MessageBox(new Shell(), SWT.ERROR);
	msg.setMessage("Select a correct wii Disc format");
	msg.setText("Error");
	msg.open();
	}
}
