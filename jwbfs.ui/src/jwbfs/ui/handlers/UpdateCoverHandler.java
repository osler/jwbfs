package jwbfs.ui.handlers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import jwbfs.model.Constants;
import jwbfs.model.CoverConstants;
import jwbfs.model.Model;
import jwbfs.model.beans.GameBean;
import jwbfs.model.beans.SettingsBean;
import jwbfs.ui.utils.FileUtils;
import jwbfs.ui.utils.GuiUtils;
import jwbfs.ui.views.CoverView;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.graphics.Image;

public class UpdateCoverHandler extends AbstractHandler {
	private GameBean processBean;
	private SettingsBean settingsBean;
	public static final String ID = "updateCover";

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		processBean = (GameBean) Model.getBeans().get(GameBean.INDEX);
		settingsBean = (SettingsBean) Model.getBeans().get(settingsBean.INDEX);


		if(settingsBean.isManagerMode()){

			//TODO
			processBean = (GameBean) Model.getSelectedGame();			
			executeForBean();


		}else{
			settingsBean = (SettingsBean) Model.getBeans().get(SettingsBean.INDEX);

			executeForBean();
		}


		return null;
	}

	private void executeForBean() {

		((CoverView) GuiUtils.getView(CoverView.ID)).getProgressBar().setMaximum(getProgress());
		((CoverView) GuiUtils.getView(CoverView.ID)).getProgressBar().setSelection(0);

		boolean updateCover = settingsBean.isUpdateCover();
		String gameId = processBean.getId();
		String coverPath = settingsBean.getCoverPath()
		+File.separatorChar
		+gameId
		+".png";

		//IF not wii disc return and set default image
		if(gameId.contains("not a wii disc")){
			setCover(Constants.NOIMAGE);
			settingsBean.setUpdateCover(false);
			return;
		}

		if(FileUtils.coverFileExist(coverPath) && !updateCover){
			setCover(coverPath);			
		}else{
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
					setCover(coverPath);
				}else{
					//Not found
					setCover(Constants.NOIMAGE);
				}
				//progressbar
				((CoverView) GuiUtils.getView(CoverView.ID)).getProgressBar().setSelection(1);

			}
		}

		if(settingsBean.isAutomaticCoverDownload() || !updateCover)
			if(settingsBean.isCover3D()){
				String folder = settingsBean.getCoverPath()
				+File.separatorChar
				+"3d";

				FileUtils.checkAndCreateFolder(folder);

				coverPath = folder
				+File.separatorChar
				+gameId
				+".png";
				if(!FileUtils.coverFileExist(coverPath) && !updateCover){
					downloadCover(CoverConstants.COVER3D_URL,coverPath);	
				}
				//progressbar
				((CoverView) GuiUtils.getView(CoverView.ID)).getProgressBar().setSelection(getProgress()>2?2:getProgress());

			}

		if(settingsBean.isAutomaticCoverDownload() || !updateCover)
			if(settingsBean.isCoverDiscs()){
				String folder = settingsBean.getCoverPath()
				+File.separatorChar
				+"discs";

				FileUtils.checkAndCreateFolder(folder);

				coverPath = folder
				+File.separatorChar
				+gameId
				+".png";
				if(!FileUtils.coverFileExist(coverPath) && !updateCover){
					downloadCover(CoverConstants.DISC_URL,coverPath);
				}
				//progressbar
				((CoverView) GuiUtils.getView(CoverView.ID)).getProgressBar().setSelection(getProgress()>2?3:getProgress());

			}

		settingsBean.setUpdateCover(false);
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

			InputStream in = checkAndValidate(url,region,gameId,0);
			if(in == null){
				return;
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

	private void setCover(String coverPath) {

		System.out.println("Setting cover:");
		System.out.println(coverPath);
		Image img = new Image(GuiUtils.getDisplay(),coverPath);
		((CoverView) GuiUtils.getView(CoverView.ID)).getImageButton().setImage(img);


	}

	private InputStream checkAndValidate(String url,String region, String gameId, int cycle) {

		String[] alternate = {"EN","US","JA"};

		if(cycle >= alternate.length){
			return null;
		}

		String address = url
		+region
		+"/"+gameId+".png";

		InputStream in;
		try {
			URL u = new URL(checkUrl(address));
			System.out.println("Checking:\n"+address);
			in = u.openStream();
			System.out.println("Url cover correct");


		} catch (MalformedURLException e) {
			System.out.println("Region cover not found trying "+alternate[cycle]);
			in = checkAndValidate(url,alternate[cycle],gameId,++cycle);
			return in;

		} catch (IOException e) {
			System.out.println("Region cover not found trying "+alternate[cycle]);
			in = checkAndValidate(url,alternate[cycle],gameId,++cycle);		
			return in;
		}


		return in;
	}

	public static String checkUrl(String indirizzo) {
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
				url = new URL(checkUrl(indirizzo));
				InputStream is = url.openStream();
				immagine = new Image(null,is);

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();	
				immagine = new Image(GuiUtils.getDisplay(),Constants.NOIMAGE);			
			}



		}else{
			immagine = new Image(null,indirizzo);
		}
		return immagine;
	}

}
