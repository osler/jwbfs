package jwbfs.ui.jobs;

import jwbfs.ui.exceptions.NotValidDiscException;
import jwbfs.ui.utils.GuiUtils;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
/**
 * Abstract class that handle all the exceptions and forward them to GUI prompt
 * @author Luca Mazzilli
 *
 */
public abstract class JwbfsJob extends Job{

	public JwbfsJob(String name) {
		super(name);
	}

	/**
	 * RCP execution
	 */
	@Override
	protected IStatus run(IProgressMonitor monitor) {

		try {
			return runJwbfs(monitor);
		}catch (NotValidDiscException e) {
			GuiUtils.setDefaultCovers();
			e.printStackTrace();
			return Status.CANCEL_STATUS;
		}catch (Exception e) {
			GuiUtils.setDefaultCovers();
			GuiUtils.showErrorWithSaveFile(this.getClass().getName()+" Error \n", e, true);
			e.printStackTrace();
			return Status.CANCEL_STATUS;
		}
		
	}

	/**
	 * Generic code execution
	 * @param monitor
	 * @return
	 */
	protected abstract IStatus runJwbfs(IProgressMonitor monitor) throws Exception;

}
