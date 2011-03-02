package jwbfs.ui.handlers;

import jwbfs.model.ModelStore;
import jwbfs.ui.jobs.UpdateCoverOperation;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.jobs.Job;

public class UpdateCoverHandler extends AbstractHandler {
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

//		IJobManager a = Job.getJobManager();
//		
//		if(a.currentJob()!=null){
//			a.currentJob().cancel();
//		}
		
//		new SetCoverOperation().run()
		
		Job job = new UpdateCoverOperation("setting up cover for: "+ModelStore.getSelectedGame().getId(),
				ModelStore.getSelectedGame());
		job.setUser(true);
		job.schedule();
		
		return true;
	}

}
