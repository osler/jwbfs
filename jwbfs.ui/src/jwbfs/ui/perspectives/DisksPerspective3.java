package jwbfs.ui.perspectives;

import jwbfs.model.utils.CoreConstants;

public class DisksPerspective3 extends DisksPerspective2 {


	protected void addDisksTabs() {	
		super.addDisksTabs();
		leftFolder.addView(CoreConstants.VIEW_DISK_3_ID);
	}
}
