package jwbfs.ui.views.dialogs;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;

import jwbfs.i18n.Messages;
import jwbfs.model.ModelStore;
import jwbfs.model.beans.CoverPaths;
import jwbfs.model.beans.CoverSettings;
import jwbfs.model.beans.DiskBean;
import jwbfs.model.utils.DiskContants;
import jwbfs.model.utils.FileUtils;
import jwbfs.ui.utils.CoverUtils;
import jwbfs.ui.utils.GuiUtils;
import jwbfs.ui.views.WidgetCreator;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

public class DialogSelectDisk extends Dialog implements PropertyChangeListener{

	ArrayList<File> disks;
	private Combo combo;
	private ArrayList<File> customDisks = new ArrayList<File>();
	private String diskID;
	private DiskBean diskBean;
	
	public DialogSelectDisk(Shell parentShell, String diskID) {
		super(parentShell);
		this.diskID = diskID;
		diskBean = ModelStore.getDisk(diskID);
		
		diskBean.getCoverSettings().addPropertyChangeListener(this);
	}



	private boolean check() {
		
		String diskPath = diskBean.getDiskPath();
		
		CoverSettings coverSettings = diskBean.getCoverSettings();
		String testSubFolder = diskPath+File.separatorChar+DiskContants.WBFS_GAMES_FOLDER;
		
		boolean creatingFolders = false;
		
		//folder doesn't exists
		if(!diskPath.endsWith(DiskContants.WBFS_GAMES_FOLDER)
				&& !new File(testSubFolder).exists()){
			FileUtils.createRootFolderStructures(diskPath,diskID);
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
				
				diskBean.setDiskPath(testSubFolder);
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
		
		CoverUtils.setCoversPathFromDiskPath(diskID);	

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
		crateGroupCoverSettings(mainComposite);
		createGroupCoverPath(mainComposite);
		
		return parent;
	}

	private Button cover3dDownloadButton;
	private Button coverDiscDownloadButton;
	
	private void crateGroupCoverSettings(Composite mainComposite) {
		
		CoverSettings coverBean = diskBean.getCoverSettings();
		
		Group group = WidgetCreator.createGroup(mainComposite, Messages.settings_group_cover,2);
		WidgetCreator.createCheck(group, 
				Messages.settings_cover_enable, 
				coverBean, 
				"automaticCoverDownload",
				2,
				GridData.BEGINNING,
				GridData.BEGINNING);  
		WidgetCreator.createSeparator(group, 1, GridData.END, GridData.BEGINNING);
		cover3dDownloadButton = WidgetCreator.createCheck(group, 
				Messages.settings_cover_3d_download, 
				coverBean, 
				"cover3D",
				1,
				GridData.BEGINNING,
				GridData.BEGINNING);  
		WidgetCreator.createSeparator(group, 1, GridData.END, GridData.BEGINNING);
		coverDiscDownloadButton = WidgetCreator.createCheck(group, 
				Messages.settings_cover_disc_download, 
				coverBean, 
				"coverDiscs",
				1,
				GridData.BEGINNING,
				GridData.BEGINNING);  

	}



	private void createDiskGroup(final Composite mainComposite) {
		
		String diskPath = diskBean.getDiskPath();
		
		Group usbLoaderType = WidgetCreator.createGroup(mainComposite, "USB Loader covers path");

		WidgetCreator.createLabel(usbLoaderType, "Select a Disk");
		WidgetCreator.createLabel(usbLoaderType, "");
		
		combo = WidgetCreator.createCombo(usbLoaderType, new String[]{""}, diskBean, "diskPath");
		Button reload = WidgetCreator.createButton(usbLoaderType, "reload");
		

		customDisks =new ArrayList<File>();
		customDisks.add(new File(diskPath));
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
				diskBean.getCoverSettings(), "coverTypeUSBLoaderGX");
		
		WidgetCreator.createRadio(usbLoaderType, "Configurable USB Loader", 
				diskBean.getCoverSettings(), "coverTypeUSBLoaderCFG");
		
		WidgetCreator.createRadio(usbLoaderType, "Wiiflow", 
				diskBean.getCoverSettings(), "coverTypeUSBLoaderWIIFLOW");
		
	}

	private void createGroupCoverPath(Composite mainComposite) {

		CoverPaths coverBean = diskBean.getCoverSettings().getCoverPaths();

		Group group = WidgetCreator.createGroup(mainComposite, Messages.settings_group_cover_type,3);

		WidgetCreator.createLabel(group,Messages.settings_cover_save_path,3);
		WidgetCreator.createLabel(group,Messages.settings_cover_2d);

		WidgetCreator.createText(group, false, coverBean, "cover2d",2);  

		WidgetCreator.createLabel(group,Messages.settings_cover_3d);
		WidgetCreator.createText(group, false, coverBean, "cover3d",2);  

		WidgetCreator.createLabel(group,Messages.settings_cover_disc);
		WidgetCreator.createText(group, false, coverBean, "coverDisc",2);  
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

	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();
		String newValue = evt.getNewValue().toString();
		String oldValue = evt.getOldValue().toString();
		
		System.out.println(propertyName + " changed from "+oldValue+" to "+newValue);  //$NON-NLS-2$
		
		propertyChangeDownloadCover(evt);
	}
	
	private void propertyChangeDownloadCover(PropertyChangeEvent evt) {

		String propertyName = evt.getPropertyName();
		
		if(propertyName.equals("automaticCoverDownload")){ 
			
			boolean enabled = ((Boolean)evt.getNewValue()).booleanValue();
			
			cover3dDownloadButton.setEnabled(enabled);
			coverDiscDownloadButton.setEnabled(enabled);
			
		}
	
	}
	
}
