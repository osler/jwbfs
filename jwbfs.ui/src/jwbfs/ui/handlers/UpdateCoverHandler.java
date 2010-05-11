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

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.graphics.Image;

public class UpdateCoverHandler extends AbstractHandler {


	public static final String ID = "updateCover";
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		ProcessBean bean = (ProcessBean) Model.getTabs().get(ProcessBean.INDEX);
		SettingsBean settingsBean = (SettingsBean) Model.getTabs().get(SettingsBean.INDEX);
		
		String gameId = bean.getId();
		if(gameId.contains("not a wii disc")){

			Image img = new Image(GuiUtils.getDisplay(),Constants.NOIMAGE);
			
			((CoverView) GuiUtils.getView(CoverView.ID)).getImageButton().setImage(img);
			return null;
		}
		
		String coverPath = settingsBean.getCoverPath()
							+File.separatorChar
							+gameId
							+".png";
		String region = settingsBean.getRegion();
							
		try {			
			int c;

			InputStream in = checkAndValidate(region,gameId);

			FileOutputStream out = new FileOutputStream(coverPath);

			while ((c = in.read()) != -1){
				out.write(c);
			}
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}

			Image img = new Image(GuiUtils.getDisplay(),coverPath);

			((CoverView) GuiUtils.getView(CoverView.ID)).getImageButton().setImage(img);


		} catch (IOException e) {
			e.printStackTrace();
		}
		

		return null;
	}
	
	private InputStream checkAndValidate(String region, String gameId) {

		String address = Constants.COVER_URL
		+region
		+"/"+gameId+".png";
		
		InputStream in;
		try {
			URL url = new URL(checkUrl(address));
			in = url.openStream();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
			address = Constants.COVER_URL
			+"EN"
			+"/"+gameId+".png";
			in = checkAndValidate(region,gameId);
			return in;
			
		} catch (IOException e) {
			e.printStackTrace();
			address = Constants.COVER_URL
			+"EN"
			+"/"+gameId+".png";
			in = checkAndValidate(region,gameId);
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
