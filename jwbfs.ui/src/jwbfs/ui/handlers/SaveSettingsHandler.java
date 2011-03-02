package jwbfs.ui.handlers;

import java.io.File;

import jwbfs.model.utils.ConfigUtils;
import jwbfs.model.utils.CoreConstants;
import jwbfs.model.utils.Decode;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public class SaveSettingsHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		ConfigUtils.saveConfigFile();

		ConfigUtils.saveDiskConfigFile("configs"+File.separatorChar+Decode.decodeFileName(CoreConstants.VIEW_DISK_1_ID));
		ConfigUtils.saveDiskConfigFile("configs"+File.separatorChar+Decode.decodeFileName(CoreConstants.VIEW_DISK_2_ID));
		ConfigUtils.saveDiskConfigFile("configs"+File.separatorChar+Decode.decodeFileName(CoreConstants.VIEW_DISK_3_ID));
		ConfigUtils.saveDiskConfigFile("configs"+File.separatorChar+Decode.decodeFileName(CoreConstants.VIEW_DISK_4_ID));
		ConfigUtils.saveDiskConfigFile("configs"+File.separatorChar+Decode.decodeFileName(CoreConstants.VIEW_DISK_5_ID));
		ConfigUtils.saveDiskConfigFile("configs"+File.separatorChar+Decode.decodeFileName(CoreConstants.VIEW_DISK_6_ID));
	
		return true;
	}
}
