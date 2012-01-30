package jwbfs.ui.jobs;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import jwbfs.model.ModelStore;
import jwbfs.model.beans.CopyBean;
import jwbfs.model.beans.GameBean;
import jwbfs.model.utils.CoreConstants;
import jwbfs.model.utils.DiskUtils;
import jwbfs.model.utils.PlatformUtils;
import jwbfs.ui.utils.GuiUtils;
import jwbfs.ui.views.dialogs.ConfirmOverwriteDialog;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.dialogs.IDialogConstants;

public class JobImportWBFSGame extends JwbfsJob {

	private ArrayList<GameBean> skipped;
	private String diskTO;
	
	public JobImportWBFSGame(String name, String diskTO) {
		super(name);
		this.diskTO = diskTO;
	}

	@Override
	protected IStatus runJwbfs(IProgressMonitor monitor) throws Exception{
		//diskTO don't have a path
		if(!DiskUtils.pathExists(diskTO)){
			GuiUtils.showError("Please select a valid path for destination disk.", true);
			return Status.CANCEL_STATUS;
		}

		List<GameBean> gamesOnDestinationDisk = ModelStore.getGames(diskTO);

		CopyBean copyBean = ModelStore.getCopyBean();
		ArrayList<GameBean> gamesTo = copyBean.getGamesToCopy();

		//				diskFrom = copyBean.getDiskIdFrom().trim();
		//				if(diskFrom.equals(diskTO)){
		//					GuiUtils.showError("Cannot paste into same disk", true);
		//					return Status.CANCEL_STATUS;
		//				}

		int tot = gamesTo.size();
		monitor.beginTask("processing", tot);

		skipped = new ArrayList<GameBean>();
		boolean ok = processGames(gamesTo,gamesOnDestinationDisk,diskTO,monitor);

		if(!ok){
			return Status.CANCEL_STATUS;
		}

		if(skipped.size()>0){
			ok = processGames(gamesTo,gamesOnDestinationDisk,diskTO,monitor);
			if(!ok){
				return Status.CANCEL_STATUS;
			}
		}

		monitor.done();

		GuiUtils.executeCommand(diskTO,CoreConstants.COMMAND_REFRESH_ALL_DISK_ID,null,true);	

		return Status.OK_STATUS;
	}

	private boolean processGames(ArrayList<GameBean> gamesTo,
			List<GameBean> gamesOnDestinationDisk, 
			final String diskTO, 
			IProgressMonitor monitor) throws Exception {


		boolean yesAll = false;
		boolean isSkipped = skipped.size()>0;

		for(int i=0;i<gamesTo.size();i++){

			final GameBean g = gamesTo.get(i);

			if(gamesOnDestinationDisk.contains(g) && !isSkipped){
				skipped.add(g);
				continue;
			}


			if(isSkipped && !yesAll){
				try{
					String message = "Game is already present on destination disk:"
						+"\n\n"
						+g.toString()
						+"\n\n"
						+"Do you want to overwrite it?";
					int ret  = new ConfirmOverwriteDialog(message).open();

					switch (ret) {
					case IDialogConstants.NO_ID: continue; //go on cycling
					case IDialogConstants.NO_TO_ALL_ID: return true; //exit now
					case IDialogConstants.YES_ID: break;
					case IDialogConstants.YES_TO_ALL_ID:  yesAll = true; break;

					}
				}catch (Exception e) {
					//TODO
					System.out.println("TODO thread access for dialog overwrite file yes/no");
					return true; //same as exit now to fix thread access
				}
			}

			String pathTo = ModelStore.getDisk(diskTO).getDiskPath().trim();
			if(pathTo.equals("")){
				GuiUtils.showError("Select a valid path on destination disk.");
				return false;
			}


			processGameSubtask(monitor, i,g,gamesTo,gamesOnDestinationDisk,diskTO,yesAll,isSkipped);

		}

		if(isSkipped){
			skipped = new ArrayList<GameBean>();
		}

		return true;
	}

	private boolean processGameSubtask(IProgressMonitor mainMonitor,
			int index, GameBean g, 
			List<GameBean> gamesTo, 
			List<GameBean> gamesOnDestinationDisk, String diskTO,
			boolean yesAll, boolean isSkipped ) throws Exception {



		String pathTo = PlatformUtils.convertPath(ModelStore.getDisk(diskTO).getDiskPath().trim());
		if(pathTo.equals("")){
			GuiUtils.showError("Select a valid path on destination disk.");
			return false;
		}

		String folderName = getGameFolderName(g);
		final File newFolder = new File(pathTo+File.separatorChar+folderName);

		//make folder
		if(!newFolder.exists()){
			newFolder.mkdir();
		}

		//true only when a copy is in progress and user press cancel
		boolean deleteNewFiles =false;
		//parent folder
		String gameFolder = new File(g.getFilePath()).getParent();
		File[] files = new File(gameFolder).listFiles(new FileFilter() {
			@Override
			public boolean accept(File arg0) {
				boolean isWbfs = arg0.getName().toLowerCase().endsWith(".wbfs".toLowerCase());
				boolean isWbfsSplit = arg0.getName().toLowerCase().endsWith(".wbf1".toLowerCase())
				|| arg0.getName().toLowerCase().endsWith(".wbf2".toLowerCase());
				boolean isTxt = arg0.getName().toLowerCase().endsWith(".txt".toLowerCase());
				return isWbfs || isTxt || isWbfsSplit;
			}
		});

		SubProgressMonitor monitor = new SubProgressMonitor(mainMonitor, 1);
		//first cicle to get info on how many worked int are needed by the monitor
		int totWorked = 0;
		for (int j = 0; j < files.length; j++) {
			File oldFile = files[j];
			totWorked = totWorked + Integer.parseInt(PlatformUtils.getMB(oldFile.length()));
		}

		//set sub monitor total progress and name
		monitor.beginTask("processing: " +g.toString(), totWorked);

		//cicle to copy wbfs and txt files
		for (int j = 0; j < files.length; j++) {
			//original file
			File oldFile = files[j];
			//new file
			String newFileName = newFolder.getAbsolutePath()+File.separatorChar+oldFile.getName();
			File newFile = new File(newFileName);

			//print debug
			System.out.println(oldFile.getAbsolutePath());
			System.out.println(newFile.getAbsolutePath());

			//set monitor infos
			monitor.subTask(index+1 + " of " +gamesTo.size()+": "+g.toString());

			//save file
			IStatus st = saveNewFile(newFile, oldFile.getAbsolutePath(),monitor);
			if(st.equals(Status.CANCEL_STATUS)){
				deleteNewFiles = true;
				break;
			}
		}

		//if status cancel, then remove partial files
		if(deleteNewFiles){
			File[] newFiles = newFolder.listFiles();
			for (int j = 0; j < newFiles.length; j++) {
				newFiles[j].delete(); 
			}
			newFolder.delete();
			return false;
		}

		monitor.done();
		return true;
	}

	/**
	 * The folder name of the game we want to copy 
	 * @return
	 */
	private String getGameFolderName(GameBean g) {
		String name = g.getTitle()+" ["+g.getId()+"]";
		//String pathFrom = new File(PlatformUtils.convertPath(g.getFilePath())).getParent();
		//File folderName = new File(pathFrom);
		//String name = folderName.getName;
		return name;
	}

	private IStatus saveNewFile(File newFile, String originalFile, IProgressMonitor monitor) throws Exception {

		try {
			File f = new  File(originalFile);

			FileInputStream in = new FileInputStream( f );
			FileOutputStream out = new FileOutputStream(newFile);

			long t0 = System.currentTimeMillis();

			byte[] b = new byte[8192];
			int n, tot = 0;

			while(( n = in.read( b )) > 0 ) {
				out.write( b, 0, n );
				tot += n;

				if(tot % 1048576 == 0){
					monitor.worked(1);	
					System.out.println("progress "+tot/1048576);
				}

				if (monitor.isCanceled()){
					newFile.delete();
					return Status.CANCEL_STATUS;
				}
			}

			long t = System.currentTimeMillis() - t0;

			System.out.println();
			System.out.println( tot + " bytes transfered in " + ( t / 1000 ) + " seconds at " + (( tot / 1000 ) / Math.max( 1, ( t / 1000 ))) + "Kbytes/sec" );

			in.close();
			out.close();

		} catch (MalformedURLException e) {
			throw e;
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		}

		return Status.OK_STATUS;
	}


}
