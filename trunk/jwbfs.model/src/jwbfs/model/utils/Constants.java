package jwbfs.model.utils;




public class Constants {
	
	/**
	 *     -s SIZE  :  Set split size [4294934528] (8388544 sectors)
                Must be a multiple of 512 (sector size)
    -2       :  Use split size: 2GB-32kb (2147450880)
    -4       :  Use split size: 4GB-32kb (4294934528)
    -0       :  Don't split (split size: 10000000000)
	 */
	public static final  String[] SPLITSIZE_Values = {"-0","-2","-4"};
	public static final  String[] SPLITSIZE_Text = {"Don't split","2GB-32kb (2147450880)","4GB-32kb (4294934528)"};
		
	/**
	-a       :  Copy ALL partitions from ISO [default]
    -g       :  Copy only game partition from ISO
    -1       :  Copy 1:1 from ISO
	 */
	public static final  String[] COPY_PARTITIONS_Values = {"-g","-a","-1"};;
	public static final  String[] COPY_PARTITIONS_Text = {"Only Game Partitions","All Partitions","Copy 1:1"};;
	
	/**
	 * 	-x 0|1   :  disable|enable .txt file creation [default:0]
	 */
	
	public static final  String[] ENABLE_TXT_CREATION_Values = {"-x 1","-x 0"};;
	public static final  String[] ENABLE_TXT_CREATION_Text = {"enable","disable"};;
	
	/**
    -l X     :  Layout of the destination filename:
                -l f0 = file: ID.ext             (same as -b)
                -l f1 = file: ID_TITLE.ext
                -l f2 = file: TITLE [ID].ext
                -l d0 = dir:  ID/ID.ext
                -l d1 = dir:  ID_TITLE/ID.ext    (same as -d)
                -l d2 = dir:  TITLE [ID]/ID.ext  [default]
	 */
	
	public static final  String[] TXT_LAYOUT_Values = {"-lf0","-lf1","-lf2",
													 "-ld0","-ld1","-ld2"};;
	public static final  String[] TXT_LAYOUT_Text = {"ID.ext",
												"ID_TITLE.ext",
												"TITLE [ID].ext",
												"ID/ID.ext",
												"ID_TITLE/ID.ext",
												"TITLE [ID]/ID.ext"};
	
	public static final String MAINVIEW_ID = "ManagerView";
	public static final String COMMAND_CHECKDISK_ID = 	"checkDisk";
	public static final String SETTINGSVIEW_ID = "SettingsView";
	public static final String BUNDLE_CORE = "jwbfs.core";
	public static final String BUNDLE_MODEL = "jwbfs.model";
	public static final String BUNDLE_UI = "jwbfs.ui";


	/*
	 
	   Given just a filename it will convert from iso to wbfs or vice versa:

    wbfs_file filename.iso
    Will convert filename.iso to GAMEID.wbfs
    And create an info file GAMEID_TITLE.txt
	private GameBean processBean;	private GameBean processBean;	private GameBean processBe	private GameBean processBean;an;
    wbfs_file filename.wbfs
    Will convert filename.wbfs to GAMEID_TITLE.iso

  COMMANDS:
    <drive or file.iso>  convert  <DST:dir or file.wbfs>
        <filename.wbfs>  convert  <DST:dir or file.iso>
    <drive or file.iso>  scrub    <DST:dir or file.iso>
    <DST:filename.wbfs>  create   <SRC:drive or file.iso>
        <drive or file>  ls               
        <drive or file>  df               
        <drive or file>  make_info        
        <drive or file>  id_title         
    <DST:drive or file>  init             
    <DST:drive or file>  add_iso          <SRC:drive or file.iso>
    <DST:drive or file>  add_wbfs         <SRC:filename.wbfs>
    <DST:drive or file>  rm               <GAMEID>
        <drive or file>  extract_iso      <GAMEID> <DST:dir or file.iso>
        <drive or file>  extract_wbfs     <GAMEID> <DST:dir or file.wbfs>
        <drive or file>  extract_wbfs_all <DST:dir>
        <drive or file>  wbfs_copy        <GAMEID> <DST:drive or file.wbfs>
        <drive or file>  ls_file          <GAMEID>
        <drive or file>  extract_file     <GAMEID> <file> [<DST:file>]
        <drive or file>  debug_info       
        <drive or file>  iso_info

  OPTIONS: (it's recommended to just use the defaults)
    -s SIZE  :  Set split size [4294934528] (8388544 sectors)
                Must be a multiple of 512 (sector size)
    -2       :  Use split size: 2GB-32kb (2147450880)
    -4       :  Use split size: 4GB-32kb (4294934528)
    -0       :  Don't split (split size: 10000000000)
    -u SIZE  :  Set scrub block size [32768] (1 wii sector)
                Must be a multiple of 32768 (wii sector size)
                Special values: 1=1 wii sector, 2=2mb (.wbfs block)
    -z       :  make zero filled blocks as sparse when scrubbing
    -a       :  Copy ALL partitions from ISO [default]
    -g       :  Copy only game partition from ISO
    -1       :  Copy 1:1 from ISO
    -f       :  Force wbfs mode even if the wbfs file or partition
                integrity check is invalid (non matching number of
                sectors or other parameters)
    -t       :  trim extracted iso size
    -x 0|1   :  disable|enable .txt file creation [default:0]
    -l X     :  Layout of the destination filename:
                -l f0 = file: ID.ext             (same as -b)
                -l f1 = file: ID_TITLE.ext
                -l f2 = file: TITLE [ID].ext
                -l d0 = dir:  ID/ID.ext
                -l d1 = dir:  ID_TITLE/ID.ext    (same as -d)
                -l d2 = dir:  TITLE [ID]/ID.ext  [default]
    -b       :  Same as -l f0
    -d       :  Same as -l d1
    -h       :  Help

	 
	 */
	
	public static String decodeValue(String selection, String[] text, String values[]){
		String ret = "";
		
		for(int x = 0; x < text.length;x++){
			if(text[x].equalsIgnoreCase(selection)){
				
				ret = values[x];
				return ret;
			}
		}
		
		return ret;
		
	}
	
	public static String decodeValue(boolean selection, String values[]){
		String ret = "";


		if(selection){

			ret = values[0];
			return ret;
		}else{
			ret = values[1];
			return ret;	
		}

	}
	
}
