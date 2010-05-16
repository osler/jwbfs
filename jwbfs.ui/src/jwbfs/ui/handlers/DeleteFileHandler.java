package jwbfs.ui.handlers;

import java.io.File;

import jwbfs.model.Model;
import jwbfs.model.beans.GameBean;
import jwbfs.ui.utils.FileUtils;
import jwbfs.ui.utils.GuiUtils;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public class DeleteFileHandler extends AbstractHandler {


	public static final String ID = "deleteFile";

	public DeleteFileHandler() {
	}


	public Object execute(ExecutionEvent event) throws ExecutionException {

		GameBean bean = (GameBean) Model.getSelectedGame();
		if(bean != null){

			boolean res = GuiUtils.showConfirmDialog("Delete '"+bean.getTitle()+"' ?");

			if(res){
				File fileWbfs = new File(bean.getFilePath());
				File fileTxt = new File(FileUtils.getTxtFile(fileWbfs));
				File folder = new File(bean.getFilePath().replace(fileWbfs.getName(), ""));
				
				if(fileWbfs.exists()){
					System.out.println("Deleting "+fileWbfs.getAbsolutePath());
					fileWbfs.delete();
					System.out.println("Deleted!");
				}
				if(fileTxt.exists()){
					System.out.println("Deleting "+fileTxt.getAbsolutePath());
					fileTxt.delete();
					System.out.println("Deleted!");
				}
				if(folder.exists() && folder.isDirectory()){
					System.out.println("Deleting Folder"+folder.getAbsolutePath());
					folder.delete();
					System.out.println("Deleted!");
				}
				
			}



		}

		return null;
	}
}
