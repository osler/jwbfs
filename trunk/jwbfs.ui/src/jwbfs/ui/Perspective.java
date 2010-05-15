package jwbfs.ui;

import jwbfs.model.Model;
import jwbfs.ui.views.CoverView;
import jwbfs.ui.views.ManagerView;
import jwbfs.ui.views.folder.ProcessView;
import jwbfs.ui.views.folder.SettingsView;

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
		
		Model model = new Model();
		
		folder.addView(ManagerView.ID);
		folder.addView(ProcessView.ID);
		folder.addView(SettingsView.ID);
		
		layout.addStandaloneView(CoverView.ID,  false, IPageLayout.RIGHT, 0.5f, editorArea);
		
	}
}
