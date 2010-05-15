package jwbfs.ui.listeners;

import jwbfs.model.beans.GameBean;
import jwbfs.ui.exceptions.FileNotSelectedException;
import jwbfs.ui.exceptions.NotValidDiscException;
import jwbfs.ui.handlers.ToISOConvertHandler;
import jwbfs.ui.handlers.ToWBFSConvertHandler;
import jwbfs.ui.utils.Utils;

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
			Utils.getHandlerService(viewID).executeCommand(ToWBFSConvertHandler.ID, null);
		}else{
			Utils.getHandlerService(viewID).executeCommand(ToISOConvertHandler.ID, null);

		}
		

		} catch (NotValidDiscException e1) {
	
		} catch (FileNotSelectedException e1) {
	
		} catch (Exception ex) {
			throw new RuntimeException("Command not Found");
		}
	
	}


}
