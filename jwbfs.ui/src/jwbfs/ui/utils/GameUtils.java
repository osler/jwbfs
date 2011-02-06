package jwbfs.ui.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class GameUtils {

	public static String[] getGameNames(String gameID) {

		File txtFile = new File(PlatformUtils.getTitlesTXTpath());

		ArrayList<String> names = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(txtFile));
			String line = br.readLine();
			while(line!=null){
				
				if(line.trim().contains(gameID)){
					names.add(line.split("=")[1].trim());
				}
				
				line =  br.readLine();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String[] ret = new String[names.size()];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = names.get(i);
		}
		
		return ret;
	}
	
	
}
