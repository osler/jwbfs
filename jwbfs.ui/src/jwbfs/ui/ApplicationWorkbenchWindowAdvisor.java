package jwbfs.ui;

import jwbfs.model.Model;
import jwbfs.model.beans.SettingsBean;
import jwbfs.ui.controls.Exec;
import jwbfs.ui.handlers.CheckDiscHandler;
import jwbfs.ui.handlers.UpdateGameListHandler;
import jwbfs.ui.utils.GuiUtils;
import jwbfs.ui.utils.Utils;
import jwbfs.ui.views.ManagerView;
import jwbfs.ui.views.dialogs.DialogSelectDisk;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

    public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
        super(configurer);
    }

    public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer) {
        return new ApplicationActionBarAdvisor(configurer);
    }
    
    public void preWindowOpen() {
		Exec.initConfigFile();
		
		IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
		int x = Integer.parseInt(System.getProperty("window.x"));
		int y = Integer.parseInt(System.getProperty("window.y"));
		configurer.setInitialSize(new Point(x, y));
		configurer.setShowMenuBar(false);
		configurer.setShowCoolBar(false);
		configurer.setShowStatusLine(false);
        configurer.setTitle("jwbfs - a wbfs_file wrapper");
    }

	@Override
	public void postWindowClose() {
		Exec.saveConfigFile();
	}

	@Override
	public void postWindowOpen() {
		
		DialogSelectDisk sel = new DialogSelectDisk(new Shell());
		sel.open();

			try {
				((SettingsBean) Model.getBeans().get(SettingsBean.INDEX)).setManagerMode(true);
//				Utils.getHandlerService(ManagerView.ID).executeCommand(UpdateGameListHandler.ID, null);
				Utils.getHandlerService(ManagerView.ID).executeCommand(CheckDiscHandler.ID, null);
			} catch (ExecutionException e) {
				e.printStackTrace();
			} catch (NotDefinedException e) {
				e.printStackTrace();
			} catch (NotEnabledException e) {
				e.printStackTrace();
			} catch (NotHandledException e) {
				e.printStackTrace();
			}
		
	
		GuiUtils.getManagerTableViewer().setInput(Model.getGames());
		
	}
}
