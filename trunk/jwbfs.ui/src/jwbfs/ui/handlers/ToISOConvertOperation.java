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
import jwbfs.model.utils.CoreConstants;
import jwbfs.ui.controls.ErrorHandler;
import jwbfs.ui.exceptions.NotCorrectDiscFormatException;
import jwbfs.ui.exceptions.WBFSException;
import jwbfs.ui.utils.GuiUtils;
import jwbfs.ui.utils.PlatformUtils;
import jwbfs.ui.views.ManagerView;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class ToISOConvertOperation implements IRunnableWithProgress {

	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {
			
			GameBean exportBean =  Model.getExportGameBean();
			GameBean gameToExport =  Model.getSelectedGame();
			
			monitor.beginTask("Creating wbfs-file: "+gameToExport.getTitle(), 
//					100);
					IProgressMonitor.UNKNOWN);
			
			monitor.subTask("reading wbfs infos");

			monitor.worked(5);
			
			//File selected
			String filePath = gameToExport.getFilePath();
			File fileToExport = new File(filePath);
			
			//Dest File
			String folderPath = exportBean.getFilePath();
			folderPath = folderPath.replace(fileToExport.getName(), "");
			
			if(filePath.toLowerCase().endsWith(".iso")){
				try {
					throw new NotCorrectDiscFormatException();
				} catch (NotCorrectDiscFormatException e) {
					return;
				}
			}
			
			  try {
				  String path = fileToExport.getAbsolutePath();			  	  			  
				  String bin = PlatformUtils.getWBFSpath();
		
				  //processa iso
				  String[]  processo = getProcessParameter(bin,path,folderPath);
				  System.out.println(processo);
				  Process p = Runtime.getRuntime().exec(processo);
				  checkProcessMessages(p,monitor);
		
				  monitor.done();
		
				  GuiUtils.showInfo(gameToExport.getTitle() + " exported to 1n"
						  + gameToExport.getFolderPath(), SWT.NONE, true);
		
			  }
			  catch (WBFSException err) {
				  return;
			    } catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
		
		
			
			return ;
		

	}


	private  static boolean  checkProcessMessages(Process p, IProgressMonitor monitor) throws IOException, WBFSException {
	

		
		  String line;
	      int bar = 0;
	      
	      BufferedReader input =
		        new BufferedReader
		          (new InputStreamReader(p.getInputStream()));
		      while ((line = input.readLine()) != null) {
		    	  
		    	  if(monitor.isCanceled()){
		    		  throw new WBFSException("Process cancelled by user",WBFSException.USER_CANCEL);
		    	  }
		    	  
			      System.out.println(line);
			      
			      ErrorHandler.processError(line);

			      bar = PlatformUtils.getPercentual(line); 
			      monitor.subTask(line);
			      monitor.worked(bar);

//			      ((ManagerView) GuiUtils.getView(ManagerView.ID)).getProgressBar().setSelection(bar);
					
		      }
		      input.close();
			return true;
		
	
		
	}

	private static String[] getProcessParameter(String bin, String path, String folderPath) {
				
		
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
