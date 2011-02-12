package jwbfs.ui.listeners.mainView;

import jwbfs.model.Model;
import jwbfs.model.beans.GameBean;
import jwbfs.model.utils.CoreConstants;
import jwbfs.model.utils.PlatformUtils;
import jwbfs.ui.exceptions.FileNotSelectedException;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class ExportButtonListener extends SelectionAdapter {


	private String viewID;

	public ExportButtonListener(String viewID){
		this.viewID = viewID;
	}


	@Override
	public void widgetSelected(SelectionEvent e) {
		
		try {
			
			GameBean gameSelected = Model.getSelectedGame();

			if(gameSelected.getFilePath() == null 
					|| gameSelected.getFilePath().equals("")){
				 
				throw new FileNotSelectedException();

			}
			
			boolean ok = (Boolean) PlatformUtils.getHandlerService(viewID).executeCommand(CoreConstants.COMMAND_FILE_EXPORT_DIALOG_ID, null);

			if(!ok){
				return;
			}
			
//			if(Model.getSelectedGame().getFilePath() == null || Model.getSelectedGame().getFilePath().equals("")){
//				GuiUtils.setDefaultCovers();
//				throw new FileNotSelectedException();
//			}
			

			PlatformUtils.getHandlerService(viewID).executeCommand(CoreConstants.COMMAND_TOISO_ID, null);
			
		} catch (ExecutionException e1) {
			e1.printStackTrace();
		} catch (NotDefinedException e1) {
			e1.printStackTrace();
		} catch (NotEnabledException e1) {
			e1.printStackTrace();
		} catch (NotHandledException e1) {
			e1.printStackTrace();
		} catch (FileNotSelectedException e1) {
			System.out.println("File not selected");
		} 


	}

}
