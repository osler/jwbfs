package jwbfs.ui.utils;

import java.io.File;

import jwbfs.model.ModelStore;
import jwbfs.model.beans.CoverPaths;
import jwbfs.model.beans.SettingsBean;
import jwbfs.model.utils.CoverConstants;
import jwbfs.model.utils.FileUtils;

import org.eclipse.swt.SWT;

public class CoverUtils {

	public static String get2DPath(String coverPath) {

		//reset the coverPath to temp if the loader don't support this kind of cover
		String subPath = CoverConstants.getImage2D();
		if(subPath == null){
			coverPath = System.getProperty("java.io.tmpdir");
		}
		
		coverPath = coverPath+File.separatorChar
					+subPath;
		
		FileUtils.checkAndCreateFolder(coverPath);

		return coverPath;

	}
	
	public static String get3DPath(String coverPath) {

		//reset the coverPath to temp if the loader don't support this kind of cover
		String subPath = CoverConstants.getImage3D();
		if(subPath == null){
			coverPath = System.getProperty("java.io.tmpdir");
		}
		
		coverPath = coverPath+File.separatorChar
					+CoverConstants.getImage3D();
		
		FileUtils.checkAndCreateFolder(coverPath);

		return coverPath;

	}
	
	public static String getDISCSPath(String coverPath) {

		//reset the coverPath to temp if the loader don't support this kind of cover
		String subPath = CoverConstants.getImageDisc();
		if(subPath == null){
			coverPath = System.getProperty("java.io.tmpdir");
		}
		
		coverPath = coverPath+File.separatorChar
					+CoverConstants.getImageDisc();
		
		FileUtils.checkAndCreateFolder(coverPath);

		return coverPath;

	}

	/**
	 * Create all the image folder and sub folder from the wbfs disk path actually used.
	 */
	
	public static void setCoversPathFromDiskPath(){

		  
		SettingsBean bean = ModelStore.getSettingsBean();
		
		String diskModel = ModelStore.getDiskPath(GuiUtils.getActiveViewID());
		
		String diskPath = new File(diskModel).getParent();

		String coverPath = diskPath+File.separatorChar
		  +CoverConstants.getFolderName();
		
		CoverPaths coverPaths = bean.getCoverSettings().getCoverPaths();
		

		if(!coverPathExist(diskPath)){
			GuiUtils.showInfo("No cover folder found. I will create it.\n"+
					"("+coverPath+")", SWT.ERROR);
			
			coverPath = FileUtils.createCoverFolder(diskPath);	
			
		}
		
		
		String coverPath2d = CoverUtils.get2DPath(coverPath);
		String coverPath3d = CoverUtils.get3DPath(coverPath);
		String coverPathDisc = CoverUtils.getDISCSPath(coverPath);
		
		coverPaths.setCover2d(coverPath2d);
		coverPaths.setCover3d(coverPath3d);
		coverPaths.setCoverDisc(coverPathDisc);
	}

	public static boolean coverPathExist(String diskPath) {

		String coverPath = diskPath+File.separatorChar
						  +CoverConstants.getFolderName();
		
		File dirImg = new File(coverPath);
		
		return dirImg.exists();
	}

}
