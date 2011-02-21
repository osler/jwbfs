package jwbfs.ui.perspectives;

import jwbfs.model.utils.CoreConstants;

public class DisksPerspective3 extends DisksPerspective2 {


	protected void addDisksTabs() {	
		super.addDisksTabs();
		diskFolder.addView(CoreConstants.VIEW_DISK_3_ID);
	}
}
