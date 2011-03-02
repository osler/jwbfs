package jwbfs.ui.handlers;

import jwbfs.ui.utils.GuiUtils;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class OpenViewHandler extends AbstractHandler {


	public OpenViewHandler() {
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		int numDisks = Integer.parseInt(event.getParameter("numDisks"));
		
//		ModelStore.setNumDisk(numDisks);
		
//		String perspectiveID = GuiUtils.decodePerspectiveID(numDisks);
//	
//		try {
//
//			PlatformUI.getWorkbench().showPerspective(perspectiveID, PlatformUI.getWorkbench().getActiveWorkbenchWindow());
//		} catch (WorkbenchException e) {
//			e.printStackTrace();
//		}

		IWorkbenchWindow win =  PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		
		String viewID = GuiUtils.decodeDiskID(numDisks);
		try {
			
			win.
					getActivePage().
						showView(
							viewID
							,
							null
							,IWorkbenchPage.VIEW_ACTIVATE
						);	
		
		}catch (Exception e){
			
			e.printStackTrace();
			
		}
		
		return true;
	}
}
