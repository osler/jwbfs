package jwbfs.ui.utils;

import jwbfs.model.utils.Constants;
import jwbfs.ui.views.CoverView;
import jwbfs.ui.views.ManagerView;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

public class GuiUtils {

	
	public static ViewPart getView(String ID){
		ViewPart view = null;
		try{
			view = (ViewPart) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(ID);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}

	public static Device getDisplay() {
		return PlatformUI.getWorkbench().getDisplay();
	}

	public static void showInfo(String message, int style) {
		MessageBox msg = new MessageBox(new Shell(),style);
		msg.setMessage(message);
		msg.open();
		
	}

	public static TableViewer getManagerTableViewer() {
		// TODO Auto-generated method stub
		return 	((ManagerView)GuiUtils.getView(ManagerView.ID)).getTv();
	}

	public static boolean showConfirmDialog(String message) {
		boolean ret =  MessageDialog.openConfirm(new Shell(), "Confirm", message);

		return ret;
	}


	public static void setCover(String coverPath) {

		System.out.println("Setting cover:");
		System.out.println(coverPath);
		Image img = new Image(GuiUtils.getDisplay(),coverPath);
		((CoverView) GuiUtils.getView(CoverView.ID)).getCover().setImage(img);


	}
	
	public static void setCover3d(String coverPath) {

		System.out.println("Setting cover3d:");
		System.out.println(coverPath);
		Image img = new Image(GuiUtils.getDisplay(),coverPath);
		((CoverView) GuiUtils.getView(CoverView.ID)).getCover3d().setImage(img);


	}
	
	public static void setCoverDisc(String coverPath) {

		System.out.println("Setting cover disc:");
		System.out.println(coverPath);
		Image img = new Image(GuiUtils.getDisplay(),coverPath);
		((CoverView) GuiUtils.getView(CoverView.ID)).getDisk().setImage(img);


	}
	
	public static void setDefaultCovers() {
		GuiUtils.setCover(CoverConstants.NOIMAGE);
		GuiUtils.setCover3d(CoverConstants.NOIMAGE3D);
		GuiUtils.setCoverDisc(CoverConstants.NODISC);
	}
	
}
