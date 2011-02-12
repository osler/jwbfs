package jwbfs.ui.views.folder;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import jwbfs.i18n.Messages;
import jwbfs.model.Model;
import jwbfs.model.beans.CoverPaths;
import jwbfs.model.beans.CoverSettings;
import jwbfs.model.beans.ModelObject;
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

public class SettingsView extends ViewPart implements PropertyChangeListener{
	 
	protected SettingsBean bean = null;
	private Button cover3dDownloadButton;
	private Button coverDiscDownloadButton;
	
	public SettingsView() {
		bean = (SettingsBean) getTabBean() ;
		bean.getCoverSettings().addPropertyChangeListener(this);
		bean.getSystemSettings().addPropertyChangeListener(this);
		bean.addPropertyChangeListener(this);
	}

	@Override
	public void createPartControl(Composite parent) {

		Composite composite = WidgetCreator.createComposite(parent);
		TabFolder tabFolder = WidgetCreator.createTabFolder(composite);
		
//		Color folderColor = Display.getDefault().getSystemColor(SWT.COLOR_BLUE); 
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
 
		ModelObject coverModel = Model.getSettingsBean().getCoverSettings();
		
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
		
		group = WidgetCreator.createGroup(composite, Messages.settings_group_cover,2);
		button = WidgetCreator.createCheck(group, 
				Messages.settings_cover_enable, 
				coverModel, 
				"automaticCoverDownload",
				2,
				GridData.BEGINNING,
				GridData.BEGINNING);  
		WidgetCreator.createSeparator(group, 1, GridData.END, GridData.BEGINNING);
		cover3dDownloadButton = WidgetCreator.createCheck(group, 
				Messages.settings_cover_3d_download, 
				coverModel, 
				"cover3D",
				1,
				GridData.BEGINNING,
				GridData.BEGINNING);  
		WidgetCreator.createSeparator(group, 1, GridData.END, GridData.BEGINNING);
		coverDiscDownloadButton = WidgetCreator.createCheck(group, 
				Messages.settings_cover_disc_download, 
				coverModel, 
				"coverDiscs",
				1,
				GridData.BEGINNING,
				GridData.BEGINNING);  

		group = WidgetCreator.createGroup(composite, Messages.settings_group_cover_type,3);
		WidgetCreator.createRadio(group, Messages.settings_usb_loader_gx, 
				coverModel, "coverTypeUSBLoaderGX");  
		
		WidgetCreator.createRadio(group, Messages.settings_usb_loader_cfg, 
				coverModel, "coverTypeUSBLoaderCFG");  
		
		WidgetCreator.createRadio(group, Messages.settings_usb_loader_wiiflow, 
				coverModel, "coverTypeUSBLoaderWIIFLOW");  
		
		WidgetCreator.createLabel(group,Messages.settings_cover_save_path,3);
		WidgetCreator.createLabel(group,Messages.settings_cover_2d);
		CoverPaths coverBean = ((SettingsBean) getTabBean()).getCoverSettings().getCoverPaths();
		WidgetCreator.createText(group, false, coverBean, "cover2d",2);  

		WidgetCreator.createLabel(group,Messages.settings_cover_3d);
		WidgetCreator.createText(group, false, coverBean, "cover3d",2);  
		
		WidgetCreator.createLabel(group,Messages.settings_cover_disc);
		WidgetCreator.createText(group, false, coverBean, "coverDisc",2);  

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
		label = WidgetCreator.createLabel(group,"");  
		WidgetCreator.createCheck(group, Messages.settings_txt_file_enable, getTabBean(), "enableTXT",1);  
		label = WidgetCreator.createLabel(group,Messages.settings_txt_file_layout);
		combo =  WidgetCreator.createCombo(group, WBFSFileConstants.TXT_LAYOUT_Text, (SettingsBean) getTabBean(), "txtLayout");  

		return tab;
	}

	private void addHandlerUpdateTXT(Button button) {
		button.addSelectionListener(new UpdateTitlesTXTListener(CoreConstants.SETTINGSVIEW_ID));
		
	}

	private SettingsBean getTabBean() {
		return Model.getSettingsBean();
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
			CoverSettings coverSett= bean.getCoverSettings();
			
			if(coverSett.isCoverTypeAnySelected()){
				CoverUtils.setCoversPathFromDiskPath();	
			}
			
			
		}
	}

			
}
