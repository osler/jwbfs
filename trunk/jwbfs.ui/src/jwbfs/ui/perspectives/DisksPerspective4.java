package jwbfs.ui.perspectives;

import jwbfs.model.utils.CoreConstants;

public class DisksPerspective4 extends DisksPerspective3 {


	protected void addDisksTabs() {	
		super.addDisksTabs();
		diskFolder.addView(CoreConstants.VIEW_DISK_4_ID);
	}
}
