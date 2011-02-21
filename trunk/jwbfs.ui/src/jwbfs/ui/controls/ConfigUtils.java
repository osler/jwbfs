package jwbfs.ui.controls;

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
import jwbfs.model.utils.CoreConstants;
import jwbfs.model.utils.PlatformUtils;

import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

public class ConfigUtils {
	
	
	public static void initConfigFile(){
		
		
		String nomeFileProps = CoreConstants.wbfsINI;

		try {

			Properties props = new Properties();
			
			Bundle bundle = Platform.getBundle(CoreConstants.BUNDLE_CORE);		
			InputStream is = bundle.getEntry("/" + nomeFileProps).openStream();
			props.load(is);


			String[] keys = getFileProperty(PlatformUtils.getWbfsINI());	

			
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
		
		if(line.contains("wbfs.disk.path1")){
			return subLine +  PlatformUtils.convertPath(ModelStore.getDiskPath(CoreConstants.VIEW_DISK_1_ID));
		}
		if(line.contains("wbfs.disk.path2")){
			return subLine +  PlatformUtils.convertPath(ModelStore.getDiskPath(CoreConstants.VIEW_DISK_2_ID));
		}
		if(line.contains("wbfs.disk.path3")){
			return subLine +  PlatformUtils.convertPath(ModelStore.getDiskPath(CoreConstants.VIEW_DISK_3_ID));
		}
		if(line.contains("wbfs.disk.path4")){
			return subLine +  PlatformUtils.convertPath(ModelStore.getDiskPath(CoreConstants.VIEW_DISK_4_ID));
		}
		if(line.contains("wbfs.disk.path5")){
			return subLine +  PlatformUtils.convertPath(ModelStore.getDiskPath(CoreConstants.VIEW_DISK_5_ID));
		}
		if(line.contains("wbfs.disk.path6")){
			return subLine +  PlatformUtils.convertPath(ModelStore.getDiskPath(CoreConstants.VIEW_DISK_6_ID));
		}
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
		if(line.contains("cover.path.type")){
			String type = decodeCoverType();
		return subLine + type;
		}

//		if(line.contains("cover.path.2d")){
//			return subLine + bean.getCoverSettings().getCoverPaths().getCover2d();
//		}
//		if(line.contains("cover.path.3d")){
//			return subLine + bean.getCoverSettings().getCoverPaths().getCover3d();
//		}
//		if(line.contains("cover.path.disc")){
//			return subLine + bean.getCoverSettings().getCoverPaths().getCoverDisc();
//		}
//		if(line.contains("cover.path.full")){
//			return subLine + bean.getCoverSettings().getCoverPaths().getCoverFull();
//		}

		if(line.contains("settings.split.size")){
			return subLine +  bean.getSplitSize();
		}
		if(line.contains("settings.split.partitions")){
			return subLine + bean.getCopyPartitions();
		}
		
		return line;
	}

	private static String decodeCoverType() {
		String type = null;
		CoverSettings bean = ModelStore.getSettingsBean().getCoverSettings();
		if(bean.isCoverTypeUSBLoaderCFG()){
			type = CoverPaths.CFG;
		}else if(bean.isCoverTypeUSBLoaderGX()){
				type = CoverPaths.GX;			
		}else	if(bean.isCoverTypeUSBLoaderWIIFLOW()){
			type = CoverPaths.WIIFLOW;
		}
		return type;
	}

}
