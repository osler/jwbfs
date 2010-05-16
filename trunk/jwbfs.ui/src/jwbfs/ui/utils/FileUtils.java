package jwbfs.ui.utils;

import java.io.File;

public class FileUtils {

	public static String getTxtFile(File fileWbfs) {
		
		String fold = fileWbfs.getAbsolutePath().replace(fileWbfs.getName(), "");

		File folder = new File(fold);
		String[] list = folder.list();


		for (int i = 0; i < list.length; i++)  {
			String fileTemp = list[i];
			if(fileTemp.toLowerCase().contains(".txt") && 
					fileTemp.toLowerCase().contains(fileWbfs.getName().replace(".wbfs", "").toLowerCase())){
				System.out.println(fileTemp);
				return fold+fileTemp;
			}
			
		}
		
		return null;
	}

	public static void checkAndCreateFolder(String folder) {
	
		File fol = new File(folder);
		if(fol.exists() && fol.isDirectory()){
			return;
		}else{
			fol.mkdir();
			System.out.println("Folder created: \n"+folder);
			return;
		}
		
	}

	public static boolean coverFileExist(String coverPath) {
		File file = new File(coverPath);
		boolean exist = file.exists();
		if(exist){
			System.out.println("Cover exists:");
			System.out.println(coverPath);
		}
		return exist;
	}

	public static String exist(String path, boolean cover2d, boolean cover3d, boolean coverDisc) {
		path = exist(path);

		String tempDir = System.getProperty("java.io.tmpdir");
		
			if(cover2d && path.equals(tempDir)){
				path = path+File.separatorChar+"2d";
				FileUtils.checkAndCreateFolder(path);
			}
			
			if(cover3d && path.equals(tempDir)){
				path = path+File.separatorChar+"3d";
				FileUtils.checkAndCreateFolder(path);
			}
			
			if(coverDisc && path.equals(tempDir)){
				path = path+File.separatorChar+"disc";
				FileUtils.checkAndCreateFolder(path);	
			}
		
		return path;
	}

	public static String exist(String path) {

		File tmp = new File(path);
		if(!tmp.exists() || tmp.equals("")){
			path = System.getProperty("java.io.tmpdir");
		}
		return path;
	}
}
