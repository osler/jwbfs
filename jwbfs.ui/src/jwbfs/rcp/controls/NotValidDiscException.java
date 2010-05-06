package jwbfs.rcp.controls;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class NotValidDiscException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1423304595691207987L;

	public NotValidDiscException() {
	MessageBox msg = new MessageBox(new Shell(), SWT.ERROR);
	msg.setMessage("Select a valid wii Disc");
	msg.setText("Error");
	msg.open();
	}
}
