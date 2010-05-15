package jwbfs.ui.controls;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Properties;

import jwbfs.model.Model;
import jwbfs.model.beans.SettingsBean;
import jwbfs.ui.utils.Utils;

import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

public class Exec {
	
	
	public static void initConfigFile(){
		
		
		String nomeFileProps = "wbfs.ini";

		try {

			Properties props = new Properties();
			
			Bundle bundle = Platform.getBundle("jwbfs.ui");		
			InputStream is = bundle.getEntry("/" + nomeFileProps).openStream();
			props.load(is);


			String keys[] = getFileProperty(Utils.getWbfsINI());	

			
//			String key[]  =  {"wbfs.bin","window.x","window.y"};
			for(int x = 0; x < keys.length; x++){
				System.setProperty(keys[x], props.getProperty(keys[x]));	
			}	

			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String[] getFileProperty(File file) {
		String[] keys = null;
		try {
		int numLines = getNumLine(file)-1;
		FileReader in = new FileReader(file);

		BufferedReader br = new BufferedReader(in);
		keys = new String[numLines];	

		for(int x = 0; x<numLines; x++){
			String line = br.readLine();
			keys[x] = (line.substring(0,line.indexOf("="))).trim();  
		}
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return keys;		
	}

	private static int getNumLine(File file) {
		
		String line;
		int numLine = 0;
		
		try {
			FileReader in= new FileReader(file);
			BufferedReader br = new BufferedReader(in);
					
			line = br.readLine();

		while(line != null){
			numLine = numLine+1;
			line = br.readLine();
		}
		
		} catch (IOException e) {
			return numLine;
		}
		
		return numLine;
	}

	public static void saveConfigFile() {

		SettingsBean settings = (SettingsBean) Model.getBeans().get(SettingsBean.INDEX);
		
//		Method[] methods = settings.getClass().getMethods();
		
	}
	
	

}
