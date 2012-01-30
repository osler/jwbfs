package jwbfs.ui.handlers;

import jwbfs.ui.jobs.JobUpdateTitleTXT;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

public class UpdateTitlesTXTHandler extends JwbfsAbstractHandler {
	public static final String ID = "updateTitles";

	@Override
	public Object executeJwbfs(ExecutionEvent event) throws Exception {

	
//		Shell sh = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
//		.getShell();

		Job job = new JobUpdateTitleTXT("Updating titles.txt");
		job.setUser(true);
		job.schedule();
		
//		new ProgressMonitorDialog(sh).run(true, true, new UpdateTitleTXTOperation());

		return Status.OK_STATUS;
	
		
	}

}
