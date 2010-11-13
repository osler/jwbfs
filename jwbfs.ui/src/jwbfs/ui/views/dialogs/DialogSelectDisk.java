package jwbfs.ui.views.dialogs;

import java.io.File;
import java.util.ArrayList;

import jwbfs.model.Model;
import jwbfs.model.beans.SettingsBean;
import jwbfs.model.utils.FileUtils;
import jwbfs.ui.utils.CoverUtils;
import jwbfs.ui.views.WidgetCreator;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Shell;

public class DialogSelectDisk extends Dialog{

	ArrayList<File> disks;
	
	public DialogSelectDisk(Shell parentShell) {
		super(parentShell);
		
	}

	@Override
	public boolean close() {
		

		
		CoverUtils.setCoversPathFromDiskPath();
				
	
		return super.close();
	}

	@Override
	protected Control createDialogArea(Composite parent) {
	
		Composite mainComposite = WidgetCreator.createComposite(parent);
		disks = FileUtils.getDiskAvalaible();


		
		WidgetCreator.createLabel(mainComposite, "Select a Disk");
		final Combo combo = WidgetCreator.createCombo(mainComposite, new String[]{""}, Model.getSettingsBean(), "diskPath");

		fillComboValues(combo);
		
		Button other = WidgetCreator.createButton(mainComposite, "Other folder");
		
		other.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog d = new DirectoryDialog(new Shell());
	
					String open = d.open();
					combo.add(open);
					int numItems = combo.getItemCount();
					for(int x=0;x<numItems;x++){
						String item = combo.getItem(x);
						if(item.equals(open)){
							combo.select(x);
						}
					}
					close();
//					Model.getSettingsBean().setDiskPath(open);
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		return parent;
	}

	private void fillComboValues(Combo combo) {
		
		String[] items = new String[disks.size()];
		for(int x =0;x<disks.size();x++){
			items[x] = disks.get(x).getAbsolutePath();
			combo.add(items[x]);
		}
		
		
		
	}

}
