package jwbfs.ui.views.table;

import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;

public class ManagerViewTableCellActivationStrategy extends
		ColumnViewerEditorActivationStrategy {


    public ManagerViewTableCellActivationStrategy(ColumnViewer viewer) {
		super(viewer);
	}

	protected boolean isEditorActivationEvent(
                    ColumnViewerEditorActivationEvent event) {
		int colIndex = ((ViewerCell)event.getSource()).getColumnIndex();
		switch (colIndex) {
		case 0:  return event.eventType == ColumnViewerEditorActivationEvent.TRAVERSAL
                            || event.eventType == ColumnViewerEditorActivationEvent.MOUSE_CLICK_SELECTION
                            || (event.eventType == ColumnViewerEditorActivationEvent.KEY_PRESSED && event.keyCode == SWT.CR)
                            || event.eventType == ColumnViewerEditorActivationEvent.PROGRAMMATIC;

		case 2:  return event.eventType == ColumnViewerEditorActivationEvent.TRAVERSAL
							|| event.eventType == ColumnViewerEditorActivationEvent.MOUSE_DOUBLE_CLICK_SELECTION
							|| (event.eventType == ColumnViewerEditorActivationEvent.KEY_PRESSED && event.keyCode == SWT.CR)
							|| event.eventType == ColumnViewerEditorActivationEvent.PROGRAMMATIC;

		default:return event.eventType == ColumnViewerEditorActivationEvent.TRAVERSAL
							|| event.eventType == ColumnViewerEditorActivationEvent.MOUSE_DOUBLE_CLICK_SELECTION
							|| (event.eventType == ColumnViewerEditorActivationEvent.KEY_PRESSED && event.keyCode == SWT.CR)
							|| event.eventType == ColumnViewerEditorActivationEvent.PROGRAMMATIC;

		}

    }

}
