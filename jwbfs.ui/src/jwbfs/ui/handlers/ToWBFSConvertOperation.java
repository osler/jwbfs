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
import jwbfs.ui.exceptions.MonitorCancelException;
import jwbfs.ui.exceptions.NotCorrectDiscFormatException;
import jwbfs.ui.exceptions.WBFSException;
import jwbfs.ui.exceptions.WBFSFileExistsException;
import jwbfs.ui.utils.GuiUtils;
import jwbfs.ui.utils.PlatformUtils;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;

public class ToWBFSConvertOperation implements IRunnableWithProgress {

	GameBean bean = (GameBean) Model.getSelectedGame();

	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {
		
			
			monitor.beginTask("Creating wbfs-file: "+bean.getTitle(), 
//					100);
					IProgressMonitor.UNKNOWN);
			
			monitor.subTask("reading iso infos");

			monitor.worked(5);
			
			String filePath = bean.getFilePath();
			String fileOutPath =  Model.getSettingsBean().getFolderPath();
			
			if(filePath.toLowerCase().endsWith(".wbfs")){
				try {
					throw new NotCorrectDiscFormatException();
				} catch (NotCorrectDiscFormatException e) {
					return;
				}
			}
			
			File file = new File(filePath);

			 if(fileOutPath == null || fileOutPath.equals("none") || fileOutPath.equals("") ){
//				 fileOutPath = file.getAbsolutePath().replace(file.getName(), "");
				 fileOutPath = Model.getSettingsBean().getDiskPath();
			 }
			
			  try {
				  String path = file.getAbsolutePath();			  	  			  
				  String bin = PlatformUtils.getWBFSpath();


				  monitor.worked(10);
				  
				  //processa iso
				  String[]  processo = getProcessParameter(bin,path,fileOutPath);
				  System.out.println(processo);
				  Process p = Runtime.getRuntime().exec(processo);
				  checkProcessMessages(p,monitor);

			      monitor.done();

			      GuiUtils.showInfo(bean.getTitle()+" Added to:\n"+bean.getFilePath(), SWT.NONE, true);
			      
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

			  monitor.done();	
	}


	private  boolean  checkProcessMessages(Process p, IProgressMonitor monitor) throws IOException, WBFSException, WBFSFileExistsException, MonitorCancelException {

		  String line;
	      int bar = 0;
	      
	      BufferedReader input =
		        new BufferedReader
		          (new InputStreamReader(p.getInputStream()));
	      
	      String outputFileName = "";
	      
		      while ((line = input.readLine()) != null) {
		    	
		    	  if(line.contains("Writing:")){
		    		  outputFileName = line.substring(line.indexOf(":")+1, line.length());
		    		  outputFileName = outputFileName.trim();
		    	  }
		    	  
		    	  if(monitor.isCanceled()){
		    		  p.destroy();

		    		  throw new MonitorCancelException("User cancelled",new File(outputFileName));
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
			
			par[7] = fileOutPath;
		
			return par;
	//		}
		}
	
	

}
