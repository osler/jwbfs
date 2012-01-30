package jwbfs.ui.views;


import jwbfs.i18n.Messages;
import jwbfs.model.ModelStore;
import jwbfs.model.beans.GameBean;
import jwbfs.model.beans.SettingsBean;
import jwbfs.model.utils.CoreConstants;
import jwbfs.model.utils.CoverConstants;
import jwbfs.ui.listeners.coverView.UpdateCoverListener;
import jwbfs.ui.utils.GuiUtils;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.part.ViewPart;

public class CoverView extends ViewPart {


	protected GameBean processBean = null;
	protected SettingsBean settingsBean = null;
	protected Button cover ;
	protected Button cover3d ;
	protected Button disk ;
//	protected ProgressBar progressBar;
	
	
	public CoverView() {
		processBean = (GameBean) getProcessBean() ;
		settingsBean = (SettingsBean) getSettingsBean() ;

	}

	private SettingsBean getSettingsBean() {
		return (SettingsBean) ModelStore.getSettingsBean();
	}

	private GameBean getProcessBean() {
		return (GameBean) ModelStore.getSelectedGame();
	}

	@Override
	public void createPartControl(Composite parent) {
		
		parent = WidgetCreator.formatComposite(parent);

		Group group =  WidgetCreator.createGroup(parent, Messages.view_cover,2);
		
		Image image = new Image( GuiUtils.getDisplay(),CoverConstants.NOIMAGE);
		cover = WidgetCreator.createImage(group, 180, 255);
//		disk.setSize(50,50);
		cover.setImage(image);
		
		image = new Image( GuiUtils.getDisplay(),CoverConstants.NOIMAGE3D);
		cover3d = WidgetCreator.createImage(group, 180, 255);
		cover3d.setSize(50,50);
		cover3d.setImage(image);
		
		group = WidgetCreator.createGroup(parent, Messages.view_cover_disc);
		
		image = new Image( GuiUtils.getDisplay(),CoverConstants.NODISC);
		disk = WidgetCreator.createImage(group, 180,180);
//		disk.setSize(50,50);
		disk.setImage(image);
		
		WidgetCreator.createComposite(parent); //FILLER
		
		addHandlerUpdate(cover);
		addHandlerUpdate(cover3d);
		addHandlerUpdate(disk);
//		progressBar = WidgetCreator.createProgressBar(parent);
	
	}

	@Override
	public void setFocus() {
	}

	private void addHandlerUpdate(Button button) {
		button.addSelectionListener(new UpdateCoverListener(CoreConstants.VIEW_COVER_ID,processBean));	
	}

//	public ProgressBar getProgressBar() {
//		return progressBar;
//	}
//
//	public void setProgressBar(ProgressBar progressBar) {
//		this.progressBar = progressBar;
//	}

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
