package jwbfs.ui.handlers;

import java.util.LinkedHashMap;

import jwbfs.model.ModelStore;
import jwbfs.model.utils.CoreConstants;
import jwbfs.ui.utils.GuiUtils;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.TableViewer;

public class RefreshAllDisksHandler extends AbstractHandler {


	public RefreshAllDisksHandler() {
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {

		int numDisks = 6;
		for(int i=0;i<numDisks;i++){
			String diskID = GuiUtils.decodeDiskID(i);
			
			if(ModelStore.getDisk(diskID).getDiskPath().trim().equals("")){
				continue;
			}
			
			LinkedHashMap<String,String> parametri = new LinkedHashMap<String,String>();
			parametri.put("diskID",diskID);
			GuiUtils.executeParametrizedCommand(CoreConstants.COMMAND_GAMELIST_UPDATE_ID,parametri,null);
			
			try{
				TableViewer table = GuiUtils.getManagerTableViewer(diskID);
				table.setInput(ModelStore.getGames(diskID));
				table.refresh();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return true;
	}
}
