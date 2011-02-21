package jwbfs.core;

import jwbfs.model.utils.CoreConstants;

import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.IWorkbenchConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

public class ApplicationWorkbenchAdvisor extends WorkbenchAdvisor {

    public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
        return new ApplicationWorkbenchWindowAdvisor(configurer);
    }

	public String getInitialWindowPerspectiveId() {
		return CoreConstants.PERSPECTIVE_DISKS_1;
	}
	
	@Override 
	public void initialize(IWorkbenchConfigurer configurer) { 

	super.initialize(configurer); 
	PlatformUI.getPreferenceStore().setValue(IWorkbenchPreferenceConstants.SHOW_TRADITIONAL_STYLE_TABS, false);
	PlatformUI.getPreferenceStore().setValue(IWorkbenchPreferenceConstants.VIEW_TAB_POSITION, SWT.BOTTOM); 

	} 
}
