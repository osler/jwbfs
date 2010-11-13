package jwbfs.ui.handlers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import jwbfs.model.Model;
import jwbfs.model.beans.GameBean;
import jwbfs.model.utils.FileUtils;
import jwbfs.ui.controls.ErrorHandler;
import jwbfs.ui.exceptions.WBFSException;
import jwbfs.ui.utils.GuiUtils;
import jwbfs.ui.utils.Utils;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.SWT;

public class CheckDiscHandler extends AbstractHandler {



	public static  int index = -1;
	

	public CheckDiscHandler() {
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		GameBean bean = null;

		bean = Model.getSelectedGame();


		if(bean.isWbfsToIso()){
			checkWbfs(bean);
		}else {
			checkIso(bean);
		}


		return null;

	}

	private boolean checkWbfs(GameBean bean) {
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
					info[1] = line.split("=")[1].trim();
//					info[1] = line.substring(line.indexOf("=")+1, line.length()).trim();
					info[2] = Utils.getGB(fileWbfs.length());	


				bean.setId(info[0]);
				bean.setTitle(info[1]);
				bean.setScrubGb(info[2]);

				line =input.readLine();
			}
			input.close();


		} catch (IOException e) {
			e.printStackTrace();
			GuiUtils.showError("Error: \nfile "+bean.getFilePath()+ " has errors. Skipping");
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			GuiUtils.showError("Error: \nfile "+bean.getFilePath()+ " has errors. Skipping");
			return false;
		}

		return true;
	}

	private void checkIso(GameBean bean) {

		try {

			String[] info = new String [3];
			
			String filePath = bean.getFilePath();
			if(filePath == null || filePath.equals("")){
				GuiUtils.showInfo("Select a File", SWT.ERROR);
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
