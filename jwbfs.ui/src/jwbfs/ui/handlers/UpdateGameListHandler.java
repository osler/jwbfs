package jwbfs.ui.handlers;

import jwbfs.ui.jobs.UpdateGameListOperation;
import jwbfs.ui.utils.GuiUtils;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.jobs.Job;

public class UpdateGameListHandler extends AbstractHandler {
	


	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		String diskID = event.getParameter("diskID").trim();
		
		if(diskID == null || diskID.equals("") ||diskID.equals("activeDiskID")){
			diskID = GuiUtils.getActiveViewID();
		}

		new UpdateGameListOperation("Updating games list",diskID).run();
	
		return true;
	}

}
