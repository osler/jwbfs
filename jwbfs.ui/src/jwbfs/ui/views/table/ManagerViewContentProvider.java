package jwbfs.ui.views.table;

import jwbfs.model.Model;
import jwbfs.model.beans.ProcessBean;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;


public class ManagerViewContentProvider implements IStructuredContentProvider {


	public Object[] getElements(Object inputElement) {
		if (inputElement == null)
			return new Object[0];

		if (Model.getGames().length == 0)
			return new Object[0];

		return Model.getGames();
	}

	public void dispose() {
	}

	public void inputChanged(Viewer viewer, Object oldInput,final Object newInput) {

		if (newInput == null) return;
		if (!(newInput instanceof Model)) return;
		
		Model.setGames((ProcessBean[]) newInput);
	}
}
