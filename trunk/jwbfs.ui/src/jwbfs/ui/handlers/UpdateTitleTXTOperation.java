package jwbfs.ui.handlers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;

import jwbfs.model.Model;
import jwbfs.model.beans.SettingsBean;
import jwbfs.model.utils.CoverConstants;
import jwbfs.model.utils.PlatformUtils;
import jwbfs.ui.utils.GuiUtils;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.graphics.Image;

public class UpdateTitleTXTOperation implements IRunnableWithProgress {

	private SettingsBean settingsBean;

	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {

		settingsBean = (SettingsBean) Model.getBeans().get(SettingsBean.INDEX);
		
		monitor.beginTask("Downloading Titles TXT file",
				IProgressMonitor.UNKNOWN);
		
		downloadTXT(monitor);

		monitor.done();
	}

	private void downloadTXT(IProgressMonitor monitor) {
		
		int progress = 5;
			monitor.worked(progress);
			
			String address = CoverConstants.TITLES_URL;
			String region = settingsBean.getRegion();
	
			String txtFile = PlatformUtils.getTitlesTXTpath();
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
					
					monitor.worked(progress++);
				}
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
				
	
			} catch (MalformedURLException e) {
				GuiUtils.showError("cannot download titles.TXT", true);
				monitor.done();
				return;
			} catch (IOException e) {
				GuiUtils.showError("cannot download titles.TXT", true);
				monitor.done();
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
				immagine = new Image(GuiUtils.getDisplay(),CoverConstants.NOIMAGE);			
			}
	
	
	
		}else{
			immagine = new Image(null,indirizzo);
		}
		return immagine;
	}

}
