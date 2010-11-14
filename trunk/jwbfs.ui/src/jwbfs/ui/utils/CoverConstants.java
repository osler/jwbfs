package jwbfs.ui.utils;

import java.io.File;

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
	
	public static final String IMAGE_FOLDER_NAME = "images";
	public static final String IMAGE_2D_NAME = "2D";
	public static final String IMAGE_3D_NAME = "";
	public static final String IMAGE_DISC_NAME = "disc";
	
	
}
