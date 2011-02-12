package jwbfs.ui.views.dialogs;

import java.io.File;
import java.util.ArrayList;

import jwbfs.model.Model;
import jwbfs.model.beans.CoverSettings;
import jwbfs.model.utils.DiskContants;
import jwbfs.model.utils.FileUtils;
import jwbfs.ui.utils.CoverUtils;
import jwbfs.ui.utils.GuiUtils;
import jwbfs.ui.views.WidgetCreator;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

public class DialogSelectDisk extends Dialog{

	ArrayList<File> disks;
	private Combo combo;
	private ArrayList<File> customDisks = new ArrayList<File>();
	
	public DialogSelectDisk(Shell parentShell) {
		super(parentShell);
		
	}



	private boolean check() {
		CoverSettings coverSettings = Model.getSettingsBean().getCoverSettings();
		String testSubFolder = Model.getSettingsBean().getDiskPath()+File.separatorChar+DiskContants.WBFS_GAMES_FOLDER;
		
		boolean creatingFolders = false;
		
		//folder doesn't exists
		if(!Model.getSettingsBean().getDiskPath().endsWith(DiskContants.WBFS_GAMES_FOLDER)
				&& !new File(testSubFolder).exists()){
			FileUtils.createRootFolderStructures(Model.getSettingsBean().getDiskPath());
			creatingFolders = true;
		}
		//exists a subfolder with wbfs name
		if(new File(testSubFolder).exists()){
			//maybe selected root...propose a subfolder
			if(!creatingFolders){
			
			creatingFolders = GuiUtils.showConfirmDialog("The folder you chose contains a wbfs subfolder with possible games. Maybe you mean to use that?\n"+
					"("+testSubFolder+") which contains "+ new File(testSubFolder).list().length 
					+" files/folders");
			
			}
			
			if(creatingFolders){
				Model.getSettingsBean().setDiskPath(testSubFolder);
			}
		}
		
		//if no type selected	
		if(!coverSettings.isCoverTypeUSBLoaderGX() 
				&& !coverSettings.isCoverTypeUSBLoaderCFG()
				&& !coverSettings.isCoverTypeUSBLoaderWIIFLOW()){
			GuiUtils.showError("Please select an USB Loader");
			return false;
		}
	
		return true;
	}

	@Override
	protected void okPressed() {

		if(!check()){
			return;
		}
		
		CoverUtils.setCoversPathFromDiskPath();	

		super.okPressed();
	}

	@Override
	protected Control createDialogArea(Composite parent) {
	
		Composite mainComposite = WidgetCreator.createComposite(parent);
		GridLayout gl = new GridLayout();
		gl.numColumns = 1;
		mainComposite.setLayout(gl);
		
		createDiskGroup(mainComposite);
		
		createGroupUSBLoader(mainComposite);
		
		return parent;
	}

	private void createDiskGroup(final Composite mainComposite) {
		Group usbLoaderType = WidgetCreator.createGroup(mainComposite, "USB Loader covers path");

		WidgetCreator.createLabel(usbLoaderType, "Select a Disk");
		WidgetCreator.createLabel(usbLoaderType, "");
		
		combo = WidgetCreator.createCombo(usbLoaderType, new String[]{""}, Model.getSettingsBean(), "diskPath");
		Button reload = WidgetCreator.createButton(usbLoaderType, "reload");
		

		customDisks =new ArrayList<File>();
		customDisks.add(new File(Model.getSettingsBean().getDiskPath()));
		initDisksAvalaible();
		fillComboValues();

		Button other = WidgetCreator.createButton(usbLoaderType, "Other folder");
		
		other.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog d = new DirectoryDialog(new Shell());
	
					String open = d.open();
					if(open == null || open.trim().equals("")){
						return;
					}
					customDisks = new ArrayList<File>();
					customDisks.add(new File(open));
					
					initDisksAvalaible();
					fillComboValues();
					
					int numItems = combo.getItemCount();
					for(int x=0;x<numItems;x++){
						String item = combo.getItem(x);
						if(item.equals(open)){
							combo.select(x);
						}
					}
//					close();
//					Model.getSettingsBean().setDiskPath(open);
				
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
		});
			
			reload.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent e) {
					initDisksAvalaible();
					fillComboValues();
			
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					
				}
			});

	}



	private void createGroupUSBLoader(Composite mainComposite) {
		Group usbLoaderType = WidgetCreator
				.createGroup(mainComposite, "USB Loader covers path",3);

		WidgetCreator.createRadio(usbLoaderType, "USBLoaderGX", 
				Model.getSettingsBean().getCoverSettings(), "coverTypeUSBLoaderGX");
		
		WidgetCreator.createRadio(usbLoaderType, "Configurable USB Loader", 
				Model.getSettingsBean().getCoverSettings(), "coverTypeUSBLoaderCFG");
		
		WidgetCreator.createRadio(usbLoaderType, "Wiiflow", 
				Model.getSettingsBean().getCoverSettings(), "coverTypeUSBLoaderWIIFLOW");
		
	}



	private void initDisksAvalaible() {
		
		disks =new ArrayList<File>();
		disks = FileUtils.getDiskAvalaible();

	}



	private void fillComboValues() {
		combo.removeAll();
		String[] items = new String[disks.size()];
		for(int x =0;x<disks.size();x++){
			items[x] = disks.get(x).getAbsolutePath();
			combo.add(items[x]);
		}
		
		//CUSTOM VALUES
		for(int x =0;x<customDisks.size();x++){
			String item = customDisks.get(x).getAbsolutePath();
			combo.add(item);
		}
		
		if(combo.getItemCount() == 1){
			combo.select(0);
		}
	}

}
