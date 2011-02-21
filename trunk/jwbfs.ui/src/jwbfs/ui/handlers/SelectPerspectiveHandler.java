package jwbfs.ui.handlers;

import jwbfs.model.ModelStore;
import jwbfs.ui.ContextActivator;
import jwbfs.ui.utils.GuiUtils;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;

public class SelectPerspectiveHandler extends AbstractHandler {


	public SelectPerspectiveHandler() {
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		int numDisks = Integer.parseInt(event.getParameter("numDisks"));
		
		ModelStore.setNumDisk(numDisks);
		
		String perspectiveID = GuiUtils.decodePerspectiveID(numDisks);
	
		
		try {
			PlatformUI.getWorkbench().showPerspective(perspectiveID, PlatformUI.getWorkbench().getActiveWorkbenchWindow());
		} catch (WorkbenchException e) {
			e.printStackTrace();
		}

		ContextActivator.reloadContext();
		
		return true;
	}
}
