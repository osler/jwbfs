package jwbfs.ui.utils;

import java.io.File;

import jwbfs.model.Model;
import jwbfs.model.beans.CoverPaths;
import jwbfs.model.beans.SettingsBean;
import jwbfs.model.utils.FileUtils;

public class CoverUtils {

	/**
	 * create the images folder and its subfolder (2D,3D,discs)
	 * @param diskPath
	 * @return
	 */
	public static String createCoverFolder(String diskPath) {
		
		String coverPath = diskPath+File.separatorChar
						  +CoverConstants.getFolderName();
		
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
		
		FileUtils.exist(coverPath,CoverConstants.getImage2D());
		FileUtils.exist(coverPath,CoverConstants.getImage3D());
		FileUtils.exist(coverPath,CoverConstants.getImageDisc());
		FileUtils.exist(coverPath,CoverConstants.getImageFullCover());
	}

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
	
	public static void setCoversPathFromDiskPath() {

		SettingsBean bean = Model.getSettingsBean();
		
		String diskPath = new File(bean.getDiskPath()).getParent();
		
		CoverPaths coverPaths = bean.getCoverSettings().getCoverPaths();
		
		String coverPath = CoverUtils.createCoverFolder(diskPath);
		
		String coverPath2d = CoverUtils.get2DPath(coverPath);
		String coverPath3d = CoverUtils.get3DPath(coverPath);
		String coverPathDisc = CoverUtils.getDISCSPath(coverPath);
		
		coverPaths.setCover2d(coverPath2d);
		coverPaths.setCover3d(coverPath3d);
		coverPaths.setCoverDisc(coverPathDisc);
	}

}
