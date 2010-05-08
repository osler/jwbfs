package jwbfs.ui.exceptions;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

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
