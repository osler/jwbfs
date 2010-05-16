package jwbfs.ui.handlers;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import jwbfs.model.Model;
import jwbfs.model.beans.GameBean;
import jwbfs.ui.controls.ErrorHandler;
import jwbfs.ui.exceptions.NotCorrectDiscFormatException;
import jwbfs.ui.exceptions.WBFSException;
import jwbfs.ui.utils.GuiUtils;
import jwbfs.ui.utils.Utils;
import jwbfs.ui.views.ManagerView;
import jwbfs.ui.views.folder.ProcessView;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;


public class ToISOConvertHandler extends AbstractHandler {

	public static final String ID = "toIso";

	public ToISOConvertHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		

		GameBean bean =  Model.getConvertGameBean();

		String filePath = bean.getFilePath();
		String folderPath = Model.getSettingsBean().getFolderPath();
		
		if(filePath.toLowerCase().endsWith(".iso")){
			try {
				throw new NotCorrectDiscFormatException();
			} catch (NotCorrectDiscFormatException e) {
				return null;
			}
		}
		
		File file = new File(filePath);

		 if(folderPath == null || folderPath.equals("none") || folderPath.equals("") ){
//			 folderPath = file.getAbsolutePath().replace(file.getName(), "");
			 folderPath = Model.getSettingsBean().getDiskPath();
		 }
		
		  try {
			  String path = file.getAbsolutePath();			  	  			  
			  String bin = Utils.getWBFSpath();



			  //processa iso
			  String[]  processo = getProcessParameter(bin,path,folderPath);
			  System.out.println(processo);
			  Process p = Runtime.getRuntime().exec(processo);
			  checkProcessMessages(p);



			  MessageBox msg = new MessageBox(new Shell());
			  msg.setText("Info");
			  msg.setMessage("Operazione avvenuta con successo");
			  msg.open();

		  }
		  catch (WBFSException err) {
			  return null;
		    } catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

	
		
		return null;
	}

	private static boolean checkProcessMessages(Process p) throws IOException, WBFSException,Exception {
	
		  String line;
		
	      BufferedReader input =
		        new BufferedReader
		          (new InputStreamReader(p.getInputStream()));
		      while ((line = input.readLine()) != null) {
		    	  
			      System.out.println(line);
			      
			      ErrorHandler.processError(line);
			      
			      int bar = 0;
			      bar = Utils.getPercentual(line); 
			      
			      
			      ((ManagerView) GuiUtils.getView(ManagerView.ID)).getProgressBar().setSelection(bar);
		      }
		      input.close();
			return true;
		
	}

	private static String[] getProcessParameter(String bin, String path, String folderPath) {
//		if(toIso){
			String[]par = new String[4];
			par[0] = bin;
			par[1]  = path;
			par[2] = "convert"; 
			par[3] = folderPath;
	
			return par;
//	
//		}else{
//			
//	
//		String[] par = new String[8];
//	
//		SettingsBean tab = (SettingsBean) Model.getTabs().get(SettingsBean.INDEX);
//		
//		par[0] = bin; 
//		
//		par[1] = Constants.decodeValue(tab.getCopyPartitions(), 
//				Constants.COPY_PARTITIONS_Text, 
//				Constants.COPY_PARTITIONS_Values);
//		
//		par[2] = Constants.decodeValue(tab.getSplitSize(), 
//				Constants.SPLITSIZE_Text, 
//				Constants.SPLITSIZE_Values);
//	
//		
//		par[3] = Constants.decodeValue(tab.getEnableTXT(), 
//				Constants.ENABLE_TXT_CREATION_Text, 
//				Constants.ENABLE_TXT_CREATION_Values);
//	
//		par[4] =  Constants.decodeValue(tab.getTxtLayout(), 
//				Constants.TXT_LAYOUT_Text, 
//				Constants.TXT_LAYOUT_Values);
//		
//		par[5] = path;
//		
//		//COMMAND
//		par[6] = "convert";
//		
//		par[7] = folderPath;
//	
//		return par;
//		}
	}
}
