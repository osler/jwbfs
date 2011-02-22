package jwbfs.ui.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import jwbfs.model.beans.GameBean;
import jwbfs.model.utils.CoreConstants;
import jwbfs.model.utils.CoverConstants;
import jwbfs.model.utils.PlatformUtils;
import jwbfs.ui.ContextActivator;
import jwbfs.ui.views.CoverView;
import jwbfs.ui.views.manager.ManagerView;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IParameter;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.Parameterization;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveRegistry;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class GuiUtils {

	public static IWorkbench getWorkbench(){
		return PlatformUI.getWorkbench();		
	}

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
	
	public static void showError(final String message, boolean async){
		if(async){
			Display.getDefault().asyncExec(								
					new Runnable(){
						public void run() {
							GuiUtils.showError(message);
						}
					}
			);	
		}else{
			showError(message);
		}

	}
	
	public static void showInfo(final String message, final String text, final int style,boolean async) {

		if(async){
			Display.getDefault().asyncExec(								
					new Runnable(){
						public void run() {
							GuiUtils.showInfo(message,text,style);
						}
					}
			);	
		}else{
			showInfo(message,text,style);
		}
	}

	public static void showInfo(String message, String text, int style) {
		MessageBox msg = new MessageBox(new Shell(), style);
		msg.setMessage(message);
		msg.setText(text);
		msg.open();

	}

	public static TableViewer getManagerTableViewer(String viewID) {
		return 	((ManagerView)GuiUtils.getView(viewID)).getTv();
	}
	
	public static boolean showConfirmDialog(String message) {
		boolean ret =  MessageDialog.openConfirm(new Shell(), "Confirm", message);

		return ret;
	}

	private static boolean confirm = false;
	
	public static boolean showConfirmDialog(final String message,boolean async) {
		if(async){
			Display.getDefault().asyncExec(								
					new Runnable(){
						public void run() {
							confirm = showConfirmDialog(message);				
						}
					});
			
					return confirm;
		}
	
		return showConfirmDialog(message);
	}

	public static void setCover(String coverPath, String coverCode) {
		try{

			if(coverCode.equals(CoverConstants.COVER_2D)){
				setCover(coverPath);
			}
			if(coverCode.equals(CoverConstants.COVER_3D)){
				setCover3d(coverPath);
			}
			if(coverCode.equals(CoverConstants.COVER_DISC)){
				setCoverDisc(coverPath);
			}
			//		if(coverCode.equals(CoverConstants.COVER_FULLBOX)){
			//			setCover(coverPath);
			//		}

		}catch (SWTException e) {
			setDefaultCovers();
		}
	}
	
	private static void setCover(String coverPath) {

		System.out.println("Setting cover:");
		System.out.println(coverPath);
		Image img = new Image(GuiUtils.getDisplay(),coverPath);
		((CoverView) GuiUtils.getView(CoreConstants.VIEW_COVER_ID)).getCover().setImage(img);


	}

	private static void setCover3d(String coverPath) {

		System.out.println("Setting cover3d:");
		System.out.println(coverPath);
		Image img = new Image(GuiUtils.getDisplay(),coverPath);
		((CoverView) GuiUtils.getView(CoreConstants.VIEW_COVER_ID)).getCover3d().setImage(img);


	}

	private static void setCoverDisc(String coverPath) {
		Image img = null;
		try{
			System.out.println("Setting cover disc:");
			System.out.println(coverPath);
			img =  new Image(GuiUtils.getDisplay(),coverPath);

		}catch (Exception e) {
			if(coverPath.toLowerCase().contains("2d")){
				coverPath = CoverConstants.NOIMAGE;
			}else if(coverPath.toLowerCase().contains("3d")){
				coverPath = CoverConstants.NOIMAGE3D;
			}else if(coverPath.toLowerCase().contains("disc")){
				coverPath = CoverConstants.NODISC;
			}

			img =  new Image(GuiUtils.getDisplay(),coverPath);
		}
		((CoverView) GuiUtils.getView(CoreConstants.VIEW_COVER_ID)).getDisk().setImage(img);

	}

	public static void setDefaultCovers() {
		GuiUtils.setCover(CoverConstants.NOIMAGE);
		GuiUtils.setCover3d(CoverConstants.NOIMAGE3D);
		GuiUtils.setCoverDisc(CoverConstants.NODISC);
	}

	public static void showError(String message) {
		MessageBox msg = new MessageBox(new Shell(), SWT.ERROR);
		msg.setMessage(message);
		msg.open();


	}

	public static void showInfo(final String message, final int style, boolean async) {
		if(async){
			Display.getDefault().asyncExec(								
					new Runnable(){
						public void run() {
							GuiUtils.showInfo(message,style);
						}
					}
			);	
		}else{
			showInfo(message, style);
		}

	}

	public static GameBean getGameSelectedFromTableView() {

		TableViewer tv = GuiUtils.getManagerTableViewer(getActiveViewID());

		ISelection selection = tv.getSelection();
		IStructuredSelection sel = (IStructuredSelection) selection;

		GameBean selectedGame = (GameBean) sel.getFirstElement(); //SOLO il primo oggetto perchè per i multipli non ha senso il rename

		return selectedGame;
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<GameBean>getGameSelectedMultipleFromTableView() {

		TableViewer tv = GuiUtils.getManagerTableViewer(getActiveViewID());

		ISelection selection = tv.getSelection();
		IStructuredSelection sel = (IStructuredSelection) selection;

		ArrayList<GameBean> multiSelectedGame = new ArrayList<GameBean>();
		for (Iterator<GameBean> iterator = sel.iterator(); iterator.hasNext();) {
			GameBean selectedGame = iterator.next();
			multiSelectedGame.add(selectedGame);
		}

		return multiSelectedGame;
	}

	public static String getActiveViewID() {
//		IPerspectiveDescriptor[] perspectives = PlatformUI.getWorkbench()
//		.getActiveWorkbenchWindow().getActivePage().getSortedPerspectives();
		
		ViewPart view = getView(CoreConstants.VIEW_DISK_1_ID);
		IPerspectiveDescriptor perspective = view.getSite().getPage().getPerspective();
		
		
		String diskSelected = null;
		try{
			diskSelected = PlatformUI.getWorkbench()
								.getActiveWorkbenchWindow()
								.getActivePage().getActivePart().getSite().getId();
		}catch(Exception e){
			//if a dialog is open
			diskSelected = PlatformUI.getWorkbench()
									.getWorkbenchWindows()[0]
			                       .getActivePage()
			                       .getActivePart()
			                       .getSite().getId();
		}
		
		return diskSelected;
	}

	/**
	 * If async return always NULL.
	 * @param commandID
	 * @param parameters
	 * @param event
	 * @param async
	 * @return
	 */
	public static Object executeCommand(
			final String commandID, final LinkedHashMap<String,String> parameters, final Event event, boolean async) {
		
		if(async){
			Display.getDefault().asyncExec(								
					new Runnable(){
						public void run() {
							executeParametrizedCommand(commandID, parameters,  event);
						}
					}
			);
			
		}else {
			return executeParametrizedCommand(commandID, parameters, event);
		}
		
		return null;
	}
	
	public static Object executeParametrizedCommand(
			String commandID,
			LinkedHashMap<String,String> parametri, Event event) {
		Command partRetrieve 	= PlatformUtils.getCommandService().getCommand(commandID);



		Parameterization[] parametrizationArray = new Parameterization[parametri.size()];

		Set<String> keys = parametri.keySet();
		try {
		for(int x = 0; x<parametri.size();x++){

			String key = (String) keys.toArray()[x];

			IParameter iPar;

				iPar = partRetrieve.getParameter(key);

			String valore = (String) parametri.get(key);

			parametrizationArray[x] = new Parameterization(iPar, valore);

		}

		ParameterizedCommand 
		parmCommand = new ParameterizedCommand(
				partRetrieve, parametrizationArray
		);


		Boolean ret = (Boolean) PlatformUtils.getHandlerService().executeCommand(parmCommand, event);
		if(ret){
			ContextActivator.reloadContext();
		}
		
		return ret;
		
		} catch (NotDefinedException e) {
			e.printStackTrace();
		}catch (ExecutionException e) {
			e.printStackTrace();
		} catch (NotEnabledException e) {
			e.printStackTrace();
		} catch (NotHandledException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public static Object executeCommand(String viewID,
			String commandID, Event event) {
		
		try{

			Boolean ret = (Boolean) PlatformUtils.getHandlerService(viewID).executeCommand(commandID, event);
			if(ret){
				ContextActivator.reloadContext();
			}
			
			return ret;
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (NotDefinedException e) {
			e.printStackTrace();
		} catch (NotEnabledException e) {
			e.printStackTrace();
		} catch (NotHandledException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * If async return always NULL.
	 * @param viewID
	 * @param commandID
	 * @param event
	 * @param async
	 * @return
	 */
	public static Object executeCommand(final String viewID,
			final String commandID, final Event event, boolean async) {
		
		if(async){
			Display.getDefault().asyncExec(								
					new Runnable(){
						public void run() {
							 executeCommand(viewID, commandID, event);
						}
					}
			);
			
		}else {
			return executeCommand(viewID, commandID, event);
		}
		
		return null;
	}

	public static String decodeDiskID(int i) {
		String diskID = "";
		switch (i) {
		case 1:diskID = CoreConstants.VIEW_DISK_1_ID;break;
		case 2:diskID = CoreConstants.VIEW_DISK_2_ID;break;
		case 3:diskID = CoreConstants.VIEW_DISK_3_ID;break;
		case 4:diskID = CoreConstants.VIEW_DISK_4_ID;break;
		case 5:diskID = CoreConstants.VIEW_DISK_5_ID;break;
		case 6:diskID = CoreConstants.VIEW_DISK_6_ID;break;
		}
		return diskID;
	}
	
	public static String decodePerspectiveID(int numDisks) {
		String diskID = "";
		switch (numDisks) {
		case 1:diskID = CoreConstants.PERSPECTIVE_DISKS_1;break;
		case 2:diskID = CoreConstants.PERSPECTIVE_DISKS_2;break;
		case 3:diskID = CoreConstants.PERSPECTIVE_DISKS_3;break;
		case 4:diskID = CoreConstants.PERSPECTIVE_DISKS_4;break;
		case 5:diskID = CoreConstants.PERSPECTIVE_DISKS_5;break;
		case 6:diskID = CoreConstants.PERSPECTIVE_DISKS_6;break;
		}
		return diskID;
	}
	
	public static ImageDescriptor getImageDescriptor(String pluginID, String path) {
		return AbstractUIPlugin.imageDescriptorFromPlugin(pluginID, path);
	}
}
