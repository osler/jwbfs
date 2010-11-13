package jwbfs.ui.utils;

import java.io.File;

import jwbfs.model.Model;
import jwbfs.model.beans.SettingsBean;
import jwbfs.model.utils.FileUtils;

public class CoverUtils {

	/**
	 * create the images folder and its subfolder (2D,3D,discs)
	 * @param diskPath
	 * @return
	 */
	public static String createCoverFolder(String diskPath) {
		
		String coverPath = diskPath+File.separatorChar+CoverConstants.IMAGE_FOLDER_NAME;
		File dirImg = new File(coverPath);
		if(!dirImg.exists()){
			boolean create = GuiUtils.showConfirmDialog("Folder "+dirImg.getAbsolutePath()+" does not exist, create it?");
			if(create){
				dirImg.mkdir();
			}
		}

		createCoverSubFolders(coverPath);
	
		return coverPath;
		
	}

	/**
	 * 
	 * create the images subfolder (2D,3D,discs)
	 * @param coverPath
	 */
	public static void createCoverSubFolders(String coverPath) {
		
		FileUtils.exist(coverPath,true,false,false);
		FileUtils.exist(coverPath,false,true,false);
		FileUtils.exist(coverPath,false,false,true);
	}

	public static String get2DPath(String coverPath) {

		coverPath = coverPath+File.separatorChar+CoverConstants.IMAGE_2D_NAME;;
		FileUtils.checkAndCreateFolder(coverPath);

		return coverPath;

	}
	
	public static String get3DPath(String coverPath) {

		coverPath = coverPath+File.separatorChar+CoverConstants.IMAGE_3D_NAME;;
		FileUtils.checkAndCreateFolder(coverPath);

		return coverPath;

	}
	
	public static String getDISCSPath(String coverPath) {

		coverPath = coverPath+File.separatorChar+CoverConstants.IMAGE_DISC_NAME;;
		FileUtils.checkAndCreateFolder(coverPath);

		return coverPath;

	}

	/**
	 * Create all the image folder and sub folder from the wbfs disk path actually used.
	 */
	
	public static void setCoversPathFromDiskPath() {

		SettingsBean bean = Model.getSettingsBean();
		
		String diskPath = new File(bean.getDiskPath()).getParent();
		
		String coverPath = CoverUtils.createCoverFolder(diskPath);
		
		String coverPath2d = CoverUtils.get2DPath(coverPath);
		String coverPath3d = CoverUtils.get3DPath(coverPath);
		String coverPathDisc = CoverUtils.getDISCSPath(coverPath);
		
		bean.setCoverPath2d(coverPath2d);
		bean.setCoverPath3d(coverPath3d);
		bean.setCoverPathDisc(coverPathDisc);
	}

}
