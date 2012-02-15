package jwbfs.ui.handlers;

import java.io.File;

import jwbfs.i18n.Messages;
import jwbfs.model.utils.CoreConstants;
import jwbfs.model.utils.FileUtils;
import jwbfs.model.utils.PlatformUtils;
import jwbfs.ui.utils.GuiUtils;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.SWT;

/**
 * Creates a shortcut in current OS
 * @author Luca Mazzilli
 *
 */
public class CreateShortcutHandler extends JwbfsAbstractHandler {
	public static final String ID = "createShortcut"; //$NON-NLS-1$

	@Override
	public Object executeJwbfs(ExecutionEvent event) throws Exception {

		if(!PlatformUtils.isLinux()){
			GuiUtils.showInfo(Messages.generic_not_linux, SWT.ICON_WARNING);
			return  Status.CANCEL_STATUS;
		}
		
		String param = event.getParameter(CoreConstants.PARAM_CONFIRM);
		boolean confirm = param!=null && param.trim().equals("true"); //$NON-NLS-1$
		
		try{
			String path = FileUtils.getJwbfsShortcutPath();
			File folderForMenuItem = new File(path);
			boolean create = false;
			
			if(confirm){
				if(!folderForMenuItem.exists()){
					create = GuiUtils.showConfirmDialog(Messages.icon_creation_prompt);
				}else{
					create = GuiUtils.showConfirmDialog(Messages.icon_creation_exists);
				}
			}else{
				create = true;
			}

			if(create){
				if(PlatformUtils.isLinux() ){

					FileUtils.createMenuShortcut(PlatformUtils.getInstallDir());
					Runtime.getRuntime().exec("update-menus"); //$NON-NLS-1$
				}
			}
		}catch (Exception e) {
			System.err.println(Messages.icon_creation_error);
			e.printStackTrace();
			return  Status.CANCEL_STATUS;
		}
		

		return Status.OK_STATUS;
	}

}
