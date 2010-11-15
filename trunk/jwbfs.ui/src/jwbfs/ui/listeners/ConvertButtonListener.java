package jwbfs.ui.listeners;

import jwbfs.model.beans.GameBean;
import jwbfs.model.utils.CoreConstants;
import jwbfs.ui.exceptions.FileNotSelectedException;
import jwbfs.ui.exceptions.NotValidDiscException;
import jwbfs.ui.utils.PlatformUtils;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class ConvertButtonListener extends SelectionAdapter {

	private String viewID;
	protected GameBean bean = null;
	

	public ConvertButtonListener(String viewID, GameBean bean){
		this.viewID = viewID;
		this.bean = bean;
	}
	
	@Override
	public void widgetSelected(SelectionEvent e) {

		
		try {
		
		if(bean.getFilePath() == null || bean.getFilePath().equals("")){
			 
			throw new FileNotSelectedException();

		}
		
		if(bean.getId().equals("not a wii disc")){
			throw new NotValidDiscException();

		}

		if(bean.isIsoToWbfs()){
			PlatformUtils.getHandlerService(viewID).executeCommand(CoreConstants.COMMAND_TOWBFS_ID, null);
		}else{
			PlatformUtils.getHandlerService(viewID).executeCommand(CoreConstants.COMMAND_TOISO_ID, null);

		}
		

		} catch (NotValidDiscException e1) {
	
		} catch (FileNotSelectedException e1) {
	
		} catch (Exception ex) {
			throw new RuntimeException("Command not Found");
		}
	
	}


}
