package jwbfs.ui.listeners;

import jwbfs.model.Model;
import jwbfs.model.beans.ProcessBean;
import jwbfs.model.beans.SettingsBean;
import jwbfs.ui.exceptions.FileNotSelectedException;
import jwbfs.ui.exceptions.NotValidDiscException;
import jwbfs.ui.handlers.UpdateCoverHandler;
import jwbfs.ui.utils.GuiUtils;
import jwbfs.ui.utils.Utils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class UpdateCoverListener extends SelectionAdapter {

	private String viewID;
	protected ProcessBean bean = null;
	

	public UpdateCoverListener(String viewID, ProcessBean bean){
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

		((SettingsBean)Model.getTabs().get(SettingsBean.INDEX)).setUpdateCover(true);
		Utils.getHandlerService(viewID).executeCommand(UpdateCoverHandler.ID, null);
		

		} catch (NotValidDiscException e1) {
	
		} catch (FileNotSelectedException e1) {
	
		} catch (Exception ex) {
			GuiUtils.showInfo(ex.getMessage(),SWT.ERROR);
			ex.printStackTrace();
			throw new RuntimeException("Command not Found");
		}
	
	}


}
