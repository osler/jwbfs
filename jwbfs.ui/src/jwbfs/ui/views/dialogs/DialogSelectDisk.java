package jwbfs.ui.views.dialogs;

import java.io.File;
import java.util.ArrayList;

import jwbfs.model.Model;
import jwbfs.model.utils.FileUtils;
import jwbfs.ui.views.WidgetCreator;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class DialogSelectDisk extends Dialog{

	public DialogSelectDisk(Shell parentShell) {
		super(parentShell);
		
	}

	@Override
	protected Control createDialogArea(Composite parent) {
	
		Composite mainComposite = WidgetCreator.createComposite(parent);
		ArrayList<File> disks = FileUtils.getDiskAvalaible();

		String[] items = new String[disks.size()];
		for(int x =0;x<disks.size();x++){
			items[x] = disks.get(x).getAbsolutePath();
		}
		
		WidgetCreator.createLabel(mainComposite, "Select a Disk");
		WidgetCreator.createCombo(mainComposite, items, Model.getSettingsBean(), "diskPath");

		return parent;
	}

}
