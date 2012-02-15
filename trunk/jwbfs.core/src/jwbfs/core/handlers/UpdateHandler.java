package jwbfs.core.handlers;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jwbfs.core.Activator;
import jwbfs.model.ModelStore;
import jwbfs.model.utils.CoreConstants;
import jwbfs.model.utils.PlatformUtils;
import jwbfs.ui.handlers.JwbfsAbstractHandler;
import jwbfs.ui.utils.GuiUtils;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.update.configuration.IConfiguredSite;
import org.eclipse.update.configuration.ILocalSite;
import org.eclipse.update.core.IFeatureReference;
import org.eclipse.update.core.ISite;
import org.eclipse.update.core.SiteManager;
import org.eclipse.update.operations.IInstallFeatureOperation;
import org.eclipse.update.operations.IUninstallFeatureOperation;
import org.eclipse.update.operations.OperationsManager;
import org.osgi.framework.Version;

/**
 * Check site and update jwbfs 
 * @author Luca Mazzilli
 *
 */
@SuppressWarnings("deprecation")
public class UpdateHandler extends JwbfsAbstractHandler {
	public static final String ID = "createShortcut"; //$NON-NLS-1$


	ILog logger = Activator.getDefault().getLog(); 
	
	private boolean manualUpdate = true;


	private boolean restart = false;
	
	@Override
	public Object executeJwbfs(ExecutionEvent event) throws Exception {

		String param = event.getParameter(CoreConstants.PARAM_CONFIRM);
		manualUpdate = param!=null && param.trim().equals("true"); //$NON-NLS-1$
		
		
		try{
			updateApp();
		}catch (Exception e) {
			System.out.println("Update error!");
			e.printStackTrace();
			GuiUtils.showErrorWithSaveFile("Update error ", e, true);
			return  Status.CANCEL_STATUS;
		}
		

		return Status.OK_STATUS;
	}

	private void updateApp() {
		
		if(PlatformUtils.isDevelopment()){
			IStatus status = new Status(IStatus.ERROR,Activator.PLUGIN_ID,"No update when in development mode.");
			logger.log(status); 
			return;
		}

		/*
		 * caricamento delle proprieta' dal file ini
		 */
//		String nomeFileProps = "wbfs.ini";
		String updateServer  = null;

//			Properties props = new Properties();
//
//			Bundle bundle = Platform.getBundle(CoreConstants.BUNDLE_CORE);
//
//			props.load(bundle.getEntry("/" + nomeFileProps).openStream());


			// flag per l'update
			boolean flagUpdate = System.getProperty("wbfs.update").equals("true");

			if (flagUpdate) {

	
				updateServer = ModelStore.getSettingsBean().getUpdateSite();
				
				IStatus status = new Status(IStatus.INFO,Activator.PLUGIN_ID,"Starting update on " +updateServer);
				logger.log(status); 
				update(updateServer);

				if(restart ){
					GuiUtils.showInfo("jwbfs will now restart!", SWT.ICON_INFORMATION);
					//restart app
					PlatformUI.getWorkbench().restart();
				}
			}

	}
	
	

	private void update(String updateServer) {

		final String site = updateServer;
	
		if(!checkUpdateSite(updateServer)){
			return;
		}

		try {
		
			IRunnableWithProgress op = new IRunnableWithProgress (){
					

				@SuppressWarnings({ "rawtypes" })
				public void run(IProgressMonitor monitor) throws 

				InvocationTargetException, InterruptedException {

					try {
	
						//Sito remoto
						ISite remotes = SiteManager.getSite(
								new URL(site)
								, 
								monitor);

						//feature remote
						IFeatureReference [] remoteFeatures 
						= remotes.getFeatureReferences();

						//sito Locale
						ILocalSite ls = SiteManager.getLocalSite();

						IConfiguredSite localSite = 
							ls.getCurrentConfiguration().getConfiguredSites()[0];

						//feature Locali
						IFeatureReference [] 
						                   localFeatures = localSite.getConfiguredFeatures();
						//Istanzio liste oggetti da installare e disinstallare 
						List<IInstallFeatureOperation> installOps 	= new ArrayList<IInstallFeatureOperation> ();
						List<IUninstallFeatureOperation> disinstallOps 	= new ArrayList<IUninstallFeatureOperation> ();

						for(int i = 0; i < remoteFeatures.length; i++) {		
							
							String msg = "REMOTE " + remoteFeatures[i].getVersionedIdentifier();
							IStatus status = new Status(IStatus.INFO,Activator.PLUGIN_ID, msg);
							logger.log(status);
							System.out.println(msg);			    		
						}

						for(int i = 0; i < localFeatures.length; i++) {			    		
							
							String msg = "LOCAL " + localFeatures[i].getVersionedIdentifier();
							IStatus status = new Status(IStatus.INFO,Activator.PLUGIN_ID, msg);
							logger.log(status);
							System.out.println(msg);	
						}

						boolean updateNowAnswered = false;

						//Scorre ogni feature remotatrue
						for(int i = 0; i < remoteFeatures.length; i++) {

							//Recupera versione feature remota
							Version rv = 
								new Version(
										remoteFeatures[i].
										getVersionedIdentifier().
										getVersion().
										toString()
								);

							boolean found = false;		    			

							//Scorre ogni feature locale
							for (int j = 0; j < localFeatures.length; j++) {

								//Recupera versione feature locale
								Version lv = 
									new Version(
											localFeatures[j].
											getVersionedIdentifier().getVersion().
											toString()
									);

								//controlla se stessa versone feature Remoto e Locale
								if (remoteFeatures[i].getVersionedIdentifier().getIdentifier().equals(
										localFeatures[j].getVersionedIdentifier().getIdentifier())){

									//trovata nuova versione 
									found = true;
									
									String msg = "found  "  + remoteFeatures[i].getVersionedIdentifier()+"\n"+
												"local  "  + lv+"\n"+
												"remote "  + rv+"\n"+
												"compare " + rv.compareTo(lv);
									

									IStatus status = new Status(IStatus.INFO,Activator.PLUGIN_ID, msg);
									logger.log(status);
									System.out.println(msg);

									//Se versione remota è più recente di locale
									if (rv.compareTo(lv)>0){
										
										//if needs a confirm
										if(manualUpdate){
											
											status = new Status(IStatus.INFO,Activator.PLUGIN_ID, "manual update. Showing confirm.");
											logger.log(status);
											System.out.println(msg);
											
											if((!updateNowAnswered)){
												boolean dontInstall = 
													!GuiUtils.showConfirmDialog("New updates found. Install now?");
												updateNowAnswered = true;
												if(dontInstall){
													return;
												}
											}
										}


										//Aggiungi a Lista installazione nuova versione
										installOps.add(			    								
												OperationsManager.getOperationFactory()
												.createInstallOperation(
														localSite,
														remoteFeatures[i].getFeature(
																monitor
														), null, null, null)
										);

										//Aggiungi a Lista disinstallazione vecchia versione
										disinstallOps.add(			    								
												OperationsManager.getOperationFactory()
												.createUninstallOperation(				    								
														localSite,
														localFeatures[j].getFeature(
																monitor
														))
										);

									}
								}
							}

							//Se non trova una nuova versione allora è nuova Feature
							if (!found){

								//Aggiungi a Lista installazione
								installOps.add(			    								
										OperationsManager.getOperationFactory()
										.createInstallOperation(
												localSite,
												remoteFeatures[i].getFeature(
														monitor
												), null, null, null)
								);			    				

							}
						}

						/////////////////

						
						//Scorro le features locali
						for(int i = 0; i < localFeatures.length; i++) {


							boolean found = false;

							//scorro feature remote
							for (int j = 0; j < remoteFeatures.length; j++) {

								//controlla se stessa versone feature Remoto e Locale
								if (localFeatures[i].getVersionedIdentifier().getIdentifier().equals(
										remoteFeatures[j].getVersionedIdentifier().getIdentifier())){
									//Stessa feature
									found = true;


								}
							}

							//Se non è stessa feature
							if (!found){


								/*installOps.add(			    								
		    							OperationsManager.getOperationFactory()
		    							.createInstallOperation(
		    								localSite,
		    								remoteFeatures[i].getFeature(
		    								monitor
		    								), null, null, null)
		    						);			    				
								 */

								//Aggiungo a lista da disinstallare
								disinstallOps.add(			    								
										OperationsManager.getOperationFactory()
										.createUninstallOperation(				    								
												localSite,
												localFeatures[i].getFeature(
														monitor
												))
								);

							}

						}


						////////////////

						ArrayList<String> featureInstalled = new ArrayList<String>();
						ArrayList<String> featureRemoved = new ArrayList<String>();

						String msg = "processin features to install";
						IStatus status = new Status(IStatus.INFO,Activator.PLUGIN_ID, msg);
						logger.log(status);
						System.out.println(msg);

						//Se la lista delle installazioni  o delle disinstallazioni presenta degli oggetti
						if (installOps.size()> 0 ||disinstallOps.size() > 0) {

							//Scorri la lista installazioni
							for (Iterator iter = installOps.iterator(); iter.hasNext();) {

								//Installa oggetto
								IInstallFeatureOperation op = (IInstallFeatureOperation) 

								iter.next();

								System.out.println("INSTALL " 
										+ op.getFeature().getVersionedIdentifier().getIdentifier());

								op.execute(monitor, null);

								featureInstalled.add(op.getFeature().getVersionedIdentifier().getIdentifier() 
										+ " ver: "+ op.getFeature().getVersionedIdentifier().getVersion());			    				
							}


							//Scorri la lista disinstallazioni
							for (Iterator iter = disinstallOps.iterator(); iter.hasNext();) {

								//Disinstalla oggetto
								IUninstallFeatureOperation op = 
									(IUninstallFeatureOperation) 
									iter.next();

								System.out.println("DISINSTALL " 
										+ op.getFeature().getVersionedIdentifier().getIdentifier());


								op.execute(monitor, null);
								featureRemoved.add(op.getFeature().getVersionedIdentifier().getIdentifier()
										+ " ver: "+ op.getFeature().getVersionedIdentifier().getVersion());

							}

							msg = "saving local site";
							status = new Status(IStatus.INFO,Activator.PLUGIN_ID, msg);
							logger.log(status);
							System.out.println(msg);

							//Salva il LOCALSITE
							ls.save();

							//TODO scrivere un file
							String content = "Feature installed:\n"
								+getFeatureList(featureInstalled)+"\n"
								+"Feature removed:\n"
								+getFeatureList(featureRemoved);
							
							PlatformUtils.createInstallReport(content);
//							GuiUtils.showInfo(content,"Info",SWT.HELP, true);

							msg = "restarting";
							status = new Status(IStatus.INFO,Activator.PLUGIN_ID, msg);
							logger.log(status);
							System.out.println(msg);
							
							restart = true;

						} else {
							monitor.setTaskName("No update avalaible");
						
							if(!manualUpdate){
								GuiUtils.showInfo("No update avalaible","Info",SWT.HELP, true);
							}
						}

					} catch (MalformedURLException e) {
						String msg = e.getMessage();
						IStatus status = new Status(IStatus.ERROR,Activator.PLUGIN_ID, msg, e);
						logger.log(status);
						System.out.println(msg);
						GuiUtils.showErrorWithSaveFile("Error updating: \n"+e.getMessage(), e, true);
					} catch (CoreException e) {
						String msg = e.getMessage();
						IStatus status = new Status(IStatus.ERROR,Activator.PLUGIN_ID, msg, e);
						logger.log(status);
						System.out.println(msg);
						GuiUtils.showErrorWithSaveFile("Error updating: \n"+e.getMessage(), e, true);
					}catch (Exception e) {
						String msg = e.getMessage();
						IStatus status = new Status(IStatus.ERROR,Activator.PLUGIN_ID, msg, e);
						logger.log(status);
						System.out.println(msg);
						GuiUtils.showErrorWithSaveFile("Error updating: \n"+e.getMessage(), e, true);
					}
					
				}

				private String getFeatureList(ArrayList<String> featureList) {

					String list = "";

					for (Iterator<String> iterator = featureList.iterator(); iterator
					.hasNext();) {
						String string = iterator.next();
						list = list + string + "\n";
					}

					return list;
				}
			};

			Display display =  Display.getCurrent();

			ProgressMonitorDialog pmd = 
				new ProgressMonitorDialog(display.getActiveShell());			  

			pmd.run(true, false, op);

		} catch (InvocationTargetException e) {

			e.printStackTrace();

		} catch (InterruptedException e) {

			e.printStackTrace();

		}    	



	}

	private boolean checkUpdateSite(String updateServer) {
		try {
		    HttpURLConnection.setFollowRedirects(false);
		    HttpURLConnection con;
			con = (HttpURLConnection) new URL(updateServer+"/site.xml").openConnection();
			
		    con.setRequestMethod("HEAD");
//		    con.setReadTimeout(2000);//TIMEOUT
		    
			int resp = con.getResponseCode();
			if(resp != HttpURLConnection.HTTP_OK){
				throw new MalformedURLException();
			}
		} catch (MalformedURLException e1) {
			String msg = "Non è disponibile il server per la verifica degli aggiornamenti. +" +updateServer;
			GuiUtils.showError(msg, true);
			IStatus status = new Status(IStatus.INFO,Activator.PLUGIN_ID, msg);
			logger.log(status); 
	
			return false;
		} catch (IOException e1) {
			String msg = "Non è disponibile il server per la verifica degli aggiornamenti. +" +updateServer;
			GuiUtils.showError(msg, true);
			IStatus status = new Status(IStatus.INFO,Activator.PLUGIN_ID, msg);
			logger.log(status); 
			
			return false;
		}
		return true;
	}
}
