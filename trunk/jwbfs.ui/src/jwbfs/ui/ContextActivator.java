package jwbfs.ui;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

import jwbfs.model.ModelStore;
import jwbfs.model.utils.CoreConstants;

import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.contexts.IContextActivation;
import org.eclipse.ui.contexts.IContextService;

public class ContextActivator {
 
		private static 	Hashtable<String,IContextActivation> activeContexts = new Hashtable<String,IContextActivation>();  
		
		public static void reloadContext(){
			deactivateActiveContexts();
			activateContextForDisks();
		}
					
		public static void activateContextForDisks(){
	
			int numDisks = ModelStore.getNumDisk();
			String contextID = "";
			switch (numDisks) {
			case 1:contextID = CoreConstants.CONTEXT_DISK_1;break;
			case 2:contextID = CoreConstants.CONTEXT_DISK_2;break;
			case 3:contextID = CoreConstants.CONTEXT_DISK_3;break;
			case 4:contextID = CoreConstants.CONTEXT_DISK_4;break;
			case 5:contextID = CoreConstants.CONTEXT_DISK_5;break;
			case 6:contextID = CoreConstants.CONTEXT_DISK_6;break;
			}
			
			IContextService contextService 	= (IContextService)PlatformUI.getWorkbench().getService(IContextService.class);
			IContextActivation contAct = contextService.activateContext(contextID);
			activeContexts.put(contextID,contAct);
		}
	
		private static void deactivateActiveContexts() {

			Collection<IContextActivation>			contexts 	= activeContexts.values();
			Iterator<IContextActivation>			contextsIterator = contexts.iterator();
			IContextActivation 	contextActivation = null;
			String				contextId = "";
			IContextService contextService 	= (IContextService)PlatformUI.getWorkbench().getService(IContextService.class);		
			
			while(contextsIterator.hasNext()){
				
				contextActivation = (IContextActivation)contextsIterator.next();
				contextId = contextActivation.getContextId();
				if(contextActivation!=null)
						contextService.deactivateContext(contextActivation);
				activeContexts.remove(contextId);
			}
			
			
		}

}

