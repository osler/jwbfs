package jwbfs.ui.perspectives;

import jwbfs.model.utils.CoreConstants;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class DisksPerspective1 implements IPerspectiveFactory {

	protected IFolderLayout leftFolder =  null;
	protected IFolderLayout rightFolder;
	private IFolderLayout centralFolder;
	
	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible(false);
		layout.setFixed(false);
		
		leftFolder = layout.createFolder("folderLeft", 0, 0.6f, editorArea);
//		centralFolder = layout.createFolder("folderCentral", 0, 0.6f, editorArea);
		rightFolder = layout.createFolder("folderRight", 0, 0.6f, editorArea);
		
		addDisksTabs();
		addOthersTabs();

//		layout.addStandaloneView(CoreConstants.VIEW_COVER_ID,  false, IPageLayout.RIGHT, 0.5f, editorArea);
	}

	protected void addDisksTabs() {
		leftFolder.addView(CoreConstants.VIEW_DISK_1_ID);
		leftFolder.addPlaceholder(CoreConstants.VIEW_DISK_2_ID);
		leftFolder.addPlaceholder(CoreConstants.VIEW_DISK_3_ID);
		leftFolder.addPlaceholder(CoreConstants.VIEW_DISK_4_ID);
		leftFolder.addPlaceholder(CoreConstants.VIEW_DISK_5_ID);
		leftFolder.addPlaceholder(CoreConstants.VIEW_DISK_6_ID);
		
	}

	protected void addOthersTabs() {
		rightFolder.addView(CoreConstants.VIEW_SETTINGS_ID);
		rightFolder.addView(CoreConstants.VIEW_COVER_ID);
	}
	
	public IFolderLayout getDiskFolder() {
		return leftFolder;
	}

	public void setDiskFolder(IFolderLayout diskFolder) {
		this.leftFolder = diskFolder;
	}
}
