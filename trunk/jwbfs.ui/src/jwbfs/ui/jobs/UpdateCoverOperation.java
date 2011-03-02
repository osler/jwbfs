package jwbfs.ui.jobs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import jwbfs.model.ModelStore;
import jwbfs.model.beans.CoverPaths;
import jwbfs.model.beans.DiskBean;
import jwbfs.model.beans.GameBean;
import jwbfs.model.beans.SettingsBean;
import jwbfs.model.utils.CoverConstants;
import jwbfs.model.utils.FileUtils;
import jwbfs.ui.utils.GuiUtils;
import jwbfs.ui.utils.WebUtils;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.graphics.Image;

public class UpdateCoverOperation extends Job {

	private boolean updateCover;
	private GameBean actualSelectedGame;
	
	public UpdateCoverOperation(String name, GameBean gameBean) {
		super(name);
		this.actualSelectedGame = gameBean;
	}

	@Override
	public boolean shouldSchedule() {
		
		IJobManager manager = Job.getJobManager();
		return true;
		
	}

	@Override
	public boolean shouldRun() {

		IJobManager manager = Job.getJobManager();
		if(ModelStore.getSelectedGame().getId().equals(actualSelectedGame.getId())){
			if(manager.currentJob()!=null){
				manager.currentJob().cancel();
			}
		}
		return true;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
	
		boolean offlineMode = ModelStore.getSettingsBean().getSystemSettings().isOfflineMode();

		GameBean processBean = ModelStore.getSelectedGame();
		if(processBean == null || processBean.isEmpty()){
			return Status.OK_STATUS;
		}
		DiskBean settingsBean =  ModelStore.getDisk(GuiUtils.getActiveViewID());
		CoverPaths coverPaths = settingsBean.getCoverSettings().getCoverPaths();

		monitor.beginTask("Covers", getProgress());

		updateCover = settingsBean.getCoverSettings().isUpdateCover();

		String gameId = processBean.getId();

		//IF not wii disc return and set default image
		if(gameId.contains("not a wii disc")){
			GuiUtils.setDefaultCovers();
			settingsBean.getCoverSettings().setUpdateCover(false);
			return Status.OK_STATUS;
		}


		//2D
		String folder = coverPaths.getCover2d();
		String coverPath = folder
		+File.separatorChar
		+gameId
		+".png";

		if(!processCover(offlineMode, 
				coverPath, 
				folder, 
				monitor, 
				CoverConstants.COVER_2D)){
			return Status.OK_STATUS;
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
				monitor, 
				CoverConstants.COVER_3D)){
			return Status.OK_STATUS;
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
				monitor, 
				CoverConstants.COVER_DISC)){
			return Status.OK_STATUS;
		}

		settingsBean.getCoverSettings().setUpdateCover(false);

		return Status.OK_STATUS;
	}


	
	private boolean processCover(boolean offlineMode, String coverPath, String folder, IProgressMonitor monitor, String coverCode) {

		if(FileUtils.coverFileExist(coverPath) && !updateCover){
			GuiUtils.setCover(coverPath,coverCode);		
			monitor.worked(1);
			return true;
		}

		if(offlineMode){
				GuiUtils.setDefaultCovers();
				return false;
			
		}else{
			FileUtils.checkAndCreateFolder(folder);
			downloadCover(coverPath,coverCode);
			return true;
		}
	}


	private void downloadCover(String coverPath, String coverCode) {
		
		if(coverCode.equals(CoverConstants.COVER_2D)){
			downloadCover2D(coverPath);
		}
		if(coverCode.equals(CoverConstants.COVER_3D)){
			downloadCover3D(coverPath);
		}
		if(coverCode.equals(CoverConstants.COVER_DISC)){
			downloadCoverDisc(coverPath);
		}
		
	}


	private void downloadCoverDisc(String coverPath) {
		
//		ProgressBar progressBar = ((CoverView) GuiUtils.getView(CoreConstants.VIEW_COVER_ID)).getProgressBar();
		
		DiskBean settingsBean =  ModelStore.getDisk(GuiUtils.getActiveViewID());
		
		GuiUtils.setCover(CoverConstants.NODISC,CoverConstants.COVER_DISC);
		if(settingsBean.getCoverSettings().isAutomaticCoverDownload() || updateCover)
			if(settingsBean.getCoverSettings().isCoverDiscs()){

				//delete old cover if update forced
//				if(updateCover && FileUtils.coverFileExist(coverPath)){
//					new File(coverPath).delete();
//				}
				
				processCover(CoverConstants.DISC_URL,coverPath);

				if(FileUtils.coverFileExist(coverPath)){
					//Cover Found
					GuiUtils.setCover(coverPath,CoverConstants.COVER_DISC);
				}else{
					//Not found
					GuiUtils.setCover(CoverConstants.NODISC,CoverConstants.COVER_DISC);
				}

				//progressbar
//				progressBar.setSelection(getProgress()>2?3:getProgress());

			}
	
	}

	private void downloadCover3D(String coverPath) {
		
//		ProgressBar progressBar = ((CoverView) GuiUtils.getView(CoreConstants.VIEW_COVER_ID)).getProgressBar();
		
		DiskBean settingsBean =  ModelStore.getDisk(GuiUtils.getActiveViewID());
		
		GuiUtils.setCover(CoverConstants.NOIMAGE3D,CoverConstants.COVER_3D);
		if(settingsBean.getCoverSettings().isAutomaticCoverDownload() || updateCover)
			if(settingsBean.getCoverSettings().isCover3D()){

				processCover(CoverConstants.COVER3D_URL,coverPath);	
				if(FileUtils.coverFileExist(coverPath)){
					//Cover Found
					GuiUtils.setCover(coverPath,CoverConstants.COVER_3D);
				}else{
					//Not found
					GuiUtils.setCover(CoverConstants.NOIMAGE3D,CoverConstants.COVER_3D);
				}
				//progressbar
//				progressBar.setSelection(getProgress()>2?2:getProgress());

			}

	}

	private void downloadCover2D(String coverPath) {
		
//		ProgressBar progressBar = ((CoverView) GuiUtils.getView(CoreConstants.VIEW_COVER_ID)).getProgressBar();
		
		DiskBean settingsBean =  ModelStore.getDisk(GuiUtils.getActiveViewID());
		
		GuiUtils.setCover(CoverConstants.NOIMAGE,CoverConstants.COVER_2D);
		if(settingsBean.getCoverSettings().isAutomaticCoverDownload() || updateCover){

			if(updateCover){
				System.out.println("Updating Cover");
			}else{
				if(settingsBean.getCoverSettings().isAutomaticCoverDownload()){
					System.out.println("Automatic Cover Download");
				}	
			}
			//search cover
			processCover(CoverConstants.COVER_URL,coverPath);

			if(FileUtils.coverFileExist(coverPath)){
				//Cover Found
				GuiUtils.setCover(coverPath,CoverConstants.COVER_2D);
			}else{
				//Not found
				GuiUtils.setCover(CoverConstants.NOIMAGE,CoverConstants.COVER_2D);
			}
			//progressbar
//			progressBar.setSelection(1);
		}
	}


	private int getProgress() {

		DiskBean settingsBean =  ModelStore.getDisk(GuiUtils.getActiveViewID());

		int tot = 1;

		if(settingsBean.getCoverSettings().isCover3D()){
			tot++;
		}
		if(settingsBean.getCoverSettings().isCoverDiscs()){
			tot++;
		}

		return tot;
	}

	private void processCover(String url,String coverPath) {

		GameBean processBean = ModelStore.getSelectedGame();
		SettingsBean settingsBean =  ModelStore.getSettingsBean();
		
		try {			
			String region = settingsBean.getRegion();
			String gameId = processBean.getId();

			InputStream in = obtainInputStream(url,region,gameId,-1);
			if(in == null){
				//PORCATA
				//if disc, try custom, else return
				if(url.equals(CoverConstants.DISC_URL)){
					in = obtainInputStream(CoverConstants.DISCCUSTOM_URL,
											region,gameId,-1);
					if(in == null){
						return;
					}
				}else{
					return;
				}

			}
			FileOutputStream out = new FileOutputStream(coverPath);

			int c;
			while ((c = in.read()) != -1){
				out.write(c);
			}
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
			System.out.println("Cover downloaded!");

		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}


	private InputStream obtainInputStream(String url,String region, String gameId, int cycle) {
		String[] alternate = {"EN","US","JA"};
		InputStream in;
		try {

			String address = url
			+region
			+"/"+gameId+".png";
			
			address = WebUtils.formatUrlAddress(address);
			
			System.out.println("Checking:\n"+address);
			if(!WebUtils.urlExists(address)){
				throw new MalformedURLException();
			}

			URL u = new URL(address);
			in = u.openStream();
			System.out.println("Url cover correct");


		} catch (MalformedURLException e) {
			cycle++;
			if(cycle >= alternate.length){
				return null;
			}
			System.out.println("Region cover not found trying "+alternate[cycle]);
			in = obtainInputStream(url,alternate[cycle],gameId,cycle);
			return in;

		} catch (IOException e) {
			cycle++;
			if(cycle >= alternate.length){
				return null;
			}
			System.out.println("Region cover not found trying "+alternate[cycle]);
			in = obtainInputStream(url,alternate[cycle],gameId,cycle);		
			return in;
		}


		return in;
	}

	//TODO
	private void downloadCoverFull(String coverPath) {
		
//		ProgressBar progressBar = ((CoverView) GuiUtils.getView(CoreConstants.VIEW_COVER_ID)).getProgressBar();
		
		DiskBean settingsBean =  ModelStore.getDisk(GuiUtils.getActiveViewID());
		
		GuiUtils.setCover(CoverConstants.NOIMAGEFULL,CoverConstants.COVER_FULLBOX);
		if(settingsBean.getCoverSettings().isAutomaticCoverDownload() || !updateCover)
			if(settingsBean.getCoverSettings().isCover3D()){
	
				processCover(CoverConstants.COVERFULL_URL,coverPath);	
				if(FileUtils.coverFileExist(coverPath)){
					//Cover Found
					GuiUtils.setCover(coverPath,CoverConstants.COVER_FULLBOX);
				}else{
					//Not found
					GuiUtils.setCover(CoverConstants.NOIMAGEFULL,CoverConstants.COVER_FULLBOX);
				}
				//progressbar
//				progressBar.setSelection(getProgress()>2?2:getProgress());
	
			}
	
	}


	public static Image imgAddress(String indirizzo){

		Image immagine = null;

		if(indirizzo.startsWith("http://")) {
			URL url;
			try {
				url = new URL(WebUtils.formatUrlAddress(indirizzo));
				InputStream is = url.openStream();
				immagine = new Image(null,is);

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();	
				immagine = new Image(GuiUtils.getDisplay(),CoverConstants.NOIMAGE);			
			}



		}else{
			immagine = new Image(null,indirizzo);
		}
		return immagine;
	}

}
