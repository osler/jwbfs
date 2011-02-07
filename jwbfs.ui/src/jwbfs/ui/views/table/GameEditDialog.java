package jwbfs.ui.views.table;

import jwbfs.model.beans.GameBean;
import jwbfs.ui.views.WidgetCreator;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

public class GameEditDialog extends Dialog {

	public GameBean model = new GameBean();
	protected GameEditDialog(Shell parentShell, GameBean model) {
		super(parentShell);
		this.model = model;
		
	}
	@Override
	protected Control createDialogArea(Composite parent) {
	
		Composite main = WidgetCreator.createComposite(parent);
		
		Group editGroup = WidgetCreator.createGroup(main, "Game Info Edit", 2);
		
		WidgetCreator.createLabel(editGroup, "Game Title");
		
		WidgetCreator.createCombo((Composite)editGroup, 
				model.getGameAlternativeTitlesAsArray(), 
				model, 
				"title",
				true);
		
		return main;
	}
	
	

}
