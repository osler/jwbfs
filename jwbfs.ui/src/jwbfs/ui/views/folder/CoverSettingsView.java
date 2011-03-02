package jwbfs.ui.views.folder;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import jwbfs.i18n.Messages;
import jwbfs.model.ModelStore;
import jwbfs.model.beans.CoverPaths;
import jwbfs.model.beans.CoverSettings;
import jwbfs.model.beans.SettingsBean;
import jwbfs.model.utils.CoreConstants;
import jwbfs.model.utils.CoverConstants;
import jwbfs.model.utils.WBFSFileConstants;
import jwbfs.ui.listeners.settings.UpdateTitlesTXTListener;
import jwbfs.ui.utils.CoverUtils;
import jwbfs.ui.views.WidgetCreator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.part.ViewPart;

public class CoverSettingsView extends ViewPart implements PropertyChangeListener{
	 
	protected CoverSettings coverBean = null;
	private Button cover3dDownloadButton;
	private Button coverDiscDownloadButton;
	private Combo txtLayoutCombo;
	private String diskID;
	
	public CoverSettingsView() {
		//TODO dinamic
		diskID = CoreConstants.VIEW_DISK_1_ID;
		coverBean = ModelStore.getDisk(diskID).getCoverSettings();
		//TODO OO
		
		//TODO
		coverBean.addPropertyChangeListener(this);
//		bean.getSystemSettings().addPropertyChangeListener(this);
//		bean.addPropertyChangeListener(this);
	}

	@Override
	public void createPartControl(Composite parent) {

		Composite composite = WidgetCreator.createComposite(parent);
		TabFolder tabFolder = WidgetCreator.createTabFolder(composite);
		
//		Color folderColor = Display.getDefault().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND); 
//		tabFolder.setBackground(folderColor); 
		
		createGeneralTab(tabFolder,Messages.settings_tab_general);
		createDiskSettingsTab(tabFolder,Messages.settings_tab_disk);
	}



	private TabItem createGeneralTab(TabFolder folder,String name) {
		TabItem tab = new TabItem(folder, SWT.NONE);
		tab.setText(name);
		
//		Color folderColor = Display.getDefault().getSystemColor(SWT.COLOR_BLUE); 
//
//		folder.setBackground(folderColor); 
		
		Composite composite = WidgetCreator.createComposite(folder);
		tab.setControl(composite);
		
		Group group = WidgetCreator.createGroup(composite, Messages.settings_region,3);
 
		WidgetCreator.createCombo(group, 
				CoverConstants.REGIONS, 
				(SettingsBean) getTabBean(),
				"region", 
				false,
				3,
				GridData.BEGINNING,
				GridData.BEGINNING); 
		WidgetCreator.createLabel(group,"titles.TXT",2);  
		Button button = WidgetCreator.createButton(group,Messages.settings_update);
		addHandlerUpdateTXT(button);
		

		CoverPaths coverPaths = coverBean.getCoverPaths();
		
		group = WidgetCreator.createGroup(composite, Messages.settings_group_cover,2);
		button = WidgetCreator.createCheck(group, 
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


		group = WidgetCreator.createGroup(composite, Messages.settings_group_cover_type,3);
		WidgetCreator.createRadio(group, Messages.settings_usb_loader_gx, 
				coverBean, "coverTypeUSBLoaderGX");  
		
		WidgetCreator.createRadio(group, Messages.settings_usb_loader_cfg, 
				coverBean, "coverTypeUSBLoaderCFG");  
		
		WidgetCreator.createRadio(group, Messages.settings_usb_loader_wiiflow, 
				coverBean, "coverTypeUSBLoaderWIIFLOW");  
		
		WidgetCreator.createLabel(group,Messages.settings_cover_save_path,3);
		WidgetCreator.createLabel(group,Messages.settings_cover_2d);
		
		WidgetCreator.createText(group, false, coverPaths, "cover2d",2);  

		WidgetCreator.createLabel(group,Messages.settings_cover_3d);
		WidgetCreator.createText(group, false, coverPaths, "cover3d",2);  
		
		WidgetCreator.createLabel(group,Messages.settings_cover_disc);
		WidgetCreator.createText(group, false, coverPaths, "coverDisc",2);  

		return tab;
	}
	
	private TabItem createDiskSettingsTab(TabFolder folder, String name) {
		TabItem tab = new TabItem(folder, SWT.NONE);
		tab.setText(name);
		
		Composite composite = WidgetCreator.createComposite(folder);
		tab.setControl(composite);
		
		Group group = WidgetCreator.createGroup(composite, Messages.settings_partitions);
		@SuppressWarnings("unused")
		Label label = WidgetCreator.createLabel(group,Messages.settings_partitions_split_size);
		@SuppressWarnings("unused")
		Combo combo =  WidgetCreator.createCombo(group, WBFSFileConstants.SPLITSIZE_Text, (SettingsBean) getTabBean(), "splitSize");  
		label = WidgetCreator.createLabel(group,Messages.settings_partitions_copy);
		combo =  WidgetCreator.createCombo(group, WBFSFileConstants.COPY_PARTITIONS_Text, (SettingsBean) getTabBean(), "copyPartitions");  
	
		group = WidgetCreator.createGroup(composite, Messages.settings_group_txt_file);
		WidgetCreator.createCheck(group, 
				Messages.settings_txt_file_enable, 
				getTabBean(), 
				"enableTXT",
				2,
				GridData.BEGINNING,
				GridData.BEGINNING);  
		label = WidgetCreator.createLabel(group,Messages.settings_txt_file_layout);
		txtLayoutCombo =  WidgetCreator.createCombo(group, WBFSFileConstants.TXT_LAYOUT_Text, (SettingsBean) getTabBean(), "txtLayout");  

		return tab;
	}

	private void addHandlerUpdateTXT(Button button) {
		button.addSelectionListener(new UpdateTitlesTXTListener(CoreConstants.VIEW_SETTINGS_ID));
		
	}

	private SettingsBean getTabBean() {
		return ModelStore.getSettingsBean();
	}

	@Override
	public void setFocus() {
		
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();
		String newValue = evt.getNewValue().toString();
		String oldValue = evt.getOldValue().toString();
		
		System.out.println(propertyName + " changed from "+oldValue+" to "+newValue);  //$NON-NLS-2$
		
		propertyChangeUpdateCoverType(evt);
		
		propertyChangeDownloadCover(evt);
		
		propertyChangeEnableTxtCreation(evt);
	}

	private void propertyChangeEnableTxtCreation(PropertyChangeEvent evt) {

		String propertyName = evt.getPropertyName();
		
		if(propertyName.equals("enableTXT")){ 
			
			boolean enabled = ((Boolean)evt.getNewValue()).booleanValue();
			
			txtLayoutCombo.setEnabled(enabled);
			
		}
	
	}

	private void propertyChangeDownloadCover(PropertyChangeEvent evt) {

		String propertyName = evt.getPropertyName();
		
		if(propertyName.equals("automaticCoverDownload")){ 
			
			boolean enabled = ((Boolean)evt.getNewValue()).booleanValue();
			
			cover3dDownloadButton.setEnabled(enabled);
			coverDiscDownloadButton.setEnabled(enabled);
			
		}
	
	}

	private void propertyChangeUpdateCoverType(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();
		
		if(propertyName.equals("coverTypeUSBLoaderGX") 
				|| propertyName.equals("coverTypeUSBLoaderCFG") 
				|| propertyName.equals("coverTypeUSBLoaderWIIFLOW")){ 
			
			if(coverBean.isCoverTypeAnySelected()){
				CoverUtils.setCoversPathFromDiskPath(diskID);	
			}
			
			
		}
	}

			
}
