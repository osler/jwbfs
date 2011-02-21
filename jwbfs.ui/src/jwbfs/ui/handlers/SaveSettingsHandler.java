package jwbfs.ui.handlers;

import jwbfs.ui.controls.ConfigUtils;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public class SaveSettingsHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		ConfigUtils.saveConfigFile();
		
		return true;
	}
}
