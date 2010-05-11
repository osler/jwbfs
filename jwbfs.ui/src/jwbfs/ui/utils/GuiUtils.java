package jwbfs.ui.utils;

import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
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

	public static void showInfo(String message, int style) {
		MessageBox msg = new MessageBox(new Shell(),style);
		msg.setMessage(message);
		msg.open();
		
	}

}
