package jwbfs.ui.handlers;

import java.lang.reflect.InvocationTargetException;

import jwbfs.ui.utils.GuiUtils;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class ToWBFSConvertHandler extends AbstractHandler {

	public ToWBFSConvertHandler() {
	}


	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		Shell sh = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
		.getShell();

		try {

			new ProgressMonitorDialog(sh).run(true, true, new ToWBFSConvertOperation());

		} catch (InvocationTargetException e) {

			e.printStackTrace();

			return null;

		} catch (InterruptedException e) {

			GuiUtils.showError("Error: "+e.getMessage());
			return null;

		}

		
		return null;
	}
}
