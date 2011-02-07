package jwbfs.ui.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class GameUtils {

	public static ArrayList<String> getGameAlternativeTitles(String gameID) {

		File txtFile = new File(PlatformUtils.getTitlesTXTpath());

		ArrayList<String> names = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(txtFile));
			String line = br.readLine();
			while(line!=null){
				
				if(line.trim().contains(gameID)){
					names.add((line.split("=")[1]).trim());
				}
				
				line =  br.readLine();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return names;
	}
	
	
	public static String decodeGameName(int indexGame, String [] names){
		if(indexGame == -1){
			indexGame =  0;
		}
		
		return names[indexGame];
	}
	
	public static int indexOfGameName(String name, String [] names){
		
		for (int i = 0; i < names.length; i++) {
			String string = names[i];
			
			if(string.trim().equals(name.trim())){
				return i;
			}
			
		}
		
		return 0;
	}
	
}
