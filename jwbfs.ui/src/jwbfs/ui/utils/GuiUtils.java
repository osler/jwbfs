package jwbfs.ui.utils;

import jwbfs.ui.views.MainView;

import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

public class GuiUtils {

	
	public static ViewPart getView(String ID){
	    
		MainView view = (MainView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(ID);
		  
		return view; 
	}
	
	
	public static ViewPart getMainView(){
		return getView(MainView.ID);
	}
	
}
