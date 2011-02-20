package jwbfs.ui.listeners.mainView;

import jwbfs.model.ModelStore;
import jwbfs.model.utils.CoreConstants;
import jwbfs.model.utils.PlatformUtils;
import jwbfs.ui.exceptions.NotValidDiscException;
import jwbfs.ui.utils.GuiUtils;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Display;

public class AddButtonListener extends SelectionAdapter {


	private String viewID;

	public AddButtonListener(String viewID){
		this.viewID = viewID;
	}


	@Override
	public void widgetSelected(SelectionEvent e) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				try {
					boolean ok = (Boolean) PlatformUtils.getHandlerService(viewID).executeCommand(CoreConstants.COMMAND_FILE_IMPORT_DIALOG_ID, null);

					if(!ok){
						return;
					}

					PlatformUtils.getHandlerService(viewID).executeCommand(CoreConstants.COMMAND_CHECKDISK_ID, null);


					if(ModelStore.getSelectedGame().getId().contains("not a wii disc")){
						GuiUtils.setDefaultCovers();
						throw new NotValidDiscException();

					}

					PlatformUtils.getHandlerService(viewID).executeCommand(CoreConstants.COMMAND_COVER_UPDATE_ID, null);
					if(ModelStore.getSelectedGame().isIsoToWbfs()){
						PlatformUtils.getHandlerService(viewID).executeCommand(CoreConstants.COMMAND_TOWBFS_ID, null);
						PlatformUtils.getHandlerService(viewID).executeCommand(CoreConstants.COMMAND_GAMELIST_UPDATE_ID, null);
						GuiUtils.setDefaultCovers();
					}


				} catch (ExecutionException e1) {
					e1.printStackTrace();
				} catch (NotDefinedException e1) {
					e1.printStackTrace();
				} catch (NotEnabledException e1) {
					e1.printStackTrace();
				} catch (NotHandledException e1) {
					e1.printStackTrace();
				} catch (NotValidDiscException e1) {
					e1.printStackTrace();
				}
			}
		});
	}

}
