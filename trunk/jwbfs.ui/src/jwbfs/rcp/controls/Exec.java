package jwbfs.rcp.controls;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import jwbfs.model.Model;
import jwbfs.model.SettingsTabConstants;
import jwbfs.model.beans.SettingsTab;
import jwbfs.rcp.utils.Utils;
import jwbfs.rcp.views.MainView;
import jwbfs.rcp.views.tabs.ISOtoWBFSTabView;

import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Bundle;

public class Exec {
	
	
	public static void initConfigFile(){
		
		
		String nomeFileProps = "wbfs.ini";

		try {

			Properties props = new Properties();
			
			Bundle bundle = Platform.getBundle("jwbfs.ui");		
			InputStream is = bundle.getEntry("/" + nomeFileProps).openStream();
			props.load(is);
			
			String key[]  =  {"wbfs.bin","window.x","window.y"};
			for(int x = 0; x < key.length; x++){
				System.setProperty(key[x], props.getProperty(key[x]));	
			}	
			
//			InputStreamReader in= new InputStreamReader(is);
//			FileReader in= new FileReader(new File(file));
//			BufferedReader bf = new BufferedReader(in);
//			String key = bf.readLine();
//			while( key != null){
//				key = bf.readLine();  
//			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public static void convert(String filePath,String folderPath, boolean toIso) {
		

		File file = new File(filePath);

		 if(folderPath == null || folderPath.equals("none") || folderPath.equals("") ){
			 folderPath = file.getAbsolutePath().replace(file.getName(), "");
		 }
		
		  try {
			  String path = file.getAbsolutePath();			  	  			  
			  String bin = Utils.getWBFSpath();



			  //processa iso
			  String[]  processo = getProcessParameter(bin,path,folderPath,toIso);
			  System.out.println(processo);
			  Process p = Runtime.getRuntime().exec(processo);
			  checkProcessMessages(p);



			  MessageBox msg = new MessageBox(new Shell());
			  msg.setText("Info");
			  msg.setMessage("Operazione avvenuta con successo");
			  msg.open();

		  }
		  catch (WBFSException err) {
			  return;
		    } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}


	private static String[] getProcessParameter(String bin, String path, String folderPath, boolean toIso) {
		if(toIso){
			String[]par = new String[4];
			par[0] = bin;
			par[1]  = path;
			par[2] = "convert"; 
			par[3] = folderPath;

			return par;

		}else{
			

		String[] par = new String[8];

		SettingsTab tab = (SettingsTab) Model.getTabs().get(SettingsTab.INDEX);
		
		par[0] = bin; 
		
		par[1] = SettingsTabConstants.decodeValue(tab.getCopyPartitions(), 
				SettingsTabConstants.COPY_PARTITIONS_Text, 
				SettingsTabConstants.COPY_PARTITIONS_Values);
		
		par[2] = SettingsTabConstants.decodeValue(tab.getSplitSize(), 
				SettingsTabConstants.SPLITSIZE_Text, 
				SettingsTabConstants.SPLITSIZE_Values);

		
		par[3] = SettingsTabConstants.decodeValue(tab.getEnableTXT(), 
				SettingsTabConstants.ENABLE_TXT_CREATION_Text, 
				SettingsTabConstants.ENABLE_TXT_CREATION_Values);

		par[4] =  SettingsTabConstants.decodeValue(tab.getTxtLayout(), 
				SettingsTabConstants.TXT_LAYOUT_Text, 
				SettingsTabConstants.TXT_LAYOUT_Values);
		
		par[5] = path;
		
		//COMMAND
		par[6] = "convert";
		
		par[7] = folderPath;

		return par;
		}
	}


	public static String[] checkIso(File file,boolean iso) throws IOException, WBFSException {
		
		  String path = file.getAbsolutePath();			  	  			  
		  String bin = Utils.getWBFSpath();
		  
		  new File(bin).setExecutable(true);

		  String infoCmd;
		  if(iso){
			  infoCmd = "id_title";
		  }else {
			  infoCmd = "iso_info";
		  }
		
		  String[] checkIso = {bin,path,infoCmd};
	      Process pCheck = Runtime.getRuntime().exec(checkIso);

	      String[] info = new String [3];
	  	
		  String line;
		
	      BufferedReader input =
		        new BufferedReader
		          (new InputStreamReader(pCheck.getInputStream()));
	      line = input.readLine();
		      while ( line != null) {
		    	  
			      System.out.println(line);
			      
			      ErrorHandler.processError(line);
			      
			      if(iso){
			    	  
				    	  info[0] = line;
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
			      
			      int bar = 0;
			      bar = Utils.getPercentual(line); 
			      
			      MainView view = (MainView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(MainView.ID);
			      
			      ((ISOtoWBFSTabView) view.getTabs().get(ISOtoWBFSTabView.INDEX)).getProgressBar().setSelection(bar);

			      line =input.readLine();
		      }
		      input.close();
			
		      return info;
	}


	private static boolean checkProcessMessages(Process p) throws IOException, WBFSException {
	
		  String line;
		
	      BufferedReader input =
		        new BufferedReader
		          (new InputStreamReader(p.getInputStream()));
		      while ((line = input.readLine()) != null) {
		    	  
			      System.out.println(line);
			      
			      ErrorHandler.processError(line);
			      
			      int bar = 0;
			      bar = Utils.getPercentual(line); 
			      
			      MainView view = (MainView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(MainView.ID);
			      
			      ((ISOtoWBFSTabView) view.getTabs().get(ISOtoWBFSTabView.INDEX)).getProgressBar().setSelection(bar);

		      }
		      input.close();
			return true;
		
	}
	
	

}
