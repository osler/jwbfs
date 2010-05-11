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

}
