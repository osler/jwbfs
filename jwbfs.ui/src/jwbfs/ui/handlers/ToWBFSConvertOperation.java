package jwbfs.ui.handlers;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

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
import jwbfs.ui.jobs.UpdateGameListOperation;
import jwbfs.ui.utils.GuiUtils;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;

public class ToWBFSConvertOperation extends Job {

	public ToWBFSConvertOperation(String name) {
		super(name);
	}

	GameBean bean = (GameBean) ModelStore.getSelectedGame();

	@Override
	public IStatus run(IProgressMonitor monitor) {
		
			
			monitor.beginTask("Creating wbfs-file: "+bean.getTitle(), 
//					100);
					IProgressMonitor.UNKNOWN);
			
			monitor.subTask("reading iso infos");

			monitor.worked(5);
			
			String filePath = bean.getFilePath();
			
			String activeViewID = GuiUtils.getActiveViewID();
			String fileOutPath = ModelStore.getDiskPath(activeViewID);
			
			if(filePath.toLowerCase().endsWith(".wbfs")){
				try {
					throw new NotCorrectDiscFormatException();
				} catch (NotCorrectDiscFormatException e) {
					return Status.CANCEL_STATUS;
				}
			}
			
			File file = new File(filePath);

			 if(fileOutPath == null || fileOutPath.equals("none") || fileOutPath.equals("") ){
//				 fileOutPath = file.getAbsolutePath().replace(file.getName(), "");
				 fileOutPath = ModelStore.getDiskPath(activeViewID);
			 }
			
			  try {
				  String path = file.getAbsolutePath();			  	  			  
				  String bin = PlatformUtils.getWBFSpath();


				  monitor.worked(10);
				  
				  //processa iso
				  String[]  processo = getProcessParameter(bin,path,fileOutPath);
				  
				  String processoLogString = "";
				  for(int i=0;i<processo.length;i++){
					  processoLogString = processoLogString+ " " +processo[i];
				  }
				  System.out.println(processoLogString);
				  
				  Process p = Runtime.getRuntime().exec(processo);
				  checkProcessMessages(p,monitor);

			      monitor.done();

//			      GuiUtils.showInfo(bean.getTitle()+" Added to:\n"+fileOutPath, SWT.NONE, true);
			      
					Display.getDefault().asyncExec(
									new UpdateGameListOperation(
											"Updating games list",
											GuiUtils.getActiveViewID())
									);
					
		
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
			  
			return Status.OK_STATUS;	
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
