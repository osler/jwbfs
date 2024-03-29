package jwbfs.ui.handlers;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;

import jwbfs.model.ModelStore;
import jwbfs.model.beans.GameBean;
import jwbfs.model.beans.SettingsBean;
import jwbfs.model.utils.PlatformUtils;
import jwbfs.model.utils.WBFSFileConstants;
import jwbfs.ui.controls.ErrorHandler;
import jwbfs.ui.exceptions.MonitorCancelException;
import jwbfs.ui.exceptions.NotCorrectDiscFormatException;
import jwbfs.ui.exceptions.WBFSException;
import jwbfs.ui.exceptions.WBFSFileExistsException;
import jwbfs.ui.utils.GuiUtils;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;

public class ToISOConvertOperation implements IRunnableWithProgress {

	
	GameBean exportBean =  ModelStore.getExportGameBean();
	GameBean gameToExport =  ModelStore.getSelectedGame();
	
	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {
		
			
			monitor.beginTask("Creating wbfs-file: "+gameToExport.getTitle(), 
//					100);
					IProgressMonitor.UNKNOWN);
			
			monitor.subTask("reading wbfs infos");

			monitor.worked(5);
			
			//File selected
			String filePath = gameToExport.getFilePath();
			File fileToExport = new File(filePath);
			
			//Dest File
			String fileOutPath = exportBean.getFilePath();
			fileOutPath = fileOutPath.replace(fileToExport.getName(), "");
			
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
				  String[]  processo = getProcessParameter(bin,path,fileOutPath);
				  System.out.println(processo);
				  Process p = Runtime.getRuntime().exec(processo);
				  checkProcessMessages(p,monitor);
		
				  monitor.done();
		
				  GuiUtils.showInfo(gameToExport.getTitle() + " exported to \n"
						  + fileOutPath, SWT.NONE, true);

			    } catch (IOException e) {
			  	  monitor.done();
			  	  e.printStackTrace();
				} catch (WBFSFileExistsException e) {
				  	  monitor.done();
				} catch (WBFSException e) {
				  	  monitor.done();
				} catch (MonitorCancelException e) {
				  	  monitor.done();
				}
		
			return;
		

	}


	private boolean  checkProcessMessages(Process p, IProgressMonitor monitor) throws IOException, WBFSException, WBFSFileExistsException, MonitorCancelException {
	
		  String line;
	      int bar = 0;
	      
	      BufferedReader input =
		        new BufferedReader
		          (new InputStreamReader(p.getInputStream()));
		      while ((line = input.readLine()) != null) {
		    	  
		    	  if(monitor.isCanceled()){
		    		  p.destroy();
		    		  throw new MonitorCancelException("User cancelled",new File(exportBean.getFilePath()));
		    	  }
		    	  
			      System.out.println(line);
			      
			      ErrorHandler.processError(line,this);

			      bar = PlatformUtils.getPercentual(line); 
			      monitor.subTask(line);
			      monitor.worked(bar);

//			      ((ManagerView) GuiUtils.getView(ManagerView.ID)).getProgressBar().setSelection(bar);
					
		      }
		      input.close();
			return true;
		
	
		
	}

	private static String[] getProcessParameter(String bin, String path, String fileOutPath) {
				
		
			String[] par = new String[8];
		
			SettingsBean tab = (SettingsBean) ModelStore.getSettingsBean();
			
			par[0] = bin; 
			
			par[1] = WBFSFileConstants.decodeValue(tab.getCopyPartitions(), 
					WBFSFileConstants.COPY_PARTITIONS_Text, 
					WBFSFileConstants.COPY_PARTITIONS_Values);
			
			par[2] = WBFSFileConstants.decodeValue(tab.getSplitSize(), 
					WBFSFileConstants.SPLITSIZE_Text, 
					WBFSFileConstants.SPLITSIZE_Values);
		
			
			par[3] = WBFSFileConstants.decodeValue(tab.isEnableTXT(),  
					WBFSFileConstants.ENABLE_TXT_CREATION_Values);
		
			par[4] =  WBFSFileConstants.decodeValue(tab.getTxtLayout(), 
					WBFSFileConstants.TXT_LAYOUT_Text, 
					WBFSFileConstants.TXT_LAYOUT_Values);
			
			par[5] = path;
			
			//COMMAND
			par[6] = "convert";
			
			par[7] = fileOutPath;
		
			return par;
	//		}
		}
	
	

}
