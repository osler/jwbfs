package jwbfs.ui.listeners.coverView;

import jwbfs.model.Model;
import jwbfs.model.beans.SettingsBean;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Shell;

public class FolderCoverDialogListener extends SelectionAdapter {

	private String viewID;
	private String type;

	public FolderCoverDialogListener(String viewID){
		this.viewID = viewID;
	}
	
	public FolderCoverDialogListener(String viewID, String type){
		this.viewID = viewID;
		this.type = type;
	}
	
	@Override
	public void widgetSelected(SelectionEvent e) {

		System.out.println("Launching Cover Folder Selection Dialog");

		SettingsBean bean = Model.getSettingsBean();
		if(bean != null){
			
		String oldValue = "";
		
		if(type.equals("2d")){
			oldValue = bean.getCoverPath2d();
		}
		if(type.equals("3d")){
			oldValue = bean.getCoverPath3d();
		}
		if(type.equals("disc")){
			oldValue = bean.getCoverPathDisc();
		}

		DirectoryDialog d = new DirectoryDialog(new Shell());
		
		if(bean != null){
			String open = d.open();
			
			if(open == null || open.equals("")){
				open = oldValue;
			}

			if(type.equals("2d")){
				bean.setCoverPath2d(open);
			}
			if(type.equals("3d")){
				bean.setCoverPath3d(open);
			}
			if(type.equals("disc")){
				bean.setCoverPathDisc(open);
			}
		}
			
		}
	
	}


}
