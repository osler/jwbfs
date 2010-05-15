package jwbfs.ui.views.table;

import jwbfs.model.beans.ProcessBean;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

public class ManagerViewLabelProvider implements ITableLabelProvider {

	public ManagerViewLabelProvider() {

	}

	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}
	
	public String getColumnText(Object element, int columnIndex) {
		ProcessBean r = (ProcessBean) element;

		switch (columnIndex) {
		case 0:

			return r.getId();

		case 1:
			
			return r.getTitle();
			
		case 2:
			
			return r.getRegion();
			
		case 3:
			
			return r.getScrubGb();
		}
		

		return "";

	}


	public void addListener(ILabelProviderListener listener) {
	}

	public void dispose() {
	}

	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	public void removeListener(ILabelProviderListener listener) {
	}

}
