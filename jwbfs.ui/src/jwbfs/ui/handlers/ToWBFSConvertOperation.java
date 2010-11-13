package jwbfs.ui.handlers;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;

import jwbfs.model.Model;
import jwbfs.model.beans.GameBean;
import jwbfs.model.beans.SettingsBean;
import jwbfs.model.utils.Constants;
import jwbfs.ui.controls.ErrorHandler;
import jwbfs.ui.exceptions.NotCorrectDiscFormatException;
import jwbfs.ui.exceptions.WBFSException;
import jwbfs.ui.utils.GuiUtils;
import jwbfs.ui.utils.Utils;
import jwbfs.ui.views.ManagerView;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class ToWBFSConvertOperation implements IRunnableWithProgress {

	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {

			GameBean bean = (GameBean) Model.getConvertGameBean();
			
			monitor.beginTask("Creating wbfs-file: "+bean.getTitle(), IProgressMonitor.UNKNOWN);
			
			monitor.subTask("reading iso infos");

			monitor.worked(5);
			
			String filePath = bean.getFilePath();
			String folderPath =  Model.getSettingsBean().getFolderPath();
			
			if(filePath.toLowerCase().endsWith(".wbfs")){
				try {
					throw new NotCorrectDiscFormatException();
				} catch (NotCorrectDiscFormatException e) {
					return;
				}
			}
			
			File file = new File(filePath);

			 if(folderPath == null || folderPath.equals("none") || folderPath.equals("") ){
//				 folderPath = file.getAbsolutePath().replace(file.getName(), "");
				 folderPath = Model.getSettingsBean().getDiskPath();
			 }
			
			  try {
				  String path = file.getAbsolutePath();			  	  			  
				  String bin = Utils.getWBFSpath();


				  monitor.worked(10);
				  
				  //processa iso
				  String[]  processo = getProcessParameter(bin,path,folderPath);
				  System.out.println(processo);
				  Process p = Runtime.getRuntime().exec(processo);
				  checkProcessMessages(p,monitor);

			      monitor.done();

				  MessageBox msg = new MessageBox(new Shell());
				  msg.setText("Info");
				  msg.setMessage(bean.getTitle()+" Added");
				  msg.open();

			  }
			  catch (WBFSException err) {
				  return ;
			    } catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}


	}

	private  static boolean  checkProcessMessages(Process p, IProgressMonitor monitor) throws IOException, WBFSException {
	

		
		  String line;
		
	      BufferedReader input =
		        new BufferedReader
		          (new InputStreamReader(p.getInputStream()));
		      while ((line = input.readLine()) != null) {
		    	  
		    	  if(monitor.isCanceled()){
		    		  throw new WBFSException("Process cancelled by user",WBFSException.USER_CANCEL);
		    	  }
		    	  
			      System.out.println(line);
			      
			      ErrorHandler.processError(line);
			      
			      int bar = 0;
			      bar = Utils.getPercentual(line); 
			      monitor.subTask(line);
			      monitor.worked(bar);

//			      ((ManagerView) GuiUtils.getView(ManagerView.ID)).getProgressBar().setSelection(bar);
					
		      }
		      input.close();
			return true;
		
	
		
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
			      
			      ((ManagerView) GuiUtils.getView(Constants.MAINVIEW_ID)).getProgressBar().setSelection(bar);
					
		      }
		      input.close();
			return true;
		
	}

	private static String[] getProcessParameter(String bin, String path, String folderPath) {
	//		if(toIso){
	//			String[]par = new String[4];
	//			par[0] = bin;
	//			par[1]  = path;
	//			par[2] = "convert"; 
	//			par[3] = folderPath;
	//	
	//			return par;
	//	
	//		}else{
				
		
			String[] par = new String[8];
		
			SettingsBean tab = (SettingsBean) Model.getBeans().get(SettingsBean.INDEX);
			
			par[0] = bin; 
			
			par[1] = Constants.decodeValue(tab.getCopyPartitions(), 
					Constants.COPY_PARTITIONS_Text, 
					Constants.COPY_PARTITIONS_Values);
			
			par[2] = Constants.decodeValue(tab.getSplitSize(), 
					Constants.SPLITSIZE_Text, 
					Constants.SPLITSIZE_Values);
		
			
			par[3] = Constants.decodeValue(tab.isEnableTXT(),  
					Constants.ENABLE_TXT_CREATION_Values);
		
			par[4] =  Constants.decodeValue(tab.getTxtLayout(), 
					Constants.TXT_LAYOUT_Text, 
					Constants.TXT_LAYOUT_Values);
			
			par[5] = path;
			
			//COMMAND
			par[6] = "convert";
			
			par[7] = folderPath;
		
			return par;
	//		}
		}
	
	

}
