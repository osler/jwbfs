
package jwbfs.model.utils;

import java.io.File;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.IHandlerService;



public class PlatformUtils {

	public static String convertPath(String path) {
		if (isWindows()) {
			System.out.print(path);
			String[] tmp = path.split("\\\\");
			path="";
			for (int i = 0; i < tmp.length; i++) {
				String separator = ""+File.separatorChar+File.separatorChar;
				if(i==tmp.length-1){
					separator = "";
				}
				path = (path + tmp[i]+separator).trim();
			}
		}

		return path;
	}
	
	
	public static String convertPathSeparator(String path) {
		path = path.replace(" ", "\\ " );

		return path;
	}

	public static int getPercentual(String line) {

		int per = 0;

		try {
			if(line.contains("%")){
				String temp = line.trim().substring(0,line.indexOf("%")-1).trim();
				temp = line.subSequence(0, line.indexOf(".")).toString();

				per = Integer.parseInt(temp.trim());
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return 0;
		}

		return per;
	}

	public static String getWBFSpath(){
		String path = "";

		System.out.println(Platform.getInstanceLocation (). getURL (). getPath ()); 

		path = getRoot();

		if(isLinux()){
			path = path + "wbfs_file/linux/wbfs_file";
		}
		if(isWindows()){
			path = path + "wbfs_file/win/wbfs_file.exe";
		}
		if(isOSX()){
			path = path + "wbfs_file/mac_osx/wbfs_file";
		}

		System.out.println(path);

		return path; 
	}

	public static boolean isLinux() {
		String os = System.getProperty("os.name").toLowerCase();
		return os.contains("linux");
	}
	public static boolean isWindows() {
		String os = System.getProperty("os.name").toLowerCase();
		return os.contains("windows");
	}
	public static boolean isOSX() {
		String os = System.getProperty("os.name").toLowerCase();
		return os.contains("osx");
	}

	public static String getTitlesTXTpath(){
		String path = "";

		System.out.println(Platform.getInstanceLocation (). getURL (). getPath ()); 

		path = getRoot();

		if(isLinux()){
			path = path + "wbfs_file"+File.separatorChar+"linux"+File.separatorChar+"titles.txt";
		}
		if(isWindows()){
			path = path + "wbfs_file"+File.separatorChar+"win"+File.separatorChar+"titles.txt";
		}
		if(isOSX()){
			path = path + "wbfs_file"+File.separatorChar+"mac_osx"+File.separatorChar+"titles.txt";
		}

		System.out.println(path);

		return path; 
	}

	public static String getFile (String file) {
		String path = null;
		try {
			path = FileLocator.toFileURL (
					Platform.getBundle (CoreConstants.BUNDLE_CORE). getEntry (file)). getPath ();

		} catch (Exception e) {
			e.printStackTrace ();

		}

		return path;
	}

	public static File getWbfsINI(){
		String ini = getFile(CoreConstants.wbfsINI);
		System.out.println(ini);
		return new File(ini);
	}

	public static String getRoot () {
		String path = null;
		try {
			path = getFile(CoreConstants.wbfsINI);
			path = path.replaceAll(CoreConstants.wbfsINI, "");

		} catch (Exception e) {
			e.printStackTrace ();

		}

		return path;
	}


	public static IHandlerService getHandlerService(String viewID) {
		try {
			IHandlerService handlerService = (IHandlerService) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(viewID).getSite()
			.getService(IHandlerService.class);

			return handlerService;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public static IHandlerService getHandlerService() {
		try {
			IHandlerService handlerService = (IHandlerService) 
											PlatformUI.getWorkbench()
											.getService(IHandlerService.class);

			return handlerService;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String getGB(long bytes) {
		double gb = Double.valueOf(bytes)/1073741824;	
		String ret = String.valueOf(gb);
		String size = ret.substring(0,ret.indexOf("."));
		String sizeDecimal = ret.substring(ret.indexOf("."),ret.length());
		sizeDecimal = sizeDecimal.length()>2?sizeDecimal.substring(0,2):sizeDecimal;
		return size+sizeDecimal;
	}

	public static String getMB(long bytes) {
		long mb = bytes/1048576;
		return String.valueOf(mb);
	}

	public static String getKB(long bytes) {
		long kb = bytes/1024;
		return String.valueOf(kb);
	}
}
