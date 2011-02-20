package jwbfs.ui.perspectives;

import jwbfs.model.ModelStore;
import jwbfs.model.utils.CoreConstants;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class DisksPerspective implements IPerspectiveFactory {

	private IFolderLayout diskFolder =  null;
	
	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible(false);
		layout.setFixed(true);
		
		diskFolder = layout.createFolder("folder", 0, 0.6f, editorArea);
		
//		layout.addStandaloneView(MainView.ID,  false, IPageLayout.LEFT, 0.5f, editorArea);
//		layout.addStandaloneView(CoverView.ID,  false, IPageLayout.RIGHT, 0.5f, editorArea);
		
		new ModelStore();
		
		if(ModelStore.getNumDisk() == 1){
			diskFolder.addView(CoreConstants.VIEW_DISK_0_ID);
		}else if(ModelStore.getNumDisk() == 2){
			diskFolder.addView(CoreConstants.VIEW_DISK_0_ID);		
			diskFolder.addView(CoreConstants.VIEW_DISK_1_ID);
		}else if(ModelStore.getNumDisk() == 3){
			diskFolder.addView(CoreConstants.VIEW_DISK_0_ID);	
			diskFolder.addView(CoreConstants.VIEW_DISK_1_ID);
			diskFolder.addView(CoreConstants.VIEW_DISK_2_ID);
		}else if(ModelStore.getNumDisk() == 4){
			diskFolder.addView(CoreConstants.VIEW_DISK_0_ID);	
			diskFolder.addView(CoreConstants.VIEW_DISK_1_ID);
			diskFolder.addView(CoreConstants.VIEW_DISK_2_ID);
			diskFolder.addView(CoreConstants.VIEW_DISK_3_ID);
		}else if(ModelStore.getNumDisk() == 5){
			diskFolder.addView(CoreConstants.VIEW_DISK_0_ID);	
			diskFolder.addView(CoreConstants.VIEW_DISK_1_ID);
			diskFolder.addView(CoreConstants.VIEW_DISK_2_ID);
			diskFolder.addView(CoreConstants.VIEW_DISK_3_ID);
			diskFolder.addView(CoreConstants.VIEW_DISK_4_ID);	
		}
			
//		diskFolder.addView(CoreConstants.VIEW_DISK_0_ID);
//		
//		diskFolder.addView(CoreConstants.VIEW_DISK_1_ID);
		
//		folder.addView(CoreConstants.VIEW_DISK_1_ID);
		diskFolder.addView(CoreConstants.VIEW_SETTINGS_ID);
	
		
		layout.addStandaloneView(CoreConstants.VIEW_COVER_ID,  false, IPageLayout.RIGHT, 0.5f, editorArea);
		
	}

	public IFolderLayout getDiskFolder() {
		return diskFolder;
	}

	public void setDiskFolder(IFolderLayout diskFolder) {
		this.diskFolder = diskFolder;
	}
}
