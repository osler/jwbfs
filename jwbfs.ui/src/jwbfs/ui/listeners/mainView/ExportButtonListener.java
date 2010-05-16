package jwbfs.ui.listeners.mainView;

import jwbfs.model.Model;
import jwbfs.ui.exceptions.FileNotSelectedException;
import jwbfs.ui.handlers.FileDialogExportHandler;
import jwbfs.ui.handlers.ToISOConvertHandler;
import jwbfs.ui.utils.GuiUtils;
import jwbfs.ui.utils.Utils;

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
	
		//TODO 
		
		try {
			Utils.getHandlerService(viewID).executeCommand(FileDialogExportHandler.ID, null);
//			CheckDiscHandler.index = -1;
//			
//			Utils.getHandlerService(viewID).executeCommand(CheckDiscHandler.ID, null);
			
			if(Model.getConvertGameBean().getFilePath() == null || Model.getConvertGameBean().getFilePath().equals("")){
				GuiUtils.setDefaultCovers();
				throw new FileNotSelectedException();
			}
			
//			if(Model.getConvertGameBean().getId().contains("not a wii disc")){
//				GuiUtils.setCover(Constants.NOIMAGE);
//				GuiUtils.setCover3d(Constants.NOIMAGE3D);
//				GuiUtils.setCoverDisc(Constants.NODISC);
//				throw new NotValidDiscException();
//
//			}

//			Utils.getHandlerService(viewID).executeCommand(UpdateCoverHandler.ID, null);
//			if(Model.getConvertGameBean().isWbfsToIso()){
				Utils.getHandlerService(viewID).executeCommand(ToISOConvertHandler.ID, null);
//				Utils.getHandlerService(viewID).executeCommand(UpdateGameListHandler.ID, null);
//			}

		} catch (ExecutionException e1) {
			e1.printStackTrace();
		} catch (NotDefinedException e1) {
			e1.printStackTrace();
		} catch (NotEnabledException e1) {
			e1.printStackTrace();
		} catch (NotHandledException e1) {
			e1.printStackTrace();
		} catch (FileNotSelectedException e1) {
			e1.printStackTrace();
		} 


	}

}
