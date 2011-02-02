package jwbfs.ui.listeners.coverView;

import jwbfs.model.Model;
import jwbfs.model.beans.CoverPaths;

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

		CoverPaths bean = Model.getSettingsBean().getCoverSettings().getCoverPaths();
		if(bean != null){
			
		String oldValue = "";
		
		if(type.equals("2d")){
			oldValue = bean.getCover2d();
		}
		if(type.equals("3d")){
			oldValue = bean.getCover3d();
		}
		if(type.equals("disc")){
			oldValue = bean.getCoverDisc();
		}

		DirectoryDialog d = new DirectoryDialog(new Shell());
		
		if(bean != null){
			String open = d.open();
			
			if(open == null || open.equals("")){
				open = oldValue;
			}

			if(type.equals("2d")){
				bean.setCover2d(open);
			}
			if(type.equals("3d")){
				bean.setCover3d(open);
			}
			if(type.equals("disc")){
				bean.setCoverDisc(open);
			}
		}
			
		}
	
	}


}
