package jwbfs.ui.views.table;

import jwbfs.i18n.Messages;
import jwbfs.model.beans.GameBean;

import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Item;

public class GameCellModifiers implements ICellModifier {

	private TableViewer viewer;

	public GameCellModifiers(TableViewer viewer) {
		this.viewer = viewer;
	}

	public boolean canModify(Object element, String property) {

		if (property.equals(Messages.view_gamelist_column_selection)){
			return true;
		}
		if (property.equals(Messages.view_gamelist_column_name)){
			return true;
		}

		return false;
	}

	public Object getValue(Object element, String property) {
		GameBean p = (GameBean) element;


		Object result = null;

		int columnIndex = getColumnIndex(property);


		switch (columnIndex) {
		case 0 : // ID
			result = new Boolean(p.isSelected());
			break;
		case 1 : // ID
		result = p.getId();
		break;
		case 2 : // TITOLO
//		result = GameUtils.indexOfGameName(p.getTitle(),p.getGameAlternativeTitlesAsArray());
		result = p.getTitle();
		break;
		case 3 : // SIZE
			result = p.getScrubGb();
			break;
		}

		return result;
	}

	private int getColumnIndex(String property) {
		int columnIndex = -1;
		String[] columns = (String[]) viewer.getColumnProperties();

		for (int i = 0; i < columns.length; i++) {
			String string = columns[i];
			if(string.trim().equals(property)){
				columnIndex = i;
				break;
			}
		}
		return columnIndex;
	}

	public void modify(Object element, String property, Object value) {
		if (element instanceof Item) element = ((Item) element).getData();

		GameBean p = (GameBean) element;
		if (property.equals(Messages.view_gamelist_column_selection))
			p.setSelected((Boolean) value);
		else if (property.equals(Messages.view_gamelist_column_id))
			p.setId((String) value);
		else if (property.equals(Messages.view_gamelist_column_name)){
//			String title = GameUtils.decodeGameName((Integer) value,p.getGameAlternativeTitlesAsArray());
			String title = (String) value;
			p.setTitle(title);
		}
		else if (property.equals(Messages.view_gamelist_column_size))
			p.setScrubGb((String) value);


		viewer.refresh();
	}
}