package jwbfs.ui.views;


import jwbfs.model.Model;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.ViewPart;

public class CoverView extends ViewPart {

	public static final String ID = "jwbfs.ui.CoverView";

	public CoverView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		
		Model model = new Model();
		
		parent = WidgetCreator.formatComposite(parent);

		Group group =  WidgetCreator.createGroup(parent, "Game Info",3);
		Label text = WidgetCreator.createLabel(group, "last updated:");
		text = WidgetCreator.createLabel(group, "never");
		Button button = WidgetCreator.createButton(group, "update");


	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
