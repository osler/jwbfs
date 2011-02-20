package jwbfs.ui.handlers;

import jwbfs.model.utils.CoreConstants;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;

public class AddDiskHandler extends AbstractHandler {


	public AddDiskHandler() {
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		int numDisks = Integer.parseInt(event.getParameter("numDisks"));
		
		String perspectiveID = CoreConstants.PERSPECTIVE_DISKS_1 + String.valueOf(numDisks);
		
		try {
			PlatformUI.getWorkbench().showPerspective(perspectiveID, PlatformUI.getWorkbench().getActiveWorkbenchWindow());
		} catch (WorkbenchException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
