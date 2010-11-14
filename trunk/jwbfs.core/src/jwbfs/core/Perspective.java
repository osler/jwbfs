package jwbfs.core;

import jwbfs.model.Model;
import jwbfs.model.utils.CoreConstants;
import jwbfs.ui.views.CoverView;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class Perspective implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible(false);
		layout.setFixed(true);
		
		IFolderLayout folder = layout.createFolder("test", 0, 0.6f, editorArea);
		
//		layout.addStandaloneView(MainView.ID,  false, IPageLayout.LEFT, 0.5f, editorArea);
//		layout.addStandaloneView(CoverView.ID,  false, IPageLayout.RIGHT, 0.5f, editorArea);
		
		new Model();
		
		folder.addView(CoreConstants.MAINVIEW_ID);
		folder.addView(CoreConstants.SETTINGSVIEW_ID);
	
		
		layout.addStandaloneView(CoverView.ID,  false, IPageLayout.RIGHT, 0.5f, editorArea);
		
	}
}
