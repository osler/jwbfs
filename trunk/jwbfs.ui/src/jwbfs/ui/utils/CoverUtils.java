package jwbfs.ui.utils;

import java.io.File;

import jwbfs.model.ModelStore;
import jwbfs.model.beans.CoverPaths;
import jwbfs.model.utils.CoverConstants;
import jwbfs.model.utils.FileUtils;

import org.eclipse.swt.SWT;

public class CoverUtils {

	public static String get2DPath(String coverPath, String diskID) {

		//reset the coverPath to temp if the loader don't support this kind of cover
		String subPath = CoverConstants.getImage2D(diskID);
		if(subPath == null){
			coverPath = System.getProperty("java.io.tmpdir");
		}
		
		coverPath = coverPath+File.separatorChar
					+subPath;
		
		FileUtils.checkAndCreateFolder(coverPath);

		return coverPath;

	}
	
	public static String get3DPath(String coverPath, String diskID) {

		//reset the coverPath to temp if the loader don't support this kind of cover
		String subPath = CoverConstants.getImage3D(diskID);
		if(subPath == null){
			coverPath = System.getProperty("java.io.tmpdir");
		}
		
		coverPath = coverPath+File.separatorChar
					+CoverConstants.getImage3D(diskID);
		
		FileUtils.checkAndCreateFolder(coverPath);

		return coverPath;

	}
	
	public static String getDISCSPath(String coverPath, String diskID) {

		//reset the coverPath to temp if the loader don't support this kind of cover
		String subPath = CoverConstants.getImageDisc(diskID);
		if(subPath == null){
			coverPath = System.getProperty("java.io.tmpdir");
		}
		
		coverPath = coverPath+File.separatorChar
					+CoverConstants.getImageDisc(diskID);
		
		FileUtils.checkAndCreateFolder(coverPath);

		return coverPath;

	}

	/**
	 * Create all the image folder and sub folder from the wbfs disk path actually used.
	 */
	
	public static void setCoversPathFromDiskPath(String diskID){

		String diskModel = ModelStore.getDiskPath(diskID);
		
		String diskPath = new File(diskModel).getParent();

		String coverPath = diskPath+File.separatorChar
		  +CoverConstants.getFolderName(diskID);
		
		CoverPaths coverPaths = ModelStore.getDisk(diskID).getCoverSettings().getCoverPaths();
		

		if(!coverPathExist(diskPath,diskID)){
			GuiUtils.showInfo("No cover folder found. I will create it.\n"+
					"("+coverPath+")", SWT.ERROR);
			
			coverPath = FileUtils.createCoverFolder(diskPath,diskID);	
			
		}
		
		String coverPath2d = CoverUtils.get2DPath(coverPath,diskID);
		String coverPath3d = CoverUtils.get3DPath(coverPath,diskID);
		String coverPathDisc = CoverUtils.getDISCSPath(coverPath,diskID);
		
		coverPaths.setCover2d(coverPath2d);
		coverPaths.setCover3d(coverPath3d);
		coverPaths.setCoverDisc(coverPathDisc);
	}

	public static boolean coverPathExist(String diskPath,String diskID) {

		String coverPath = diskPath+File.separatorChar
						  +CoverConstants.getFolderName(diskID);
		
		File dirImg = new File(coverPath);
		
		return dirImg.exists();
	}

}
