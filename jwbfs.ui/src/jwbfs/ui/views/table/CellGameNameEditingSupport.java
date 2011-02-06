package jwbfs.ui.views.table;

import jwbfs.model.beans.GameBean;
import jwbfs.ui.utils.GameUtils;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;

public class CellGameNameEditingSupport extends EditingSupport {

	private final TableViewer viewer;

	public CellGameNameEditingSupport(TableViewer viewer) {
		super(viewer);
		this.viewer = viewer;
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		GameBean game = (GameBean) element;
		String[] names = GameUtils.getGameNames(game.getId());
		
//		gender[0] = game.getTitle();
//		gender[1] = "female";

		return new ComboBoxCellEditor(viewer.getTable(), names);
	}

	@Override
	protected boolean canEdit(Object element) {
		return true;
	}

	@Override
	protected Object getValue(Object element) {
		GameBean game = (GameBean) element;
		return game.getTitle();

	}

	@Override
	protected void setValue(Object element, Object value) {
		GameBean game = (GameBean) element;
//		if (((Integer) value) == 0) {
//			game.setGender("male");
//		} else {
//			game.setGender("female");
//		}
		viewer.refresh();
	}
	
}