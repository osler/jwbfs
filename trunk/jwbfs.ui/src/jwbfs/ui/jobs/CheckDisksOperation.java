package jwbfs.ui.jobs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import jwbfs.model.ModelStore;
import jwbfs.model.beans.GameBean;
import jwbfs.model.utils.FileUtils;
import jwbfs.model.utils.PlatformUtils;
import jwbfs.ui.controls.ErrorHandler;
import jwbfs.ui.exceptions.WBFSException;
import jwbfs.ui.exceptions.WBFSFileExistsException;
import jwbfs.ui.utils.GameUtils;
import jwbfs.ui.utils.GuiUtils;

import org.eclipse.swt.SWT;

public class CheckDisksOperation implements Runnable {

	boolean isWbfs = false;
	
	public CheckDisksOperation(String name, boolean isWbfs) {
		this.isWbfs = isWbfs;
	}

	@Override
	public void run() {
		GameBean bean = null;

		bean = ModelStore.getSelectedGame();

		boolean ok = true;
		if(isWbfs){
			ok = checkWbfs(bean);
		}else {
			try {
				ok =checkIso(bean);
			} catch (WBFSFileExistsException e) {
				e.printStackTrace();
			}
		}

		//Aggiungo nomi aggiuntivi
		if(ok){
			ArrayList<String> gameTitles = GameUtils.getGameAlternativeTitles(bean.getId());
			bean.setGameAlternativeTitles(gameTitles);
		}

//		return Status.OK_STATUS;

	}

	private boolean checkWbfs(GameBean bean) {
		try {
			String[] info = new String [4];
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
					info[2] = PlatformUtils.getGB(fileWbfs.length());
					info[3] = PlatformUtils.getKB(fileWbfs.length());	


				bean.setId(info[0]);
				bean.setTitle(info[1]);
				bean.setScrubGb(info[2]);
				bean.setScrubSize(Long.parseLong(info[3]));

				line =input.readLine();
			}
			input.close();


		} catch (IOException e) {
//			e.printStackTrace();
			System.out.println("Error: \nfile "+bean.getFilePath()+ " has errors. Skipping");
//			GuiUtils.showError("Error: \nfile "+bean.getFilePath()+ " has errors. Skipping");
			return false;
		} catch (Exception e) {
//			e.printStackTrace();
			System.out.println("Error: \nfile "+bean.getFilePath()+ " has errors. Skipping");
//			GuiUtils.showError("Error: \nfile "+bean.getFilePath()+ " has errors. Skipping");
			return false;
		}

		return true;
	}

	private boolean checkIso(GameBean bean) throws WBFSFileExistsException {

		try {

			String[] info = new String [3];
			long scrubSize =0;
			String filePath = bean.getFilePath();
			if(filePath == null || filePath.equals("")){
				GuiUtils.showInfo("Select a File", SWT.ERROR,true);
				return false;
			}

			String path = new File(filePath).getAbsolutePath();			  	  			  
			String bin = PlatformUtils.getWBFSpath();

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
				control = "scrub size:";
				if(line.contains(control)){
					line = line.substring(line.indexOf(control),line.length());
					scrubSize = Long.parseLong(line.replaceAll(control, "").trim());
				}

				control = "not a wii disc:";
				if(line.toLowerCase().contains(control) ||
						(info[0] == null && info[1] == null && info[2] == null )){
					info[0] = control.trim();
				}

				bean.setId(info[0]);
				bean.setTitle(info[1]);
				bean.setScrubGb(info[2]);
				bean.setScrubSize(scrubSize);
				

				line =input.readLine();
				
			}

			input.close();


		} catch (WBFSException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
}
