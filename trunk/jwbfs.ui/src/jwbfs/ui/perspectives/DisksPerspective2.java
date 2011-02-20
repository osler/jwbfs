package jwbfs.ui.perspectives;

import jwbfs.model.utils.CoreConstants;

public class DisksPerspective2 extends DisksPerspective1 {


	protected void addDisksTabs() {	
		super.addDisksTabs();
		diskFolder.addView(CoreConstants.VIEW_DISK_1_ID);
	}
}
