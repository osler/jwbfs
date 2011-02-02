package jwbfs.ui.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WebUtils {

	public static boolean urlExists(String updateServer) {
	
		HttpURLConnection.setFollowRedirects(false);
		HttpURLConnection con;
		try {
			con = (HttpURLConnection) new URL(updateServer).openConnection();
	
	
		con.setRequestMethod("HEAD");
		con.setReadTimeout(300);// timeout 3 sec
	
		int resp = con.getResponseCode();
		if(resp != HttpURLConnection.HTTP_OK){
			return false;
		}
		} catch (MalformedURLException e) {
			return false;
		} catch (IOException e) {
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

}
