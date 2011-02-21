package jwbfs.ui.listeners.mainView;

import java.util.LinkedHashMap;

import jwbfs.model.ModelStore;
import jwbfs.model.utils.CoreConstants;
import jwbfs.ui.utils.GuiUtils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class UpdateGameListListener extends SelectionAdapter {

	private String viewID;


	public UpdateGameListListener(String viewID){
		this.viewID = viewID;
	}
	
	@Override
	public void widgetSelected(SelectionEvent e) {

		
		try {
		
		LinkedHashMap<String,String> parametri = new LinkedHashMap<String,String>();
		parametri.put("diskID",viewID);
		GuiUtils.executeParametrizedCommand(CoreConstants.COMMAND_GAMELIST_UPDATE_ID,parametri,null);	

		ModelStore.getExportGameBean().clean();

		} catch (Exception ex) {
			GuiUtils.showInfo(ex.getMessage(),SWT.ERROR);
			ex.printStackTrace();
			throw new RuntimeException("Command not Found");
		}
	
	}


}
