package jwbfs.model.utils;

import jwbfs.model.ModelStore;

public class DiskUtils {

	/**
	 * Check if the disk has a valid path
	 * @param diskTO
	 * @return
	 */
	public static boolean pathExists(String diskTO) {
		String diskPath = ModelStore.getDisk(diskTO).getDiskPath();
		boolean exists = FileUtils.exists(diskPath);
		return exists;
	}
	
}
