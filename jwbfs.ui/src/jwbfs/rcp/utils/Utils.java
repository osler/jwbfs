package jwbfs.rcp.utils;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;



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
			
			path = getRoot("jwbfs.ui");
			
			String os = System.getProperty("os.name").toLowerCase();
			
			if(os.contains("linux")){
				path = path + "linux/wbfs_file";
			}
			if(os.contains("windows")){
				path = path + "win/wbfs_file.exe";
			}
			if(os.contains("osx")){
				path = path + "mac_osx/wbfs_file";
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
	
	public static String getRoot (String pluginID) {
			String path = null;
			try {
			path = FileLocator.toFileURL (
			Platform.getBundle (pluginID). getEntry ("wbfs_file")). getPath ();
//			path = path.substring (path.indexOf ("/") + 1, path.length ());
			} catch (Exception e) {
				e.printStackTrace ();
					
			}
			
			return path;
}

}
