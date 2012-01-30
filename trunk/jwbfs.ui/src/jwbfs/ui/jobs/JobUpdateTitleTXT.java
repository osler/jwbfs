package jwbfs.ui.jobs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import jwbfs.model.ModelStore;
import jwbfs.model.beans.SettingsBean;
import jwbfs.model.utils.CoverConstants;
import jwbfs.model.utils.PlatformUtils;
import jwbfs.ui.utils.GuiUtils;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.graphics.Image;

public class JobUpdateTitleTXT extends JwbfsJob {

	private SettingsBean settingsBean;

	public JobUpdateTitleTXT(String string) {
		super(string);
	}

	@Override
	public IStatus runJwbfs(IProgressMonitor monitor) throws Exception{

		settingsBean = (SettingsBean) ModelStore.getSettingsBean();
		
		monitor.beginTask("Downloading Titles TXT file",
				IProgressMonitor.UNKNOWN);
		
		return downloadTXT(monitor);
	}

	private IStatus downloadTXT(IProgressMonitor monitor) {
		
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
					return Status.CANCEL_STATUS;
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
				return Status.CANCEL_STATUS;
			} catch (IOException e) {
				GuiUtils.showError("cannot download titles.TXT", true);
				return Status.CANCEL_STATUS;
			}finally{
				monitor.done();
			}
	
			if(fileExist(txtFile)){
			System.out.println("titles.TXT downloaded!");
			}
		
			return Status.OK_STATUS;
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
