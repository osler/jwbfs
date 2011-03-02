package jwbfs.ui.listeners.coverView;

import java.util.LinkedHashMap;

import jwbfs.model.ModelStore;
import jwbfs.model.beans.GameBean;
import jwbfs.model.utils.CoreConstants;
import jwbfs.model.utils.PlatformUtils;
import jwbfs.ui.exceptions.FileNotSelectedException;
import jwbfs.ui.exceptions.NotValidDiscException;
import jwbfs.ui.utils.GuiUtils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Display;

public class UpdateCoverListener extends SelectionAdapter {

	private String viewID;
	protected GameBean bean = null;


	public UpdateCoverListener(String viewID, GameBean bean){
		this.viewID = viewID;
		this.bean = bean;
	}

	@Override
	public void widgetSelected(SelectionEvent e) {


		Display.getDefault().asyncExec(new Runnable() {
			public void run() {

				try {
					bean = ModelStore.getSelectedGame();
					if(bean.getFilePath() == null || bean.getFilePath().equals("")){

						throw new FileNotSelectedException();

					}

					if(bean.getId().equals("not a wii disc")){
						throw new NotValidDiscException();

					}

					String diskID = GuiUtils.getActiveViewID();
					ModelStore.getDisk(diskID).getCoverSettings().setUpdateCover(true);
	
					LinkedHashMap<String,String> parametri = new LinkedHashMap<String,String>();
					parametri.put("diskID",diskID);
					GuiUtils.executeParametrizedCommand(
							CoreConstants.COMMAND_COVER_UPDATE_ID, parametri, null);


				} catch (NotValidDiscException e1) {

				} catch (FileNotSelectedException e1) {

				} catch (Exception ex) {
					GuiUtils.showInfo(ex.getMessage(),SWT.ERROR);
					ex.printStackTrace();
					throw new RuntimeException("Command not Found");
				}


			}
		});	

	}


}
