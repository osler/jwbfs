package jwbfs.ui.handlers;

import jwbfs.model.ModelStore;
import jwbfs.model.utils.WBFSFileConstants;
import jwbfs.ui.utils.GuiUtils;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.jobs.Job;

public class ToWBFSConvertHandler extends AbstractHandler {

	public ToWBFSConvertHandler() {
	}


	public Object execute(ExecutionEvent event) throws ExecutionException {

		if(!check()){
			return false;
		}
	
		Job job = new ToWBFSConvertOperation("Converting "+ModelStore.getSelectedGame().getTitle());
		job.setUser(true);
		job.schedule();

		
		//		Shell sh = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
//
//		try {
//
//			new ProgressMonitorDialog(sh).run(true, true, new ToWBFSConvertOperation());
//
//		} catch (InvocationTargetException e) {
//
//			e.printStackTrace();
//
//			return false;
//
//		} catch (InterruptedException e) {
//
//			GuiUtils.showError("Error: "+e.getMessage());
//			return false;
//
//		}

		return true;
	}


	private boolean check() {
		
		long size = ModelStore.getSelectedGame().getScrubSize();
		
		//FileUtils.createTempFile();
		
		if(size > 4194304 && ModelStore.getSettingsBean().getSplitSize().equals(WBFSFileConstants.SPLITSIZE_Text[0])){
			boolean confirm = GuiUtils.showConfirmDialog("The scrub size of the selected ISO is over 4 gb. If you are using FAT32 you should split the iso");
			return confirm;
		}

		return true;
	}
}
