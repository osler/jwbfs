package jwbfs.ui.views.folder;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import jwbfs.i18n.Messages;
import jwbfs.model.ModelStore;
import jwbfs.model.beans.SettingsBean;
import jwbfs.model.utils.CoreConstants;
import jwbfs.model.utils.CoverConstants;
import jwbfs.model.utils.WBFSFileConstants;
import jwbfs.ui.listeners.settings.UpdateTitlesTXTListener;
import jwbfs.ui.views.WidgetCreator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
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
	private Combo txtLayoutCombo;
	
	public SettingsView() {
		bean = (SettingsBean) getTabBean() ;
		bean.addPropertyChangeListener(this);
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
		
		//update site
		WidgetCreator.createLabel(group,"update site",2);  
		Button buttonReset = WidgetCreator.createButton(group,"default");
		buttonReset.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				getTabBean().resetUpdateSite();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		WidgetCreator.createText(group, true, getTabBean(), "updateSite",3); 
		
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


			
}
