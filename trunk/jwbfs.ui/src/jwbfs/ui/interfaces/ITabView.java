package jwbfs.ui.interfaces;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

public interface ITabView {
	
	public Control createView();
	
	public TabFolder getParent();

	public TabItem getTabItem();
		
}
