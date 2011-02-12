package jwbfs.model.utils;

import java.io.File;

import jwbfs.model.Model;
import jwbfs.model.beans.CoverSettings;

public class CoverConstants {

	/*
	 
	 NTSC-U: US
PAL: EN, FR, DE, ES, IT, NL, PT, AU
NTSC-J: JA
NTSC-K: KO

Artwork URL to be used by loaders (160x224):
http://wiitdb.com/wiitdb/artwork/cover/
http://wiitdb.com/wiitdb/artwork/cover3D/
http://wiitdb.com/wiitdb/artwork/coverfull/
http://wiitdb.com/wiitdb/artwork/disc/
http://wiitdb.com/wiitdb/artwork/disccustom/

TITLES:
http://wiitdb.com/titles.txt?LANG=XX
	 */
	
	public static final  String[] REGIONS = {"EN", "FR", "DE", "ES", "IT", "NL", "PT", "AU","US","JA","KO"};
	
	public static final  String COVER_URL = "http://wiitdb.com/wiitdb/artwork/cover/";
	public static final  String COVER3D_URL = "http://wiitdb.com/wiitdb/artwork/cover3D/";
	public static final  String COVERFULL_URL = "http://wiitdb.com/wiitdb/artwork/coverfull/";
	public static final  String DISC_URL = "http://wiitdb.com/wiitdb/artwork/disc/";
	public static final  String DISCCUSTOM_URL = "http://wiitdb.com/wiitdb/artwork/disccustom/";
	public static final  String TITLES_URL = "http://wiitdb.com/titles.txt?LANG=";
	
	public static final String NOIMAGE = PlatformUtils.getRoot()+"icons"+File.separatorChar+"noimg.png";
	public static final String NOIMAGE3D = PlatformUtils.getRoot()+"icons"+File.separatorChar+"noimg3d.png";
	public static final String NODISC = PlatformUtils.getRoot()+"icons"+File.separatorChar+"nodisc.png";
	
	public static final String COVER_3D = "3D";
	public static final String COVER_2D = "2D";
	public static final String COVER_DISC = "disc";
	public static final String COVER_FULLBOX = "coverfull";

	//USBLOADER CFG
	public static final String IMAGE_FOLDER_NAME_CFG = "usb-loader"+File.separatorChar+"covers";
	public static final String IMAGE_2D_NAME_CFG = "";
	public static final String IMAGE_3D_NAME_CFG = "3d";
	public static final String IMAGE_DISC_NAME_CFG = "disc";
	public static final String IMAGE_FULL_CFG = "full";
	
	//USBLOADER GX
	public static final String IMAGE_FOLDER_NAME_GX = "images";
	public static final String IMAGE_2D_NAME_GX = "2D";
	public static final String IMAGE_3D_NAME_GX = "";
	public static final String IMAGE_DISC_NAME_GX = "disc";
	public static final String IMAGE_FULL_GX = "notSupportedFull";  //not supported
	
	//WIIFLOW
	public static final String IMAGE_FOLDER_NAME_WIIFLOW = "wiiflow";
	public static final String IMAGE_2D_NAME_WIIFLOW = "covers";
	public static final String IMAGE_3D_NAME_WIIFLOW = "notSupported3D"; //not supported
	public static final String IMAGE_DISC_NAME_WIIFLOW = "notSupportedDisc"; //not supported
	public static final String IMAGE_FULL_WIIFLOW = "boxcovers";
	
	public static String getFolderName(){

		CoverSettings settings = Model.getSettingsBean().getCoverSettings();
		
		if(settings.isCoverTypeUSBLoaderGX()){
			return IMAGE_FOLDER_NAME_GX;
		}
		if(settings.isCoverTypeUSBLoaderCFG()){
			return IMAGE_FOLDER_NAME_CFG;
		}
		if(settings.isCoverTypeUSBLoaderWIIFLOW()){
			return IMAGE_FOLDER_NAME_WIIFLOW;
		}
		
		return null;
	}
	
	public static String getImage2D(){

		CoverSettings settings = Model.getSettingsBean().getCoverSettings();
		
		if(settings.isCoverTypeUSBLoaderGX()){
			return IMAGE_2D_NAME_GX;
		}
		if(settings.isCoverTypeUSBLoaderCFG()){
			return IMAGE_2D_NAME_CFG;
		}
		if(settings.isCoverTypeUSBLoaderWIIFLOW()){
			return IMAGE_2D_NAME_WIIFLOW;
		}
		
		return null;
	}
	
	public static String getImage3D(){

		CoverSettings settings = Model.getSettingsBean().getCoverSettings();
		
		if(settings.isCoverTypeUSBLoaderGX()){
			return IMAGE_3D_NAME_GX;
		}
		if(settings.isCoverTypeUSBLoaderCFG()){
			return IMAGE_3D_NAME_CFG;
		}
		if(settings.isCoverTypeUSBLoaderWIIFLOW()){
			return IMAGE_3D_NAME_WIIFLOW;
		}
		
		return null;
	}
	
	public static String getImageDisc(){

		CoverSettings settings = Model.getSettingsBean().getCoverSettings();
		
		if(settings.isCoverTypeUSBLoaderGX()){
			return IMAGE_DISC_NAME_GX;
		}
		if(settings.isCoverTypeUSBLoaderCFG()){
			return IMAGE_DISC_NAME_CFG;
		}
		if(settings.isCoverTypeUSBLoaderWIIFLOW()){
			return IMAGE_DISC_NAME_WIIFLOW;
		}
		
		return null;
	}
	
	public static String getImageFullCover(){

		CoverSettings settings = Model.getSettingsBean().getCoverSettings();
		
		if(settings.isCoverTypeUSBLoaderGX()){
			return IMAGE_FULL_GX;
		}
		if(settings.isCoverTypeUSBLoaderCFG()){
			return IMAGE_FULL_CFG;
		}
		if(settings.isCoverTypeUSBLoaderWIIFLOW()){
			return IMAGE_FULL_WIIFLOW;
		}
		
		return null;
	}
}
