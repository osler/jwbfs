package jwbfs.ui.views.folder;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import jwbfs.i18n.Messages;
import jwbfs.model.Model;
import jwbfs.model.beans.CoverPaths;
import jwbfs.model.beans.SettingsBean;
import jwbfs.model.utils.CoreConstants;
import jwbfs.model.utils.WBFSFileConstants;
import jwbfs.ui.listeners.settings.UpdateTitlesTXTListener;
import jwbfs.ui.utils.CoverConstants;
import jwbfs.ui.views.WidgetCreator;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.ViewPart;

public class SettingsView extends ViewPart implements PropertyChangeListener{
	 
	protected SettingsBean bean = null;
	
	public SettingsView() {
		bean = (SettingsBean) getTabBean() ;
		
	}

	@Override
	public void createPartControl(Composite parent) {

		Composite composite = WidgetCreator.createComposite(parent);
		
		Group group = WidgetCreator.createGroup(composite, Messages.settings_partitions);
		@SuppressWarnings("unused")
		Label label = WidgetCreator.createLabel(group,Messages.settings_partitions_split_size);
		@SuppressWarnings("unused")
		Combo combo =  WidgetCreator.createCombo(group, WBFSFileConstants.SPLITSIZE_Text, (SettingsBean) getTabBean(), "splitSize"); //$NON-NLS-1$
		label = WidgetCreator.createLabel(group,Messages.settings_partitions_copy);
		combo =  WidgetCreator.createCombo(group, WBFSFileConstants.COPY_PARTITIONS_Text, (SettingsBean) getTabBean(), "copyPartitions"); //$NON-NLS-1$
	
		group = WidgetCreator.createGroup(composite, Messages.settings_group_txt_file);
		label = WidgetCreator.createLabel(group,""); //$NON-NLS-1$
		Button button = WidgetCreator.createCheck(group, Messages.settings_txt_file_enable, getTabBean(), "enableTXT"); //$NON-NLS-2$
		label = WidgetCreator.createLabel(group,Messages.settings_txt_file_layout);
		combo =  WidgetCreator.createCombo(group, WBFSFileConstants.TXT_LAYOUT_Text, (SettingsBean) getTabBean(), "txtLayout"); //$NON-NLS-1$
		
		
		group = WidgetCreator.createGroup(composite, Messages.settings_group_cover,3);

		button = WidgetCreator.createCheck(group, Messages.settings_cover_enable, getTabBean(), "automaticCoverDownload"); //$NON-NLS-2$
		button = WidgetCreator.createCheck(group, Messages.settings_cover_3d_download, getTabBean(), "cover3D"); //$NON-NLS-2$
		button = WidgetCreator.createCheck(group, Messages.settings_cover_disc_download, getTabBean(), "coverDiscs"); //$NON-NLS-2$

		label = WidgetCreator.createLabel(group,Messages.settings_region);
		combo =  WidgetCreator.createCombo(group, CoverConstants.REGIONS, (SettingsBean) getTabBean(), "region"); //$NON-NLS-1$
		label = WidgetCreator.createLabel(group,""); //$NON-NLS-1$
		
		label = WidgetCreator.createLabel(group,"titles.TXT"); //$NON-NLS-1$
		button = WidgetCreator.createButton(group,Messages.settings_update);
		addHandlerUpdateTXT(button);
		label = WidgetCreator.createLabel(group,""); //$NON-NLS-1$
		
		WidgetCreator.createRadio(group, Messages.settings_usb_loader_gx, 
				Model.getSettingsBean().getCoverSettings(), "coverTypeUSBLoaderGX"); //$NON-NLS-1$
		
		WidgetCreator.createRadio(group, Messages.settings_usb_loader_cfg, 
				Model.getSettingsBean().getCoverSettings(), "coverTypeUSBLoaderCFG"); //$NON-NLS-1$
		
		WidgetCreator.createRadio(group, Messages.settings_usb_loader_wiiflow, 
				Model.getSettingsBean().getCoverSettings(), "coverTypeUSBLoaderWIIFLOW"); //$NON-NLS-1$
		
		label = WidgetCreator.createLabel(group,Messages.settings_cover_save_path,3);
		label = WidgetCreator.createLabel(group,Messages.settings_cover_2d);
		CoverPaths coverBean = ((SettingsBean) getTabBean()).getCoverSettings().getCoverPaths();
		WidgetCreator.createText(group, false, coverBean, "cover2d",2); //$NON-NLS-1$
//		button = WidgetCreator.createButton(group,"open");	
//		addCoverListener(button,"2d");
		
		label = WidgetCreator.createLabel(group,Messages.settings_cover_3d);
		WidgetCreator.createText(group, false, coverBean, "cover3d",2); //$NON-NLS-1$
//		button = WidgetCreator.createButton(group,"open");	
//		addCoverListener(button,"3d");
		
		label = WidgetCreator.createLabel(group,Messages.settings_cover_disc);
		WidgetCreator.createText(group, false, coverBean, "coverDisc",2); //$NON-NLS-1$
//		button = WidgetCreator.createButton(group,"open");	
//		addCoverListener(button,"disc");
	}

//	private void addCoverListener(Button button, String type) {
//		button.addSelectionListener(new FolderCoverDialogListener(CoreConstants.SETTINGSVIEW_ID, type));
//		
//	}

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
		System.out.println("TEST"); //$NON-NLS-1$
	}

			
}
