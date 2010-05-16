package jwbfs.ui.views;


import jwbfs.model.Constants;
import jwbfs.model.Model;
import jwbfs.model.beans.GameBean;
import jwbfs.model.beans.SettingsBean;
import jwbfs.ui.listeners.coverView.FolderCoverDialogListener;
import jwbfs.ui.listeners.coverView.UpdateCoverListener;
import jwbfs.ui.utils.GuiUtils;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.ui.part.ViewPart;

public class CoverView extends ViewPart {

	public static final String ID = "CoverView";
	protected GameBean processBean = null;
	protected SettingsBean settingsBean = null;
	protected Button cover ;
	protected Button cover3d ;
	protected Button disk ;
	protected ProgressBar progressBar;
	
	
	public CoverView() {
		processBean = (GameBean) getProcessBean() ;
		settingsBean = (SettingsBean) getSettingsBean() ;

	}

	private SettingsBean getSettingsBean() {
		return (SettingsBean) Model.getBeans().get(SettingsBean.INDEX);
	}

	private GameBean getProcessBean() {
		return (GameBean) Model.getBeans().get(GameBean.INDEX);
	}

	@Override
	public void createPartControl(Composite parent) {
		
		parent = WidgetCreator.formatComposite(parent);

		Group group =  WidgetCreator.createGroup(parent, "Cover",2);
		
		Image image = new Image( GuiUtils.getDisplay(),Constants.NOIMAGE);
		cover = WidgetCreator.createImage(group);
		cover.setImage(image);
		
		image = new Image( GuiUtils.getDisplay(),Constants.NOIMAGE3D);
		cover3d = WidgetCreator.createImage(group);
		cover3d.setSize(50,50);
		cover3d.setImage(image);
		
		group = WidgetCreator.createGroup(parent, "Disc");
		
		image = new Image( GuiUtils.getDisplay(),Constants.NODISC);
		disk = WidgetCreator.createImage(group);
		disk.setSize(50,50);
		disk.setImage(image);
		
		Composite filler = WidgetCreator.createComposite(parent);
		
		addHandlerUpdate(cover);
		progressBar = WidgetCreator.createProgressBar(parent);
		
		
	}

	private void addHandlerFolder(Button button) {
		
		button.addSelectionListener(new FolderCoverDialogListener(ID));
}
	
	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	private void addHandlerUpdate(Button button) {
		
		button.addSelectionListener(new UpdateCoverListener(ID,processBean));
	
		
	}

	public ProgressBar getProgressBar() {
		return progressBar;
	}

	public void setProgressBar(ProgressBar progressBar) {
		this.progressBar = progressBar;
	}

	/**
	 * @return the cover
	 */
	public Button getCover() {
		return cover;
	}

	/**
	 * @param cover the cover to set
	 */
	public void setCover(Button cover) {
		this.cover = cover;
	}

	/**
	 * @return the cover3d
	 */
	public Button getCover3d() {
		return cover3d;
	}

	/**
	 * @param cover3d the cover3d to set
	 */
	public void setCover3d(Button cover3d) {
		this.cover3d = cover3d;
	}

	/**
	 * @return the disk
	 */
	public Button getDisk() {
		return disk;
	}

	/**
	 * @param disk the disk to set
	 */
	public void setDisk(Button disk) {
		this.disk = disk;
	}

}
