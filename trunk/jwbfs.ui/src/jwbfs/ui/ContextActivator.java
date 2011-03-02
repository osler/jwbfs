package jwbfs.ui;

import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Hashtable;
import java.util.Iterator;

import jwbfs.model.utils.CoreConstants;
import jwbfs.model.utils.Decode;
import jwbfs.ui.utils.GuiUtils;

import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.contexts.IContextActivation;
import org.eclipse.ui.contexts.IContextService;

public class  ContextActivator {
 
		private static	Hashtable<String,IContextActivation> activeContexts = new Hashtable<String,IContextActivation>();  
		
		public synchronized static Hashtable<String, IContextActivation> getActiveContexts() {
			return activeContexts;
		}

		public synchronized static void setActiveContexts(
				Hashtable<String, IContextActivation> activeContexts) {
			ContextActivator.activeContexts = activeContexts;
		}

		public static synchronized void reloadContext(){
			deactivateActiveContexts();
			activateContextForDisks();
		}
					
		public static void activateContextForDisks(){
	
			
			IContextService contextService 	= (IContextService)PlatformUI.getWorkbench().getService(IContextService.class);
			if(GuiUtils.viewExist(CoreConstants.VIEW_DISK_1_ID)){
				String contextID = CoreConstants.CONTEXT_DISK_1;
				IContextActivation contAct = contextService.activateContext(contextID);
				getActiveContexts().put(contextID,contAct);
			}
			if(GuiUtils.viewExist(CoreConstants.VIEW_DISK_2_ID)){
				String contextID = CoreConstants.CONTEXT_DISK_2;
				IContextActivation contAct = contextService.activateContext(contextID);
				getActiveContexts().put(contextID,contAct);
			}
			if(GuiUtils.viewExist(CoreConstants.VIEW_DISK_3_ID)){
				String contextID = CoreConstants.CONTEXT_DISK_3;
				IContextActivation contAct = contextService.activateContext(contextID);
				getActiveContexts().put(contextID,contAct);
			}
			if(GuiUtils.viewExist(CoreConstants.VIEW_DISK_4_ID)){
				String contextID = CoreConstants.CONTEXT_DISK_4;
				IContextActivation contAct = contextService.activateContext(contextID);
				getActiveContexts().put(contextID,contAct);
			}
			if(GuiUtils.viewExist(CoreConstants.VIEW_DISK_5_ID)){
				String contextID = CoreConstants.CONTEXT_DISK_5;
				IContextActivation contAct = contextService.activateContext(contextID);
				getActiveContexts().put(contextID,contAct);
			}
			if(GuiUtils.viewExist(CoreConstants.VIEW_DISK_6_ID)){
				String contextID = CoreConstants.CONTEXT_DISK_6;
				IContextActivation contAct = contextService.activateContext(contextID);
				getActiveContexts().put(contextID,contAct);
			}
			
			System.out.println("Contexts reloaded");
		}
	
		private static synchronized void deactivateActiveContexts() {

			try{

				Collection<IContextActivation>			contexts 	= getActiveContexts().values();
				Iterator<IContextActivation>			contextsIterator = contexts.iterator();
				IContextActivation 	contextActivation = null;
				String				contextId = "";
				IContextService contextService 	= (IContextService)PlatformUI.getWorkbench().getService(IContextService.class);		

				while(contextsIterator.hasNext()){

					contextActivation = (IContextActivation)contextsIterator.next();
					contextId = contextActivation.getContextId();
					if(contextActivation!=null)
						contextService.deactivateContext(contextActivation);
					getActiveContexts().remove(contextId);
				}
			}catch (ConcurrentModificationException e) {
				deactivateActiveContexts();
			}
			
		}

		public static void reloadContext(String viewID) {
			deactivateActiveContexts();
			activateContextForDisks();
			activateContextForDisks(viewID);
		}

		public static void activateContextForDisks(String viewID){

			IContextService contextService 	= (IContextService)PlatformUI.getWorkbench().getService(IContextService.class);
			String contextID = Decode.decodeContextFromView(viewID);
			IContextActivation contAct = contextService.activateContext(contextID);
			getActiveContexts().put(contextID,contAct);
		}
}

