package jwbfs.ui.utils;

import java.io.File;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.handlers.IHandlerService;



public class Utils {

	public static String convertPathSeparator(String path) {
		path = path.replace(" ", "\\ " );
		
		return path;
		
//		String out = "";
//		for(int x = 0; x<path.length(); x++){
//
//			String ch = path.substring(x, x+1);
//			
//			if(ch.equals(" ")){
//				ch = ch.replace(' '+"", '\\'+" ");	
//			}
//			out = out+ch;
//		}
//	
//		return out;
		
		
//		StringCharacterIterator it = new StringCharacterIterator(path);
//		
//		Character temp = it.first();
//		String out = "";
//		while(!temp.equals( it.last())){
//			if(temp.equals('/')){
//				out = out + "\\/";
//			}else {
//				out = out+temp;
//			}
//			temp = it.next();
//		}
//		
//		return out;
		
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

//			URL pluginInternalURL = Activator.getDefault().getBundle().getEntry(System.getProperty("wbfs.bin"));
			
			
//			if(Activator.getDefault().isDebugging()){
//			try {
//			 path = FileLocator.toFileURL(pluginInternalURL).getPath();		
//		} catch (IOException e) {
//			e.printStackTrace();
//		} 
//			}else{
////		String path = Platform.getLocation().makeAbsolute().toString();
//
//			path = pluginInternalURL.getPath();
//			}

//			IWorkspaceRoot ws = ResourcesPlugin.getWorkspace().getRoot();

		
			System.out.println(Platform.getInstanceLocation (). getURL (). getPath ()); 
			
			path = getRoot();
			
			String os = System.getProperty("os.name").toLowerCase();
			
			if(os.contains("linux")){
				path = path + "wbfs_file/linux/wbfs_file";
			}
			if(os.contains("windows")){
				path = path + "wbfs_file/win/wbfs_file.exe";
			}
			if(os.contains("osx")){
				path = path + "wbfs_file/mac_osx/wbfs_file";
			}
			
			System.out.println(path);
			
			
//			ResourcesPlugin.getPlugin (). GetWorkspace (). GetRoot (). GetProject (
//
//			"yourprojectname"). getFolder ( "src"); 
//			IFile ifile = srcFolder.getFile ( "hibernate.cfg.xml");
//
//			String path = ifile.getLocation (). MakeAbsolute (). ToFile (). GetAbsolutePath (); 
			
		return path; 
	}
	
	public static String getTitlesTXTpath(){
		String path = "";
		
			System.out.println(Platform.getInstanceLocation (). getURL (). getPath ()); 
			
			path = getRoot();
			
			String os = System.getProperty("os.name").toLowerCase();
			
			if(os.contains("linux")){
				path = path + "wbfs_file"+File.separatorChar+"linux"+File.separatorChar+"titles.txt";
			}
			if(os.contains("windows")){
				path = path + "wbfs_file"+File.separatorChar+"win"+File.separatorChar+"titles.txt";
			}
			if(os.contains("osx")){
				path = path + "wbfs_file"+File.separatorChar+"mac_osx"+File.separatorChar+"titles.txt";
			}
			
			System.out.println(path);
			
		return path; 
	}
	
	public static File getWbfsINI(){
		String ini = getFile("wbfs.ini");
		System.out.println(ini);
		return new File(ini);
	}
	
	public static String getRoot () {
			String path = null;
			try {
			path = FileLocator.toFileURL (
			Platform.getBundle ("jwbfs.ui"). getEntry ("wbfs.ini")). getPath ();
//			path = path.substring (path.indexOf ("/") + 1, path.length ());
			
			path = path.replaceAll("wbfs.ini", "");
			
			} catch (Exception e) {
				e.printStackTrace ();
					
			}
			
			return path;
}
	
	public static String getFile (String file) {
		String path = null;
		try {
		path = FileLocator.toFileURL (
		Platform.getBundle ("jwbfs.ui"). getEntry (file)). getPath ();
//		path = path.substring (path.indexOf ("/") + 1, path.length ());
		
//		path = path.replaceAll("wbfs.ini", "");
		
		} catch (Exception e) {
			e.printStackTrace ();
				
		}
		
		return path;
}

	public static IHandlerService getHandlerService(String viewID) {
		try {
		IHandlerService handlerService = (IHandlerService) GuiUtils.getView(viewID).getSite()
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
		return ret.substring(0, ret.indexOf(".")+3);
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
