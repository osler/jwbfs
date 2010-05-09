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
		
		String gameId = bean.getId();
		
		String temp = System.getProperty("java.io.tmpdir")+File.separatorChar+gameId+".png";
		String address = Constants.COVER_URL+"EN"+gameId+".png";
		
					
			try {			
			int c;
			URL url = new URL(checkUrl(address));
			InputStream in = url.openStream();
			
			FileOutputStream out = new FileOutputStream(temp);
			
			while ((c = in.read()) != -1){
				 out.write(c);
			}
			 if (in != null) {
	                in.close();
	            }
	            if (out != null) {
	                out.close();
	            }

			File f = new File(temp);
			
			
			Image img = new Image(GuiUtils.getDisplay(),temp);
//			Image img = new Image(GuiUtils.getDisplay(),Constants.TESTIMAGE);
			((CoverView) GuiUtils.getView(CoverView.ID)).getImageButton().setImage(img);

			
		} catch (IOException e) {
			e.printStackTrace();
		}
		

		return null;
	}
	
	public static String checkUrl(String indirizzo) {
		String modificato = null;
		if (indirizzo.contains(" ")){
			modificato = indirizzo.replaceAll(" ", "%20");
		}
		return modificato;
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
