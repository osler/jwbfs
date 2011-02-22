package jwbfs.ui.perspectives;

import jwbfs.model.utils.CoreConstants;

public class DisksPerspective2 extends DisksPerspective1 {


	protected void addDisksTabs() {	
		super.addDisksTabs();
		leftFolder.addView(CoreConstants.VIEW_DISK_2_ID);
	}
}
