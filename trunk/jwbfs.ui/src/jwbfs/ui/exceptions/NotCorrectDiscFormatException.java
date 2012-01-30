package jwbfs.ui.exceptions;

import jwbfs.ui.utils.GuiUtils;

public class NotCorrectDiscFormatException extends Exception {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1693788657710440187L;

	public NotCorrectDiscFormatException() {
		GuiUtils.showError("Select a correct wii Disc format", true);
	}
}
