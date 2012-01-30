package jwbfs.ui.handlers.copy;

import jwbfs.ui.handlers.JwbfsAbstractHandler;
import jwbfs.ui.jobs.JobImportWBFSGame;
import jwbfs.ui.utils.GuiUtils;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

public class ImportWBFSGameHandler extends JwbfsAbstractHandler{


	public ImportWBFSGameHandler() {
	}

	public Object executeJwbfs(ExecutionEvent event) throws ExecutionException {


			String disk = event.getParameter("diskTo");

			if(disk == null || disk.trim().equals("activeID")){
				disk = GuiUtils.getActiveViewID();
			}

			final String diskTO = disk;
			Job job = new JobImportWBFSGame("Importing wbfs game", diskTO);
			job.setUser(true);
			job.schedule();
			
			//status scheduled, not the job return status
		return Status.OK_STATUS;
	}

}
