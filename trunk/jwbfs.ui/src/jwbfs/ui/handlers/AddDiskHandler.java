package jwbfs.ui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.Perspective;

public class AddDiskHandler extends AbstractHandler {


	public AddDiskHandler() {
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {

		IPerspectiveDescriptor persp = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getPerspective();
		
		Perspective a = (Perspective) persp; //TODO add disk, i need access to perspective

		return null;
	}
}
