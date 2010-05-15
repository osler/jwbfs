package jwbfs.ui.views.table;

import java.util.List;

import jwbfs.model.Model;
import jwbfs.model.beans.GameBean;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;


public class ManagerViewContentProvider implements IStructuredContentProvider {


	public Object[] getElements(Object inputElement) {
		if (inputElement == null)
			return new Object[0];

		if (Model.getGames().size() == 0)
			return new Object[0];

		return Model.getGames().toArray();
	}

	public void dispose() {
	}

	public void inputChanged(Viewer viewer, Object oldInput,final Object newInput) {

		if (newInput == null) return;
		if (!(newInput instanceof Model)) return;
		
		Model.setGames( (List<GameBean>) newInput);
	}
}
