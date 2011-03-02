package jwbfs.model.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import jwbfs.model.ModelStore;
import jwbfs.model.beans.CoverPaths;
import jwbfs.model.beans.CoverSettings;
import jwbfs.model.beans.SettingsBean;

import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

public class ConfigUtils {
	
	public static void initConfigFile(){
		initConfigFile("configs"+File.separatorChar+CoreConstants.wbfsINI);
		initConfigFile("configs"+File.separatorChar+"disk1.ini");
		initConfigFile("configs"+File.separatorChar+"disk2.ini");
		initConfigFile("configs"+File.separatorChar+"disk3.ini");
		initConfigFile("configs"+File.separatorChar+"disk4.ini");
		initConfigFile("configs"+File.separatorChar+"disk5.ini");
		initConfigFile("configs"+File.separatorChar+"disk6.ini");
		
	}
	
	public static void initConfigFile(String nomeFileProps){

		try {

			Properties props = new Properties();
			
			Bundle bundle = Platform.getBundle(CoreConstants.BUNDLE_CORE);		
			InputStream is = bundle.getEntry(nomeFileProps).openStream();
			props.load(is);


			String[] keys = getFileProperty(PlatformUtils.getFileINI(nomeFileProps));	

			
//			String key[]  =  {"wbfs.bin","window.x","window.y"};
			for(int x = 0; x < keys.length; x++){
				if(keys[x] != null){
					String property = props.getProperty(keys[x]);
					if(property == null){
						property = "";
					}
					System.setProperty(keys[x], property);
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

		ArrayList<String> propsLines = new ArrayList<String>();
		//Good lines
		for(int x = 0; x<numLines; x++){			
			String line = br.readLine();
			if(line.indexOf("=")>-1){
				propsLines.add((line.substring(0,line.indexOf("="))).trim());
			}
			
		}
		
		for(int x = 0; x<propsLines.size(); x++){			
			String line = propsLines.get(x);			
			keys[x] = line.trim();	
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

			File configFile = PlatformUtils.getWbfsINI();

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
		SettingsBean bean = ModelStore.getSettingsBean();
		
		String subLine = (line.substring(0,line.indexOf("="))).trim() + " = ";
	
		if(line.contains("wbfs.disks")){
			return subLine +  ModelStore.getNumDisk();
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

		if(line.contains("settings.split.size")){
			return subLine +  bean.getSplitSize();
		}
		if(line.contains("settings.split.partitions")){
			return subLine + bean.getCopyPartitions();
		}
		
		return line;
	}

	private static String decodeCoverType(String diskID) {
		String type = "";
		if(ModelStore.getDiskPath(CoreConstants.VIEW_DISK_1_ID).trim().equals("")){
			return type;
		}
		CoverSettings bean = ModelStore.getDisk(diskID).getCoverSettings();
		if(bean.isCoverTypeUSBLoaderCFG()){
			type = CoverPaths.CFG;
		}else if(bean.isCoverTypeUSBLoaderGX()){
				type = CoverPaths.GX;			
		}else	if(bean.isCoverTypeUSBLoaderWIIFLOW()){
			type = CoverPaths.WIIFLOW;
		}
		return type;
	}

	public static void saveDiskConfigFile(String diskPath) {
			try {
	
				File configFile = PlatformUtils.getFileINI(diskPath);
	
				int numLines = getNumLine(configFile);
	
				FileReader in = new FileReader(configFile);
	
				BufferedReader br = new BufferedReader(in);
	
				String outFile = "";
	
				for(int x = 0; x<numLines; x++){
					String line = br.readLine();
					if(line != null){
						outFile = outFile + mapDiskConfig(line,diskPath)+"\n";
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

	private static String mapDiskConfig(String line, String diskINIPath) {
		String diskID = Decode.decodeViewNameFromDiskINI(diskINIPath);
		
		CoverSettings bean = ModelStore.getDisk(diskID).getCoverSettings();
		
		String subLine = (line.substring(0,line.indexOf("="))).trim() + " = ";
		
		if(line.contains("wbfs.disk.path")){
			return subLine +  PlatformUtils.convertPath(ModelStore.getDiskPath(diskID));
		}

		if(line.contains("cover.path.type")){
			String type = decodeCoverType(diskID);
		return subLine + type;
		}
		
		if(line.contains("cover.download.auto")){
			return subLine + bean.isAutomaticCoverDownload();
		}
		if(line.contains("cover.download.3d")){
			return subLine + bean.isCover3D();
		}
		if(line.contains("cover.download.disk")){
			return subLine + bean.isCoverDiscs();
		}
		if(line.contains("cover.download.full")){
			return subLine + bean.isCoverFullEnabled();
		}
	
		return line;
	}

	public static String getProperty(String key, String fileProps){
	String property = "";
		try {
			Properties props = new Properties();
			Bundle bundle = Platform.getBundle(CoreConstants.BUNDLE_CORE);		
			InputStream is = bundle.getEntry(fileProps).openStream();
			props.load(is);

			property = props.getProperty(key);
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return property;
	}
}
