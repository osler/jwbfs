package jwbfs.core.splash;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.splash.AbstractSplashHandler;

public class SplashHandler extends AbstractSplashHandler {
 
	private static final String BETA_PNG = "splash.png";
	private static final int BORDER = 10;
	private Image image;
 
	public SplashHandler() {
		super();
	}
 
	@Override
	public void init(Shell splash) {
		super.init(splash);
 
		//here you could check some condition on which decoration to show
 
//		ImageDescriptor descriptor = AbstractUIPlugin.imageDescriptorFromPlugin("jwbfs.core", BETA_PNG);
//		if (descriptor != null)
//			image = descriptor.createImage();
//		if (image !=null) {
//			final int xposition = splash.getSize().x - image.getImageData().width - BORDER;
//			final int yposition = BORDER;
//			getContent().addPaintListener (new PaintListener () {
//				public void paintControl (PaintEvent e) {
//					e.gc.drawImage (image, xposition, yposition);
//				}
//			});
//		}
	}
 
	@Override
	public void dispose() {
		super.dispose();
		if (image != null)
			image.dispose();
	}
}
