package jwbfs.ui.jobs;

import java.io.File;

import org.eclipse.core.runtime.jobs.Job;

import jwbfs.model.ModelStore;
import jwbfs.model.beans.CoverPaths;
import jwbfs.model.beans.DiskBean;
import jwbfs.model.utils.CoverConstants;
import jwbfs.model.utils.FileUtils;
import jwbfs.ui.utils.GuiUtils;

public class SetCoverOperation implements Runnable {

	public SetCoverOperation() {

	}


	@Override
	public void run() {
		
		boolean offlineMode = ModelStore.getSettingsBean().getSystemSettings().isOfflineMode();
		DiskBean settingsBean =  ModelStore.getDisk(GuiUtils.getActiveViewID());
		CoverPaths coverPaths = settingsBean.getCoverSettings().getCoverPaths();
		String gameId = ModelStore.getSelectedGame().getId();
		
		//2D
		String folder = coverPaths.getCover2d();
		String coverPath = folder
		+File.separatorChar
		+gameId
		+".png";

		if(!processCover(offlineMode, 
				coverPath, 
				folder, 
				 
				CoverConstants.COVER_2D)){
			return;
		}


		//3D
		folder = coverPaths.getCover3d()
		+File.separatorChar;

		FileUtils.checkAndCreateFolder(folder);

		coverPath = folder
		+File.separatorChar
		+gameId
		+".png";

		if(!processCover(offlineMode, 
				coverPath, 
				folder, 
				 
				CoverConstants.COVER_3D)){
			return;
		}

		//DISC
		folder = coverPaths.getCoverDisc()
		+File.separatorChar;

		FileUtils.checkAndCreateFolder(folder);

		coverPath = folder
		+File.separatorChar
		+gameId
		+".png";

		if(!processCover(offlineMode, 
				coverPath, 
				folder, 
				 
				CoverConstants.COVER_DISC)){
			return;
		}

	}


	
	private boolean processCover(boolean offlineMode, String coverPath, String folder, String coverCode) {

		if(FileUtils.coverFileExist(coverPath)){
			GuiUtils.setCover(coverPath,coverCode);		
			return true;
		}

		if(offlineMode){
				GuiUtils.setDefaultCovers();
				return false;
			
		}else{
			FileUtils.checkAndCreateFolder(folder);
			Job job = new UpdateCoverOperation("cover:"+ModelStore.getSelectedGame().getId(),
					ModelStore.getSelectedGame());
			job.setUser(true);
			job.schedule();
			return true;
		}
	}

}
