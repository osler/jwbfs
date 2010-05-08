package jwbfs.ui;

import jwbfs.ui.controls.Exec;

import org.eclipse.swt.graphics.Point;
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
}
