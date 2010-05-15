package jwbfs.ui.handlers;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import jwbfs.model.Model;
import jwbfs.model.beans.ProcessBean;
import jwbfs.model.beans.SettingsBean;
import jwbfs.ui.controls.ErrorHandler;
import jwbfs.ui.exceptions.WBFSException;
import jwbfs.ui.utils.FileUtils;
import jwbfs.ui.utils.Utils;
import jwbfs.ui.views.ManagerView;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;

public class CheckDiscHandler extends AbstractHandler {


	public static final String ID = "checkDisk";
	public static  int index = -1;
	

	public CheckDiscHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ProcessBean bean = null;
		
		if(((SettingsBean) Model.getTabs().get(SettingsBean.INDEX)).isManagerMode()){

			for(int x =0; x<Model.getGames().length;x++){
				bean = (ProcessBean) Model.getGames()[x];
				checkWbfs(bean);
			}
			
		}else{
			bean = (ProcessBean) Model.getTabs().get(ProcessBean.INDEX);
			

			if(bean.isWbfsToIso()){
				checkWbfs(bean);
			}else {
				checkIso(bean);
			}

		}

		((SettingsBean) Model.getTabs().get(SettingsBean.INDEX)).setManagerMode(false);
		return null;

	}

	private void checkWbfs(ProcessBean bean) {

//		String infoCmd = "id_title";
		
		try {

			String[] info = new String [3];

			File fileWbfs = new File(bean.getFilePath());
		
			String fileTxt = FileUtils.getTxtFile(fileWbfs);

			File fileTxtPath = new File(fileTxt);
			
			FileReader fis = new FileReader(fileTxtPath); 			
			BufferedReader input = new BufferedReader(fis);
			
			String line = input.readLine();
			while ( line != null) {

				System.out.println(line);

					info[0] = fileWbfs.getName().replace(".wbfs", "");
					info[1] = line.substring(line.indexOf("=")+1, line.length()).trim();
					info[2] = Utils.getGB(fileWbfs.length());	


				bean.setId(info[0]);
				bean.setTitle(info[1]);
				bean.setScrubGb(info[2]);

				line =input.readLine();
			}
			input.close();


		} catch (IOException e) {
			e.printStackTrace();
		}

	

		
	}

	private void checkIso(ProcessBean bean) {

		try {

			String[] info = new String [3];
			
			String filePath = bean.getFilePath();
			if(filePath == null || filePath.equals("")){
				return;
			}

			String path = new File(filePath).getAbsolutePath();			  	  			  
			String bin = Utils.getWBFSpath();

			new File(bin).setExecutable(true);

			String infoCmd = "iso_info";

			String[] checkIso = {bin,path,infoCmd};
			Process pCheck;
			pCheck = Runtime.getRuntime().exec(checkIso);
			//		checkProcessMessages(pCheck);

			String line;

			BufferedReader input =
				new BufferedReader
				(new InputStreamReader(pCheck.getInputStream()));
			line = input.readLine();
			while ( line != null) {

				System.out.println(line);

				ErrorHandler.processError(line);

				String control = "id:";
				if(line.contains(control)){
					line = line.substring(line.indexOf(control),line.length());
					info[0] = line.replaceAll(control, "").trim();
				}

				control = "title:";
				if(line.contains(control)){
					line = line.substring(line.indexOf(control),line.length());
					info[1] = line.replaceAll(control, "").trim();
				}

				control = "scrub gb:";
				if(line.contains(control)){
					line = line.substring(line.indexOf(control),line.length());
					info[2] = line.replaceAll(control, "").trim();
				}

				control = "not a wii disc:";
				if(line.toLowerCase().contains(control) ||
						(info[0] == null && info[1] == null && info[2] == null )){
					info[0] = control.trim();
				}

				bean.setId(info[0]);
				bean.setTitle(info[1]);
				bean.setScrubGb(info[2]);

				line =input.readLine();
				
			}

			input.close();


		} catch (WBFSException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}