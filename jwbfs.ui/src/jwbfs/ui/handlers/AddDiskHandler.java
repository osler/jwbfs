package jwbfs.ui.handlers;

import jwbfs.model.ModelStore;
import jwbfs.model.utils.CoreConstants;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;

public class AddDiskHandler extends AbstractHandler {


	public AddDiskHandler() {
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {

		IPerspectiveDescriptor persp = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getPerspective();
		
//		DisksPerspective temp =  new DisksPerspective(); //TODO add disk, i need access to perspective
//		temp.getDiskFolder().addView(CoreConstants.VIEW_DISK_2_ID);
		
		int numDisk = ModelStore.getNumDisk()+1;
		ModelStore.setNumDisk(numDisk);
		
		try {
			PlatformUI.getWorkbench().showPerspective(CoreConstants.PERSPECTIVE_DISKS, PlatformUI.getWorkbench().getActiveWorkbenchWindow());
		} catch (WorkbenchException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
