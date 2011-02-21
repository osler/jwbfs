package jwbfs.ui.perspectives;

import jwbfs.model.utils.CoreConstants;

public class DisksPerspective6 extends DisksPerspective5 {


	protected void addDisksTabs() {	
		super.addDisksTabs();
		diskFolder.addView(CoreConstants.VIEW_DISK_6_ID);	
	}
}
