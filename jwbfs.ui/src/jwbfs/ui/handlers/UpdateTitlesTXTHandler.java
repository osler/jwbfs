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
import jwbfs.ui.utils.Utils;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;

public class UpdateTitlesTXTHandler extends AbstractHandler {
	private ProcessBean processBean;
	private SettingsBean settingsBean;
	public static final String ID = "updateTitles";

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		processBean = (ProcessBean) Model.getTabs().get(ProcessBean.INDEX);
		settingsBean = (SettingsBean) Model.getTabs().get(SettingsBean.INDEX);
		
		downloadTXT();

		return null;
	}

	private void downloadTXT() {
		

			String address = Constants.TITLES_URL;
			String region = settingsBean.getRegion();

			String txtFile = Utils.getTitlesTXTpath();
			InputStream in;
			try {
				URL url = new URL(checkUrl(address+region));
				System.out.println("Checking:\n"+address+region);
				in = url.openStream();
				System.out.println("Url correct");

				if(in == null){
					return;
				}
				
				FileOutputStream out = new FileOutputStream(txtFile);

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
				

			} catch (MalformedURLException e) {
				GuiUtils.showInfo("cannot download titles.TXT", SWT.ERROR);
				return;
			} catch (IOException e) {
				GuiUtils.showInfo("cannot download titles.TXT", SWT.ERROR);
				return;
			}

			if(fileExist(txtFile)){
			System.out.println("titles.TXT downloaded!");
			}

	}

	private boolean fileExist(String coverPath) {
		File file = new File(coverPath);
		boolean exist = file.exists();
		if(exist){
			System.out.println("file exists:");
			System.out.println(coverPath);
		}
		return exist;
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
