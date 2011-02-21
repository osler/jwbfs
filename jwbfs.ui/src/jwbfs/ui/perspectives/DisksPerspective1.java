package jwbfs.ui.perspectives;

import jwbfs.model.utils.CoreConstants;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class DisksPerspective1 implements IPerspectiveFactory {

	protected IFolderLayout diskFolder =  null;
	
	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible(false);
		layout.setFixed(true);
		
		diskFolder = layout.createFolder("folder", 0, 0.6f, editorArea);
				
		addDisksTabs();

		addOthersTabs();

		layout.addStandaloneView(CoreConstants.VIEW_COVER_ID,  false, IPageLayout.RIGHT, 0.5f, editorArea);
		
	}

	protected void addDisksTabs() {
		diskFolder.addView(CoreConstants.VIEW_DISK_1_ID);	
	}

	protected void addOthersTabs() {
		diskFolder.addView(CoreConstants.VIEW_SETTINGS_ID);
	}
	
	public IFolderLayout getDiskFolder() {
		return diskFolder;
	}

	public void setDiskFolder(IFolderLayout diskFolder) {
		this.diskFolder = diskFolder;
	}
}
