package jwbfs.ui.views.table;

import jwbfs.model.beans.GameBean;
import jwbfs.model.utils.PlatformUtils;
import jwbfs.ui.utils.GuiUtils;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

public class ManagerViewLabelProvider implements ITableLabelProvider {

	public ManagerViewLabelProvider() {

	}

	// Names of images used to represent checkboxes
	public static final String CHECKED_IMAGE 	= "checked";
	public static final String UNCHECKED_IMAGE  = "unchecked";

	// For the checkbox images
	private static ImageRegistry imageRegistry = new ImageRegistry();

	/**
	 * Note: An image registry owns all of the image objects registered with it,
	 * and automatically disposes of them the SWT Display is disposed.
	 */ 
	static {
		String iconPath = "icons/"; 
		
		Image imgU = new Image(GuiUtils.getDisplay(),PlatformUtils.getRoot()+ iconPath + UNCHECKED_IMAGE + ".png");
		Image imgC = new Image(GuiUtils.getDisplay(),PlatformUtils.getRoot()+ iconPath + CHECKED_IMAGE + ".png");
		
		imageRegistry.put(CHECKED_IMAGE, ImageDescriptor.createFromImage(imgC)
			);
		imageRegistry.put(UNCHECKED_IMAGE, ImageDescriptor.createFromImage(imgU)
			);	
	}
	
	private Image getImage(boolean isSelected) {
		String key = isSelected ? CHECKED_IMAGE : UNCHECKED_IMAGE;
		return  imageRegistry.get(key);
	}
	
	public Image getColumnImage(Object element, int columnIndex) {
		
		GameBean r = (GameBean) element;
		
		switch (columnIndex) {
		case 0:
			return getImage(r.isSelected());	
		}
		
		return null;
	}

	
	public String getColumnText(Object element, int columnIndex) {
		GameBean r = (GameBean) element;

		switch (columnIndex) {
		case 0:
			//use image
			return null;
		case 1:

			return r.getId();

		case 2:
			
			return r.getTitle();
					
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
