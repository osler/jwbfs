
package jwbfs.core;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import jwbfs.model.ModelStore;
import jwbfs.model.utils.ConfigUtils;
import jwbfs.model.utils.CoreConstants;
import jwbfs.model.utils.Decode;
import jwbfs.model.utils.FileUtils;
import jwbfs.model.utils.PlatformUtils;
import jwbfs.ui.ContextActivator;
import jwbfs.ui.utils.CoverUtils;
import jwbfs.ui.utils.GuiUtils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

	public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
		super(configurer);
	}

	public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer) {
		return new ApplicationActionBarAdvisor(configurer);
	}

	public void preWindowOpen() {
		
		ConfigUtils.initConfigFile();
		new ModelStore(); //init model
		
		IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
		int x = Integer.parseInt(System.getProperty("window.x"));
		int y = Integer.parseInt(System.getProperty("window.y"));
		configurer.setInitialSize(new Point(x, y));
		configurer.setShowMenuBar(true);
//		configurer.setShowCoolBar(true);
//		configurer.setShowPerspectiveBar(true);
		configurer.setShowStatusLine(false);
		configurer.setTitle("jwbfs - a wbfs_file wrapper");
		
		configurer.setShowProgressIndicator(true);
	
	}

	@Override
	public void postWindowClose() {
		try{
			ConfigUtils.saveConfigFile();
			ConfigUtils.saveDiskConfigFile("configs"+File.separatorChar+Decode.decodeFileName(CoreConstants.VIEW_DISK_1_ID));
			ConfigUtils.saveDiskConfigFile("configs"+File.separatorChar+Decode.decodeFileName(CoreConstants.VIEW_DISK_2_ID));
			ConfigUtils.saveDiskConfigFile("configs"+File.separatorChar+Decode.decodeFileName(CoreConstants.VIEW_DISK_3_ID));
			ConfigUtils.saveDiskConfigFile("configs"+File.separatorChar+Decode.decodeFileName(CoreConstants.VIEW_DISK_4_ID));
			ConfigUtils.saveDiskConfigFile("configs"+File.separatorChar+Decode.decodeFileName(CoreConstants.VIEW_DISK_5_ID));
			ConfigUtils.saveDiskConfigFile("configs"+File.separatorChar+Decode.decodeFileName(CoreConstants.VIEW_DISK_6_ID));
		}catch (Exception e) {
			System.out.println("Configs not saved. ("+e.getMessage()+")");
		}
	}

	@Override
	public void postWindowOpen() {
		if(!PlatformUtils.getChangelog().equals("")){
			GuiUtils.showInfo("Changelog:\n"+PlatformUtils.getChangelog(), SWT.ICON_INFORMATION, true);
			if(!PlatformUtils.isDevelopment()){
				PlatformUtils.clearChangelog();
			}
		}
		
		//update
		LinkedHashMap<String,String> parametriUP = new LinkedHashMap<String,String>();
		parametriUP.put(CoreConstants.PARAM_CONFIRM,"true");
		GuiUtils.executeParametrizedCommand(CoreConstants.COMMAND_UPDATE, parametriUP, null);
		
		//create icon launcher
		//TODO by now only linux
		if(!new File(FileUtils.getJwbfsShortcutPath()).exists() && PlatformUtils.isLinux()){
			LinkedHashMap<String,String> parametriSC = new LinkedHashMap<String,String>();
			parametriSC.put(CoreConstants.PARAM_CONFIRM,"true");
			GuiUtils.executeParametrizedCommand(CoreConstants.COMMAND_CREATE_SHORTCUT, parametriSC, null);
		}
		
		Set<String> disksViewsID = ModelStore.getDisks().keySet();
		Iterator<String> it = disksViewsID.iterator();
		int counter = 0;
		int numDisks = ModelStore.getDisks().size();
//		//switch to correct perspective based on num disks
//		LinkedHashMap<String,String> parametri = new LinkedHashMap<String,String>();
//		parametri.put("numDisks",String.valueOf(numDisks));
//		GuiUtils.executeParametrizedCommand(CoreConstants.COMMAND_DISKS_PERSPECTIVE,parametri,null);
		
		try{
		//refresh games list for every disks
			while (it.hasNext()) {

				String diskViewID = (String) it.next();
				if(numDisks > counter){
					if(ModelStore.getDisk(diskViewID).getDiskPath().trim().equals("")){ 
						continue;
					}

					LinkedHashMap<String,String>  parametri = new LinkedHashMap<String,String>();
					parametri.put("diskID",diskViewID);
					if(counter == 0){
						GuiUtils.executeParametrizedCommand(CoreConstants.COMMAND_REFRESH_FIRST_DISK_ID,parametri,null);
					}else{
						GuiUtils.executeParametrizedCommand(CoreConstants.COMMAND_GAMELIST_UPDATE_ID,parametri,null);
					}
					CoverUtils.setCoversPathFromDiskPath(diskViewID);	
				}
				counter++;
			}
		}catch (Exception e) {
			System.out.println("sorry, i could not update list on startup...");
			e.printStackTrace();
		}
		
		ContextActivator.reloadContext();
	}



}
