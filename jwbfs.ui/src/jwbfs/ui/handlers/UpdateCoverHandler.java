package jwbfs.ui.handlers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import jwbfs.model.Model;
import jwbfs.model.beans.GameBean;
import jwbfs.model.beans.SettingsBean;
import jwbfs.model.utils.FileUtils;
import jwbfs.ui.utils.CoverConstants;
import jwbfs.ui.utils.GuiUtils;
import jwbfs.ui.views.CoverView;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.graphics.Image;

public class UpdateCoverHandler extends AbstractHandler {
	private GameBean processBean;
	private SettingsBean settingsBean;
	boolean updateCover;
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		processBean = Model.getSelectedGame();
		settingsBean =  Model.getSettingsBean();
		

		processBean = (GameBean) Model.getSelectedGame();			
		executeForBean();


		return null;
	}

	private void executeForBean() {

		((CoverView) GuiUtils.getView(CoverView.ID)).getProgressBar().setMaximum(getProgress());
		((CoverView) GuiUtils.getView(CoverView.ID)).getProgressBar().setSelection(0);

		updateCover = settingsBean.isUpdateCover();
		
		String gameId = processBean.getId();
		String folder = settingsBean.getCoverPath2d();
		
		String coverPath = folder
		+File.separatorChar
		+gameId
		+".png";

		//IF not wii disc return and set default image
		if(gameId.contains("not a wii disc")){
			GuiUtils.setDefaultCovers();
			settingsBean.setUpdateCover(false);
			return;
		}

		//COVER
		if(FileUtils.coverFileExist(coverPath) && !updateCover){
			GuiUtils.setCover(coverPath);		
			//progressbar
			((CoverView) GuiUtils.getView(CoverView.ID)).getProgressBar().setSelection(1);
		}else{
			FileUtils.checkAndCreateFolder(folder);
			processCover(coverPath);
		}

		//3D
		folder = settingsBean.getCoverPath3d()
		+File.separatorChar;

		FileUtils.checkAndCreateFolder(folder);

		coverPath = folder
		+File.separatorChar
		+gameId
		+".png";
		
		if(FileUtils.coverFileExist(coverPath) && !updateCover){
			GuiUtils.setCover3d(coverPath);		
			//progressbar
			((CoverView) GuiUtils.getView(CoverView.ID)).getProgressBar().setSelection(getProgress()>2?2:getProgress());
		}else{
			processCover3D(coverPath);
		}
		
		//DISC
		folder = settingsBean.getCoverPathDisc()
		+File.separatorChar;

		FileUtils.checkAndCreateFolder(folder);

		coverPath = folder
		+File.separatorChar
		+gameId
		+".png";
		
		if(FileUtils.coverFileExist(coverPath) && !updateCover){
			GuiUtils.setCoverDisc(coverPath);	
			//progressbar
			((CoverView) GuiUtils.getView(CoverView.ID)).getProgressBar().setSelection(getProgress()>2?3:getProgress());	
		}else{
			processCoverDisc(coverPath);
		}
		
		((CoverView) GuiUtils.getView(CoverView.ID)).getProgressBar().setSelection(0);
		settingsBean.setUpdateCover(false);
	
	}
		
	private void processCoverDisc(String coverPath) {
		GuiUtils.setCoverDisc(CoverConstants.NODISC);
		if(settingsBean.isAutomaticCoverDownload() || !updateCover)
			if(settingsBean.isCoverDiscs()){

				//delete old cover if update forced
//				if(updateCover && FileUtils.coverFileExist(coverPath)){
//					new File(coverPath).delete();
//				}
				
				downloadCover(CoverConstants.DISC_URL,coverPath);

				if(FileUtils.coverFileExist(coverPath)){
					//Cover Found
					GuiUtils.setCoverDisc(coverPath);
				}else{
					//Not found
					GuiUtils.setCoverDisc(CoverConstants.NODISC);
				}

				//progressbar
				((CoverView) GuiUtils.getView(CoverView.ID)).getProgressBar().setSelection(getProgress()>2?3:getProgress());

			}
	
	}

	private void processCover3D(String coverPath) {
		GuiUtils.setCover3d(CoverConstants.NOIMAGE3D);
		if(settingsBean.isAutomaticCoverDownload() || !updateCover)
			if(settingsBean.isCover3D()){

				downloadCover(CoverConstants.COVER3D_URL,coverPath);	
				if(FileUtils.coverFileExist(coverPath)){
					//Cover Found
					GuiUtils.setCover3d(coverPath);
				}else{
					//Not found
					GuiUtils.setCover3d(CoverConstants.NOIMAGE3D);
				}
				//progressbar
				((CoverView) GuiUtils.getView(CoverView.ID)).getProgressBar().setSelection(getProgress()>2?2:getProgress());

			}

	}

	private void processCover(String coverPath) {
		GuiUtils.setCover(CoverConstants.NOIMAGE);
		if(settingsBean.isAutomaticCoverDownload() || updateCover){

			if(updateCover){
				System.out.println("Updating Cover");
			}else{
				if(settingsBean.isAutomaticCoverDownload()){
					System.out.println("Automatic Cover Download");
				}	
			}
			//search cover
			downloadCover(CoverConstants.COVER_URL,coverPath);

			if(FileUtils.coverFileExist(coverPath)){
				//Cover Found
				GuiUtils.setCover(coverPath);
			}else{
				//Not found
				GuiUtils.setCover(CoverConstants.NOIMAGE);
			}
			//progressbar
			((CoverView) GuiUtils.getView(CoverView.ID)).getProgressBar().setSelection(1);

		}
	}


	private int getProgress() {

		int tot = 1;

		if(settingsBean.isCover3D()){
			tot++;
		}
		if(settingsBean.isCoverDiscs()){
			tot++;
		}

		return tot;
	}

	private void downloadCover(String url,String coverPath) {
		try {			
			String region = settingsBean.getRegion();
			String gameId = processBean.getId();

			InputStream in = obtainInputStream(url,region,gameId,-1);
			if(in == null){
				//if disc, try custom, else return
				if(url.equals(CoverConstants.DISC_URL)){
					in = obtainInputStream(CoverConstants.DISCCUSTOM_URL,
											region,gameId,-1);
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
		}
	}


	private InputStream obtainInputStream(String url,String region, String gameId, int cycle) {
		String[] alternate = {"EN","US","JA"};
		InputStream in;
		try {

			String address = url
			+region
			+"/"+gameId+".png";
			
			address = formatUrlAddress(address);
			
			System.out.println("Checking:\n"+address);
			if(!urlExists(address)){
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

	private boolean urlExists(String updateServer) throws IOException {

		HttpURLConnection.setFollowRedirects(false);
		HttpURLConnection con;
		con = (HttpURLConnection) new URL(updateServer).openConnection();

		con.setRequestMethod("HEAD");

		int resp = con.getResponseCode();
		if(resp != HttpURLConnection.HTTP_OK){
			return false;
		}

		return true;
	}

	public static String formatUrlAddress(String indirizzo) {
		if (indirizzo.contains(" ")){
			indirizzo = indirizzo.replaceAll(" ", "%20");
		}
		return indirizzo;
	}

	public static Image imgAddress(String indirizzo){

		Image immagine = null;

		if(indirizzo.startsWith("http://")) {
			URL url;
			try {
				url = new URL(formatUrlAddress(indirizzo));
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
