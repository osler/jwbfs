package jwbfs.ui.views.dialogs;

import jwbfs.ui.views.WidgetCreator;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class FileExistDialog extends Dialog {

	protected String text;

	public FileExistDialog(String line, Shell parentShell) {
		super(parentShell);
		String message =  "Do you want to delete the file?";
		text = line+"\n"+message;
	
	}

	public FileExistDialog(Shell shell) {
		super(shell);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite comp = WidgetCreator.createComposite(parent);
		
		WidgetCreator.createLabel(comp, text);
		
		return comp;
	}

}
