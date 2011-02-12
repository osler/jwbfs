package jwbfs.ui.views.table;

import jwbfs.model.beans.GameBean;
import jwbfs.model.utils.FileUtils;
import jwbfs.ui.utils.GuiUtils;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;

public class GameTitleCellEditor extends DialogCellEditor {
	
    private boolean dialogOpen =false;
	private Label defaultLabel;

	public GameTitleCellEditor(Table table, int readOnly) {
		super(table, readOnly);
	}

	protected Button createButton (final Composite parent) {
        Button button = new Button(parent, SWT.DOWN);
        button.setText("...");

        button.addFocusListener(new FocusAdapter() {


			public void focusLost (final FocusEvent e) {
                if (!dialogOpen) {
                    fireCancelEditor();
                }
            }
        });

        return button;
    }

    @Override
	protected Control createContents(Composite cell) {
    	defaultLabel = new Label(cell, SWT.CENTER);
        defaultLabel.setFont(cell.getFont());
        defaultLabel.setBackground(cell.getBackground());
        return defaultLabel;
	}

    protected void updateContents(Object value) {
        if (defaultLabel == null) {
			return;
		}

        String text = "";
        if (value != null) {
			text = value.toString();
		}
        defaultLabel.setText(text);
    }
    
	protected Object openDialogBox (final Control cellEditorWindow) {
    	
    	GameBean selected = GuiUtils.getGameSelectedFromTableView();
    	
        final Dialog dialog = new GameEditDialog(new Shell(),selected);
        dialogOpen = true;
        
        String result = selected.getTitle();
        String oldText = result;
        final boolean ok = (dialog.open() == WizardDialog.OK);
        if(ok){
        	result =selected.getTitle();
        	
			if(!oldText.trim().equals(result.trim())){
				FileUtils.updateGameTxtFile(selected.getId(),result.trim());
			}
        	
        }

        dialogOpen = false;
        return result;
    }

}
