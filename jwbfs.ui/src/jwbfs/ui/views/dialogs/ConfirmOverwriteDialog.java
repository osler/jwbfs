package jwbfs.ui.views.dialogs;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

public class ConfirmOverwriteDialog extends FileExistDialog {

	public ConfirmOverwriteDialog(String text) {
		super(new Shell());
		this.text = text;
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.YES_ID, 
				IDialogConstants.YES_LABEL, false);
		createButton(parent, IDialogConstants.YES_TO_ALL_ID,
				IDialogConstants.YES_TO_ALL_LABEL, false);
		createButton(parent, IDialogConstants.NO_ID,
				IDialogConstants.NO_LABEL, true);
		createButton(parent, IDialogConstants.NO_TO_ALL_ID,
				IDialogConstants.NO_TO_ALL_LABEL, false);
	}
	
	protected void buttonPressed(int buttonId) {
//		if (IDialogConstants.YES_ID == buttonId) {
//	
//		} else if (IDialogConstants.CANCEL_ID == buttonId) {
//		}
		setReturnCode(buttonId);
		close();
	}

}
