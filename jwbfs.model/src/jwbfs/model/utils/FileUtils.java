package jwbfs.model.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jwbfs.model.ModelStore;
import jwbfs.model.beans.GameBean;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

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
		checkAndCreateFolder(folder, false);
	}
	
	public static void checkAndCreateFolder(String folder,boolean prompt) {
		boolean confirm = true;
		if(prompt){
			confirm = false;
			confirm = MessageDialog.openConfirm(new Shell(), "Confirm", "The path don't contains needed folders. Create folder structures? \n"+
					"("+folder+")");

		}

		if (confirm) {

			File fol = new File(folder);
			if(!fol.getParentFile().exists()){
				fol.getParentFile().mkdir();
				System.out.println("Folder created: \n"+folder);
			}
			if(fol.exists() && fol.isDirectory()){
				return;
			}else{
				fol.mkdir();
				System.out.println("Folder created: \n"+folder);
				return;
			}

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

	public static String exists(String path, String coverSubFolder) {
//		if(coverSubFolder == null){		
//			path = System.getProperty("java.io.tmpdir")+File.separatorChar+coverSubFolder;
//		}
		path = validatePath(path);

		path = path+File.separatorChar+coverSubFolder;
		FileUtils.checkAndCreateFolder(path);
		
		return path;
	}

	/**
	 * Check if the path exists. If not, fallback to java temp dir
	 * @param path
	 * @return
	 */
	public static String validatePath(String path) {

		File tmp = new File(path);
		if(!tmp.exists() || tmp.equals("")){
			path = System.getProperty("java.io.tmpdir");
		}
		return path;
	}

	/**
	 * check if the path exists
	 * @param path
	 * @return
	 */
	public static boolean exists(String path) {
		if(path == null || path.equals("")){
			return false;
		}
		
		File tmp = new File(path);
		if(tmp.exists()){
			return true;
		}
		return false;
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
//			FileOutputStream out = new FileOutputStream(ModelStore.getSelectedGame().getFolderPath()+"tmp.size");

	        RandomAccessFile f = new RandomAccessFile("t", "rw");
	        f.setLength(WBFSFileConstants.SPLITSIZE_kb_Values[2]);


//			int c = 0;
//			while (c != WBFSFileConstants.SPLITSIZE_kb_Values[2]){
//				out.write(c);
//			}
//
//			if (out != null) {
//				out.close();
//			}

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
		
		new File(ModelStore.getSelectedGame().getFolderPath()+"tmp.size").delete();
		return true;
	}

	public static void updateGameTxtFile(String gameID, String gameName, String diskID) {


		try {
			List<GameBean> games = ModelStore.getGames(diskID);
			String txtFile = null;
			for (Iterator<GameBean> iterator = games.iterator(); iterator.hasNext();) {
				GameBean gameBean = (GameBean) iterator.next();
				if(gameBean.getId().trim().equals(gameID)){			
					txtFile = getTxtFile(new File(gameBean.getFilePath()));
					break;
				}		
			}

			if(!txtFile.trim().equals("")){
				File file = new File(txtFile);
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String line = reader.readLine();
				String tempLine = "";
				while(line!=null){
					tempLine = tempLine+line;
					line = reader.readLine();
				}
				
				String[] outLine= tempLine.split("=");
				outLine[1] = " "+gameName.trim();
				
				String lineNewName = outLine[0]+ "="+outLine[1];
				
				BufferedWriter writer = new BufferedWriter(new FileWriter(txtFile));
				writer.write(lineNewName);
				writer.close();
				
				System.out.println("File "+txtFile+" written: "+lineNewName);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	public static void createRootFolderStructures(String diskPath, String diskID) {
		
		String wbfsPath = diskPath+File.separatorChar+DiskContants.WBFS_GAMES_FOLDER;
		File wbfsFile = new File(wbfsPath);
		if(!diskPath.contains(DiskContants.WBFS_GAMES_FOLDER)
				&&  !wbfsFile.exists()){
	
		FileUtils.checkAndCreateFolder(wbfsPath,true);
		createCoverFolder(diskPath, diskID);
		}
		
	}


	/**
	 * create the images folder and its subfolder (2D,3D,discs)
	 * @param diskPath
	 * @return
	 */
	public static String createCoverFolder(String diskPath, String diskID) {
		
		String coverPath = diskPath+File.separatorChar
						  +CoverConstants.getFolderName(diskID);
		
		File dirImg = new File(coverPath);
		if(!dirImg.exists()){
				dirImg.mkdir();
		}
	
		createCoverSubFolders(coverPath, diskID);
	
		return coverPath;
		
	}


	/**
	 * 
	 * create the images subfolder (2D,3D,discs)
	 * @param coverPath
	 */
	public static void createCoverSubFolders(String coverPath, String diskID) {
		
		FileUtils.exists(coverPath,CoverConstants.getImage2D(diskID));
		FileUtils.exists(coverPath,CoverConstants.getImage3D(diskID));
		FileUtils.exists(coverPath,CoverConstants.getImageDisc(diskID));
		FileUtils.exists(coverPath,CoverConstants.getImageFullCover(diskID));
	}
		
}
