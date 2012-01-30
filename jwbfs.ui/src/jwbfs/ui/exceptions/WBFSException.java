package jwbfs.ui.exceptions;

import jwbfs.ui.utils.GuiUtils;

public class WBFSException extends Exception {
	
	private static final long serialVersionUID = 1L;
	public static final int FILE_EXISTS = 1;
	public static final int USER_CANCEL = 99;
	public static final int FOLDER_EXISTS = 2;

	public WBFSException() {
		System.out.println("a WBFS exception is thrown: "+this.toString() );
	}
	
	public WBFSException(String line) {
		GuiUtils.showError("error: "+line,true);
	}

}
