package jwbfs.model.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import jwbfs.model.Model;

public class FileUtils {

	public static String getTxtFile(File fileWbfs) {

		String fold = fileWbfs.getAbsolutePath().replace(fileWbfs.getName(), "");

		File folder = new File(fold);
		String[] list = folder.list();


		for (int i = 0; i < list.length; i++)  {
			String fileTemp = list[i];
			if(fileTemp.toLowerCase().contains(".txt") && 
					fileTemp.toLowerCase().contains(fileWbfs.getName().replace(".wbfs", "").toLowerCase())){
				System.out.println(fileTemp);
				return fold+fileTemp;
			}

		}

		return null;
	}

	public static void checkAndCreateFolder(String folder) {

		File fol = new File(folder);
		if(fol.exists() && fol.isDirectory()){
			return;
		}else{
			fol.mkdir();
			System.out.println("Folder created: \n"+folder);
			return;
		}

	}

	public static boolean coverFileExist(String coverPath) {
		File file = new File(coverPath);
		boolean exist = file.exists();
		if(exist){
			System.out.println("Cover exists:");
			System.out.println(coverPath);
		}
		return exist;
	}

	public static String exist(String path, boolean cover2d, boolean cover3d, boolean coverDisc) {
		path = exist(path);

		//		String tempDir = System.getProperty("java.io.tmpdir");

		if(cover2d /*&& path.equals(tempDir)*/){
			path = path+File.separatorChar+"2D";
			FileUtils.checkAndCreateFolder(path);
		}

		if(cover3d /*&& path.equals(tempDir)*/){
			path = path+File.separatorChar+"";
			FileUtils.checkAndCreateFolder(path);
		}

		if(coverDisc /*&& path.equals(tempDir)*/){
			path = path+File.separatorChar+"disc";
			FileUtils.checkAndCreateFolder(path);	
		}

		return path;
	}

	public static String exist(String path) {

		File tmp = new File(path);
		if(!tmp.exists() || tmp.equals("")){
			path = System.getProperty("java.io.tmpdir");
		}
		return path;
	}

	public static ArrayList<File> getDiskAvalaible() {
		String root = "";
		ArrayList<File> disks = new ArrayList<File>();
		File[] files = File.listRoots();
		for(int x = 0; x<files.length;x++){
			if(!isSystemDisk(files[x])){
				disks.add(files[x]);
			}else{
				root = files[x].getPath();
			}
		}
//		disks.addAll(Arrays.asList(File.listRoots()));

		if(disks != null && !root.equals("")){
			for(int f = 0; f<DiskContants.LINUX_MOUNT_FOLDERS.length; f++){
				File[] tmp = new File(root+File.separatorChar+DiskContants.LINUX_MOUNT_FOLDERS[f]).listFiles();
				for(int x = 0; x<tmp.length;x++){
					File testMedia = tmp[x];
					File testWBFS = new File(testMedia.getAbsolutePath()+File.separatorChar+DiskContants.WBFS_GAMES_FOLDER);

					if(testWBFS.exists()){
						disks.add(testWBFS);
					}
				}
			}

		}


		return  disks;
	}

	public static boolean isSystemDisk(File file) {
		
		return file.getAbsolutePath().equals("/")
		|| file.getAbsolutePath().toLowerCase().equals("c:");
	}

	public static void deleteFolder(String folder, boolean deleteAlsoFiles) {

		File parent = new File(folder);

		if(deleteAlsoFiles){
			File[] files = parent.listFiles();
			for(int i = 0; i<files.length; i++){
				files[i].delete();
			}
		}

		parent.delete();

	}

	public static void deleteWBFSFileAndTXT(File file) {
		boolean isISO = file.getName().toLowerCase().contains(".iso");
		String fileName = file.getName();
		String fileID = fileName.replaceAll(".wbfs", "");
		file.delete();
		
		if(!isISO){
			File parent = file.getParentFile();
			File[] childs = parent.listFiles();


			for(int x = 0; x<childs.length;x++){
				File fileChild = childs[x]; 
				
				if((fileChild.getName().contains(".txt")
						|| fileChild.getName().contains(".tmp")
						|| fileChild.getName().contains(".wbf1")
						|| fileChild.getName().contains(".wbf2")
						|| fileChild.getName().contains(".wbf3")) 
					&& fileChild.getName().contains(fileID)){
	
					System.out.println("removing also: "+fileChild.getName());
					fileChild.delete();
	
				}
			}

		}
	}

	public static boolean createTempFile() {

		try {
			FileOutputStream out = new FileOutputStream(Model.getSelectedGame().getFolderPath()+"tmp.size");


			int c = 0;
			while (c != WBFSFileConstants.SPLITSIZE_kb_Values[2]){
				out.write(c);
			}

			if (out != null) {
				out.close();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		new File(Model.getSelectedGame().getFolderPath()+"tmp.size").delete();
		return true;
	}
}
