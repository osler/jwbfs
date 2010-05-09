package jwbfs.ui;

import jwbfs.ui.views.CoverView;
import jwbfs.ui.views.MainView;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class Perspective implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible(false);
		layout.setFixed(true);
		
		IFolderLayout folder = layout.createFolder("test", 0, 1.0f, editorArea);
		
//		layout.addStandaloneView(MainView.ID,  false, IPageLayout.LEFT, 0.5f, editorArea);
//		layout.addStandaloneView(CoverView.ID,  false, IPageLayout.RIGHT, 0.5f, editorArea);
		
		folder.addView(MainView.ID);
		folder.addView(CoverView.ID);
	}
}
