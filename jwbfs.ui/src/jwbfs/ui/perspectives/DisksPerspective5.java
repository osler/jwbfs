package jwbfs.ui.perspectives;

import jwbfs.model.utils.CoreConstants;

public class DisksPerspective5 extends DisksPerspective4 {


	protected void addDisksTabs() {	
		super.addDisksTabs();
		diskFolder.addView(CoreConstants.VIEW_DISK_4_ID);
	}
}
