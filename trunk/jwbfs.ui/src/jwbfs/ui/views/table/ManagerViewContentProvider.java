package jwbfs.ui.views.table;

import java.util.List;

import jwbfs.model.ModelStore;
import jwbfs.model.beans.GameBean;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;


public class ManagerViewContentProvider implements IStructuredContentProvider {

	private String viewID;
	
	public ManagerViewContentProvider(String viewID){
		this.viewID = viewID;
	}

	public Object[] getElements(Object inputElement) {
		if (inputElement == null)
			return new Object[0];

		if (ModelStore.getGames(viewID).size() == 0)
			return new Object[0];

		return ModelStore.getGames(viewID).toArray();
	}

	public void dispose() {
	}

	@SuppressWarnings("unchecked")
	public void inputChanged(Viewer viewer, Object oldInput,final Object newInput) {

		if (newInput == null) return;
		if (!(newInput instanceof ModelStore)) return;
		
		ModelStore.setGames(viewID, (List<GameBean>) newInput);
	}
}
