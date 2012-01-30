package jwbfs.ui.handlers;

import jwbfs.ui.exceptions.NotValidDiscException;
import jwbfs.ui.utils.GuiUtils;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

/**
 * Abstract class that handle all the exceptions and forward them to GUI prompt
 * @author Luca Mazzilli
 *
 */
public abstract class JwbfsAbstractHandler extends AbstractHandler {

	public JwbfsAbstractHandler() {
	}

	/**
	 * RCP execute
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		try{
			return executeJwbfs(event);
		}catch (NotValidDiscException e) {
			GuiUtils.setDefaultCovers();
			e.printStackTrace();
			return false;
		}catch (Exception e) {
			GuiUtils.showErrorWithSaveFile(this.getClass().getName()+" Error \n", e, true);
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Generic code execution
	 * @param event
	 * @return
	 * @throws Exception
	 */
	protected abstract Object executeJwbfs(ExecutionEvent event) throws Exception;
	
}
