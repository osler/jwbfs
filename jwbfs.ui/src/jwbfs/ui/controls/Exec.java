package jwbfs.ui.controls;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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
			
			String key[]  =  {"wbfs.bin","window.x","window.y"};
			for(int x = 0; x < key.length; x++){
				System.setProperty(key[x], props.getProperty(key[x]));	
			}	
			
//			InputStreamReader in= new InputStreamReader(is);
//			FileReader in= new FileReader(new File(file));
//			BufferedReader bf = new BufferedReader(in);
//			String key = bf.readLine();
//			while( key != null){
//				key = bf.readLine();  
//			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

}
