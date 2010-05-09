package jwbfs.ui.handlers;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import jwbfs.model.Model;
import jwbfs.model.beans.ProcessBean;
import jwbfs.ui.controls.ErrorHandler;
import jwbfs.ui.exceptions.WBFSException;
import jwbfs.ui.utils.Utils;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public class CheckDiscHandler extends AbstractHandler {


	public static final String ID = "checkDisk";
	
	public CheckDiscHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {


		String[] info = new String [3];
		try {

			ProcessBean bean = (ProcessBean) Model.getTabs().get(ProcessBean.INDEX);

			String path = new File(bean.getFilePath()).getAbsolutePath();			  	  			  
			String bin = Utils.getWBFSpath();

			new File(bin).setExecutable(true);

			String infoCmd;
			if(bean.isWbfsToIso()){
				infoCmd = "id_title";
			}else {
				infoCmd = "iso_info";
			}

			String[] checkIso = {bin,path,infoCmd};
			Process pCheck;
			pCheck = Runtime.getRuntime().exec(checkIso);
//			checkProcessMessages(pCheck);
			
			String line;

			BufferedReader input =
				new BufferedReader
				(new InputStreamReader(pCheck.getInputStream()));
			line = input.readLine();
			while ( line != null) {

				System.out.println(line);

				ErrorHandler.processError(line);

				if(bean.isWbfsToIso()){

					info[0] = line;
					info[1] = "";
					info[2] = "";
				}else{

					if(line.contains("id:")){
						line = line.substring(line.indexOf("id:"),line.length());
						info[0] = line;
					}

					if(line.contains("title:")){
						line = line.substring(line.indexOf("title:"),line.length());
						info[1] = line;
					}

					if(line.contains("scrub gb:")){
						line = line.substring(line.indexOf("scrub gb:"),line.length());
						info[2] = line;
					}

					if(line.toLowerCase().contains("not a wii disc") ||
							(info[0] == null && info[1] == null && info[2] == null )){
						info[0] = "not a wii disc";
					}

				}
				
				bean.setId(info[0]);
				bean.setTitle(info[1]);
				bean.setScrubGb(info[2]);

//				int bar = 0;
//				bar = Utils.getPercentual(line); 
//
//				((ConvertView) ((MainView) GuiUtils.getMainView())
//						.getTabs().get(ConvertView.INDEX))
//						.getProgressBar().setSelection(bar);

				line =input.readLine();
			}
			input.close();


		} catch (WBFSException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 

		return info;

	}
}
