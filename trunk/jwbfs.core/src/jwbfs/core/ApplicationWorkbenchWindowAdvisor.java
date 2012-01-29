
package jwbfs.core;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import jwbfs.model.ModelStore;
import jwbfs.model.utils.ConfigUtils;
import jwbfs.model.utils.CoreConstants;
import jwbfs.model.utils.Decode;
import jwbfs.model.utils.PlatformUtils;
import jwbfs.ui.ContextActivator;
import jwbfs.ui.utils.CoverUtils;
import jwbfs.ui.utils.GuiUtils;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.eclipse.update.configuration.IConfiguredSite;
import org.eclipse.update.configuration.ILocalSite;
import org.eclipse.update.core.IFeatureReference;
import org.eclipse.update.core.ISite;
import org.eclipse.update.core.SiteManager;
import org.eclipse.update.operations.IInstallFeatureOperation;
import org.eclipse.update.operations.IUninstallFeatureOperation;
import org.eclipse.update.operations.OperationsManager;
import org.osgi.framework.Version;
@SuppressWarnings("deprecation")
public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

	public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
		super(configurer);
	}

	public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer) {
		return new ApplicationActionBarAdvisor(configurer);
	}

	public void preWindowOpen() {
		
		ConfigUtils.initConfigFile();
		new ModelStore(); //init model
		
		IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
		int x = Integer.parseInt(System.getProperty("window.x"));
		int y = Integer.parseInt(System.getProperty("window.y"));
		configurer.setInitialSize(new Point(x, y));
		configurer.setShowMenuBar(true);
//		configurer.setShowCoolBar(true);
//		configurer.setShowPerspectiveBar(true);
		configurer.setShowStatusLine(false);
		configurer.setTitle("jwbfs - a wbfs_file wrapper");
		
		configurer.setShowProgressIndicator(true);
	}

	@Override
	public void postWindowClose() {
		ConfigUtils.saveConfigFile();
		ConfigUtils.saveDiskConfigFile("configs"+File.separatorChar+Decode.decodeFileName(CoreConstants.VIEW_DISK_1_ID));
		ConfigUtils.saveDiskConfigFile("configs"+File.separatorChar+Decode.decodeFileName(CoreConstants.VIEW_DISK_2_ID));
		ConfigUtils.saveDiskConfigFile("configs"+File.separatorChar+Decode.decodeFileName(CoreConstants.VIEW_DISK_3_ID));
		ConfigUtils.saveDiskConfigFile("configs"+File.separatorChar+Decode.decodeFileName(CoreConstants.VIEW_DISK_4_ID));
		ConfigUtils.saveDiskConfigFile("configs"+File.separatorChar+Decode.decodeFileName(CoreConstants.VIEW_DISK_5_ID));
		ConfigUtils.saveDiskConfigFile("configs"+File.separatorChar+Decode.decodeFileName(CoreConstants.VIEW_DISK_6_ID));
	}

	@Override
	public void postWindowOpen() {

		//TODO update exception catch 
		try{
			updateApp();
		}catch (Exception e) {
			System.out.println("Update error!");
			e.printStackTrace();
		}

		Set<String> disksViewsID = ModelStore.getDisks().keySet();
		Iterator<String> it = disksViewsID.iterator();
		int counter = 0;
		int numDisks = ModelStore.getDisks().size();
//		//switch to correct perspective based on num disks
//		LinkedHashMap<String,String> parametri = new LinkedHashMap<String,String>();
//		parametri.put("numDisks",String.valueOf(numDisks));
//		GuiUtils.executeParametrizedCommand(CoreConstants.COMMAND_DISKS_PERSPECTIVE,parametri,null);
		
		//refresh games list for every disks
		while (it.hasNext()) {

				String diskViewID = (String) it.next();
			if(numDisks > counter){
				if(ModelStore.getDisk(diskViewID).getDiskPath().trim().equals("")){ 
					continue;
				}

				LinkedHashMap<String,String>  parametri = new LinkedHashMap<String,String>();
				parametri.put("diskID",diskViewID);
				if(counter == 0){
					GuiUtils.executeParametrizedCommand(CoreConstants.COMMAND_REFRESH_FIRST_DISK_ID,parametri,null);
				}else{
					GuiUtils.executeParametrizedCommand(CoreConstants.COMMAND_GAMELIST_UPDATE_ID,parametri,null);
				}
				CoverUtils.setCoversPathFromDiskPath(diskViewID);	
			}
			counter++;
		}
		
		ContextActivator.reloadContext();
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
							System.out.println("REMOTE " + remoteFeatures[i].getVersionedIdentifier());			    		
						}

						for(int i = 0; i < localFeatures.length; i++) {			    		
							System.out.println("LOCAL " + localFeatures[i].getVersionedIdentifier());			    		
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


									System.out.println("found  "  + remoteFeatures[i].getVersionedIdentifier());
									System.out.println("local  "  + lv);
									System.out.println("remote "  + rv);
									System.out.println("compare " + rv.compareTo(lv));

									//Se versione remota è più recente di locale
									if (rv.compareTo(lv)>0){

										if((!updateNowAnswered)){
											boolean dontInstall = 
												!GuiUtils.showConfirmDialog("Trovati nuovi aggiornamenti, installare ora?");
											updateNowAnswered = true;
											if(dontInstall){
												return;
											}
										}


										//			    						featureUpdatedInstalled.add(remoteFeatures[i].getVersionedIdentifier().getIdentifier());

										//			    						GuiUtils.showInfo("Nuova Versione Feature : " +
										//			    								remoteFeatures[i].getVersionedIdentifier(), "Nuova Feature");

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

								//			    				featureNewInstalled.add(remoteFeatures[i].getVersionedIdentifier().getIdentifier());

								//			    				GuiUtils.showInfo("Nuova Feature : " +remoteFeatures[i].getVersionedIdentifier(),"Nuova Feature");

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

								//			    				featureRemoved.add(remoteFeatures[i].getVersionedIdentifier().getIdentifier());

								//			    				GuiUtils.showInfo("Feature da rimuovere: " +localFeatures[i].getVersionedIdentifier(),"Feature da rimuovere");

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


							//Salva il LOCALSITE
							ls.save();

							GuiUtils.showInfo("Changelog:\n"
									+"installati:\n"
									+getFeatureList(featureInstalled)+"\n"
									+"rimossi:\n"
									+getFeatureList(featureRemoved),"Info",SWT.HELP);

							PlatformUI.getWorkbench().restart();

						} else {

							//    						GuiUtils.showInfo("Non sono presenti aggiornamenti","Info");

							monitor.setTaskName("Non sono presenti aggiornamenti");

						}

					} catch (MalformedURLException e) {

						GuiUtils.showError("Errore nell'aggiornamento del software : \n"+e.getMessage());


					} catch (CoreException e) {

						GuiUtils.showError("Errore nell'aggiornamento del software : \n"+e.getMessage());
					}catch (Exception e) {

						GuiUtils.showError("Errore nell'aggiornamento del software : \n"+e.getMessage());

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
			GuiUtils.showError("Non è disponibile il server per la verifica degli aggiornamenti.");
	
			return false;
		} catch (IOException e1) {
			GuiUtils.showError("Non è disponibile il server per la verifica degli aggiornamenti.");

			return false;
		}
		return true;
	}

	private void updateApp() {


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
			boolean flagUpdate = System.getProperty("wbfs.update").equals("true") && !isDevelopment();

			if (flagUpdate) {

				//				if(GuiUtils.showConfirmDialog("Cercare aggiornamenti?")){

				updateServer = System.getProperty("wbfs.update.server");

				update(updateServer);
				//				}

			}


	}
	private boolean isDevelopment() {

		return new File(PlatformUtils.getRoot()+"noUpdate").exists();
	}
}
