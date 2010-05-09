package jwbfs.ui.utils;

import org.eclipse.swt.graphics.Device;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

public class GuiUtils {

	
	public static ViewPart getView(String ID){
		ViewPart view = null;
		try{
			view = (ViewPart) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(ID);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}

	public static Device getDisplay() {
		return PlatformUI.getWorkbench().getDisplay();
	}

}
