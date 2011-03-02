package jwbfs.model.utils;


public class Decode {

	public static String decodeDiskNum(String diskID) {
		
		if(diskID.trim().equals(CoreConstants.VIEW_DISK_1_ID))return "1";
		if(diskID.trim().equals(CoreConstants.VIEW_DISK_2_ID))return "2";
		if(diskID.trim().equals(CoreConstants.VIEW_DISK_3_ID))return "3";
		if(diskID.trim().equals(CoreConstants.VIEW_DISK_4_ID))return "4";
		if(diskID.trim().equals(CoreConstants.VIEW_DISK_5_ID))return "5";
		if(diskID.trim().equals(CoreConstants.VIEW_DISK_6_ID))return "6";
		
		return null;
	}
	
	
	public static String decodeFileName(String diskID) {
		if(diskID.trim().equals(CoreConstants.VIEW_DISK_1_ID))return "disk1.ini";
		if(diskID.trim().equals(CoreConstants.VIEW_DISK_2_ID))return "disk2.ini";
		if(diskID.trim().equals(CoreConstants.VIEW_DISK_3_ID))return "disk3.ini";
		if(diskID.trim().equals(CoreConstants.VIEW_DISK_4_ID))return "disk4.ini";
		if(diskID.trim().equals(CoreConstants.VIEW_DISK_5_ID))return "disk5.ini";
		if(diskID.trim().equals(CoreConstants.VIEW_DISK_6_ID))return "disk6.ini";
		return CoreConstants.wbfsINI;
	}


	public static String decodeViewNameFromDiskINI(String diskINI) {

		if(diskINI.indexOf("disk1.ini")>-1)return CoreConstants.VIEW_DISK_1_ID;
		if(diskINI.indexOf("disk2.ini")>-1)return CoreConstants.VIEW_DISK_2_ID;
		if(diskINI.indexOf("disk3.ini")>-1)return CoreConstants.VIEW_DISK_3_ID;
		if(diskINI.indexOf("disk4.ini")>-1)return CoreConstants.VIEW_DISK_4_ID;
		if(diskINI.indexOf("disk5.ini")>-1)return CoreConstants.VIEW_DISK_5_ID;
		if(diskINI.indexOf("disk6.ini")>-1)return CoreConstants.VIEW_DISK_6_ID;
		return null;
	}


	public static String decodeContextFromView(String diskID) {
		
		if(diskID.trim().equals(CoreConstants.VIEW_DISK_1_ID))return CoreConstants.CONTEXT_DISK_1;
		if(diskID.trim().equals(CoreConstants.VIEW_DISK_2_ID))return CoreConstants.CONTEXT_DISK_2;
		if(diskID.trim().equals(CoreConstants.VIEW_DISK_3_ID))return CoreConstants.CONTEXT_DISK_3;
		if(diskID.trim().equals(CoreConstants.VIEW_DISK_4_ID))return CoreConstants.CONTEXT_DISK_4;
		if(diskID.trim().equals(CoreConstants.VIEW_DISK_5_ID))return CoreConstants.CONTEXT_DISK_5;
		if(diskID.trim().equals(CoreConstants.VIEW_DISK_6_ID))return CoreConstants.CONTEXT_DISK_6;
	
		return null;
	}

}
