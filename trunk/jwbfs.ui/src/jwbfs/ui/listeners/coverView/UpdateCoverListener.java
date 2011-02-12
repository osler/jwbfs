package jwbfs.ui.listeners.coverView;

import jwbfs.model.Model;
import jwbfs.model.beans.GameBean;
import jwbfs.model.beans.SettingsBean;
import jwbfs.model.utils.CoreConstants;
import jwbfs.model.utils.PlatformUtils;
import jwbfs.ui.exceptions.FileNotSelectedException;
import jwbfs.ui.exceptions.NotValidDiscException;
import jwbfs.ui.utils.GuiUtils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class UpdateCoverListener extends SelectionAdapter {

	private String viewID;
	protected GameBean bean = null;
	

	public UpdateCoverListener(String viewID, GameBean bean){
		this.viewID = viewID;
		this.bean = bean;
	}
	
	@Override
	public void widgetSelected(SelectionEvent e) {

		
		try {
				bean = Model.getSelectedGame();
				if(bean.getFilePath() == null || bean.getFilePath().equals("")){

					throw new FileNotSelectedException();

				}

				if(bean.getId().equals("not a wii disc")){
					throw new NotValidDiscException();

				}
			

		((SettingsBean)Model.getBeans().get(SettingsBean.INDEX)).getCoverSettings().setUpdateCover(true);
		PlatformUtils.getHandlerService(viewID).executeCommand(CoreConstants.COMMAND_COVER_UPDATE_ID, null);
		

		} catch (NotValidDiscException e1) {
	
		} catch (FileNotSelectedException e1) {
	
		} catch (Exception ex) {
			GuiUtils.showInfo(ex.getMessage(),SWT.ERROR);
			ex.printStackTrace();
			throw new RuntimeException("Command not Found");
		}
	
	}


}
