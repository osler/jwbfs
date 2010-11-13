package jwbfs.ui.controls;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import jwbfs.model.Model;
import jwbfs.model.beans.SettingsBean;
import jwbfs.model.utils.Constants;
import jwbfs.ui.utils.CoreConstants;
import jwbfs.ui.utils.Utils;

import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

public class Exec {
	
	
	public static void initConfigFile(){
		
		
		String nomeFileProps = CoreConstants.wbfsINI;

		try {

			Properties props = new Properties();
			
			Bundle bundle = Platform.getBundle(CoreConstants.bundleName_core);		
			InputStream is = bundle.getEntry("/" + nomeFileProps).openStream();
			props.load(is);


			String[] keys = getFileProperty(Utils.getWbfsINI());	

			
//			String key[]  =  {"wbfs.bin","window.x","window.y"};
			for(int x = 0; x < keys.length; x++){
				if(keys[x] != null){
					System.setProperty(keys[x], props.getProperty(keys[x]));
				}
			}	

			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String[] getFileProperty(File file) {
		String[] keys = null;
		try {
		int numLines = getNumLine(file);
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
		try {

			File configFile = Utils.getWbfsINI();

			int numLines = getNumLine(configFile);

			FileReader in = new FileReader(configFile);

			BufferedReader br = new BufferedReader(in);

			String outFile = "";

			for(int x = 0; x<numLines; x++){
				String line = br.readLine();
				if(line != null){
					outFile = outFile + mapConfig(line)+"\n";
				}
//				outFile = outFile + (line.substring(0,line.indexOf("="))).trim();  
			}

			FileWriter out;
			out = new FileWriter(configFile);
			BufferedWriter bw = new BufferedWriter(out);

			bw.write(outFile);
			bw.flush();
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private static String mapConfig(String line) {
		SettingsBean bean = Model.getSettingsBean();
		
		String subLine = (line.substring(0,line.indexOf("="))).trim() + " = ";
		
		if(line.contains("wbfs.disk.path")){
			return subLine +  bean.getDiskPath();
		}
		if(line.contains("wbfs.txt.layout")){
			return subLine + bean.getTxtLayout();
		}
		if(line.contains("wbfs.region")){
			return subLine + bean.getRegion();
		}
		if(line.contains("cover.region")){
			return subLine + bean.getRegion();
		}
		if(line.contains("cover.path.2d")){
			return subLine + bean.getCoverPath2d();
		}
		if(line.contains("cover.path.3d")){
			return subLine + bean.getCoverPath3d();
		}
		if(line.contains("cover.path.disc")){
			return subLine + bean.getCoverPathDisc();
		}

		if(line.contains("settings.split.size")){
			return subLine +  bean.getSplitSize();
		}
		if(line.contains("settings.split.partitions")){
			return subLine + bean.getCopyPartitions();
		}
		
		return line;
	}

}
