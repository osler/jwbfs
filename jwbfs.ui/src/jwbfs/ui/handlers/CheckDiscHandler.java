package jwbfs.ui.handlers;

import jwbfs.ui.jobs.CheckDisksOperation;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.jobs.Job;

public class CheckDiscHandler extends AbstractHandler {



	public static  int index = -1;
	

	public CheckDiscHandler() {
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		boolean isWbfs = event.getParameter("wbfs").trim().equals("true");
		
		new CheckDisksOperation("Checking disk",isWbfs).run();
		
		return true;
	}


}
