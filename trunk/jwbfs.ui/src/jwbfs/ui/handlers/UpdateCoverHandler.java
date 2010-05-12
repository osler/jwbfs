package jwbfs.ui.handlers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import jwbfs.model.Constants;
import jwbfs.model.Model;
import jwbfs.model.beans.ProcessBean;
import jwbfs.model.beans.SettingsBean;
import jwbfs.ui.utils.GuiUtils;
import jwbfs.ui.views.CoverView;
import jwbfs.ui.views.folder.ProcessView;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.graphics.Image;

public class UpdateCoverHandler extends AbstractHandler {
	private ProcessBean processBean;
	private SettingsBean settingsBean;
	public static final String ID = "updateCover";

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		processBean = (ProcessBean) Model.getTabs().get(ProcessBean.INDEX);
		settingsBean = (SettingsBean) Model.getTabs().get(SettingsBean.INDEX);

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
			return null;
		}

		if(coverFileExist(coverPath) && !updateCover){
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
				downloadCover(Constants.COVER_URL,coverPath);

				if(coverFileExist(coverPath)){
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
			
			checkFolder(folder);
			
			coverPath = folder
			+File.separatorChar
			+gameId
			+".png";
			downloadCover(Constants.COVER3D_URL,coverPath);
			//progressbar
		    ((CoverView) GuiUtils.getView(CoverView.ID)).getProgressBar().setSelection(getProgress()>2?2:getProgress());
			
		}
		
		if(settingsBean.isAutomaticCoverDownload() || !updateCover)
			if(settingsBean.isCoverDiscs()){
				String folder = settingsBean.getCoverPath()
				+File.separatorChar
				+"discs";
				
				checkFolder(folder);
				
				coverPath = folder
				+File.separatorChar
				+gameId
				+".png";
				
				downloadCover(Constants.DISC_URL,coverPath);
				//progressbar
			    ((CoverView) GuiUtils.getView(CoverView.ID)).getProgressBar().setSelection(getProgress()>2?3:getProgress());
				
			}
		
		settingsBean.setUpdateCover(false);
		return null;
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

	private void checkFolder(String folder) {
	
		File fol = new File(folder);
		if(fol.exists() && fol.isDirectory()){
			return;
		}else{
			fol.mkdir();
			System.out.println("Folder created: \n"+folder);
			return;
		}
		
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

	private boolean coverFileExist(String coverPath) {
		File file = new File(coverPath);
		boolean exist = file.exists();
		if(exist){
			System.out.println("Cover exists:");
			System.out.println(coverPath);
		}
		return exist;
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
