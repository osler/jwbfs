package jwbfs.ui.views;

import jwbfs.model.beans.ModelObject;
import jwbfs.model.beans.SettingsBean;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;


public class WidgetCreator {


	public static ProgressBar createProgressBar(Composite composite) {
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.verticalAlignment = GridData.CENTER;
		ProgressBar progressBar = new ProgressBar(composite, SWT.NONE);
		progressBar.setLayoutData(gridData);
		return progressBar;
	}

	public static Composite formatComposite(Composite composite) {

		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.verticalAlignment = GridData.FILL;

		composite.setLayout(new GridLayout());
		composite.setLayoutData(gridData);

		return composite;
	}
	
	public static Composite createComposite(Composite parent) {

		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(gridData);

		return composite;
	}
	
	public static Composite createComposite(TabFolder parent) {

		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.verticalAlignment = GridData.FILL;
		
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(gridData);

		return composite;
	}
	

	public static Group createGroup(Composite composite, String title) {
		return createGroup(composite,title,2);
	}
	
	public static Group createGroup(Composite composite, String title,int numColumns) {

		GridData gridGrp = new GridData();
		gridGrp.horizontalAlignment = GridData.FILL;
		gridGrp.grabExcessHorizontalSpace = true;
		gridGrp.verticalAlignment = GridData.CENTER;
		Group group1 = new Group(composite, SWT.NONE);
		group1.setLayout(new GridLayout(numColumns, false));
		group1.setLayoutData(gridGrp);
		group1.setText(title);

		return group1;
	}
	

	
	
	public static Combo createCombo(Composite composite, String[] items, final SettingsBean bean, String valueToBind) {
		
		GridData gridBtn = new GridData();
		gridBtn.horizontalAlignment = GridData.CENTER;
		gridBtn.widthHint = 200;
		gridBtn.horizontalSpan = 1;
		gridBtn.verticalAlignment = GridData.CENTER;

		Combo combo = new Combo(composite, SWT.READ_ONLY);
		combo.setItems(items);
		combo.setLayoutData(gridBtn);
		
		DataBindingContext dbc = new DataBindingContext();
		 dbc.bindValue(SWTObservables.observeSelection(combo),
			 BeansObservables.observeValue(bean,valueToBind),null,null);
		 
		return combo;
	}

	public static Shell createShell() {
		Shell sShell = new Shell();
		sShell.setText("Shell");
		sShell.setSize(new Point(600, 400));
		sShell.setLayout(new GridLayout());
		return sShell;
	}
	
	public static Text createText(Composite parent, boolean enabled,ModelObject bean, String valueToBind){
		return createText(parent, enabled, bean, valueToBind, 1);
	}
	

	public static Text createText(Composite parent, boolean enabled,ModelObject bean, String valueToBind,int horizontalSpan){
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = horizontalSpan;
		gridData.verticalAlignment = GridData.CENTER;

		Text txtFile = new Text(parent, SWT.NONE);
		txtFile.setText("none");
		txtFile.setEnabled(enabled);
		txtFile.setLayoutData(gridData);

		if(bean!= null){
			DataBindingContext dbc = new DataBindingContext();
			dbc.bindValue(SWTObservables.observeText(txtFile,SWT.Modify),
					BeansObservables.observeValue(bean,valueToBind),null,null);
		}else{
			txtFile.setText(valueToBind);
		}
		
		return txtFile;	
	}
	
	public static Label createLabel(Composite parent, String text){
		return createLabel(parent, text, 1);
	}
	
	public static Label createLabel(Composite parent, String text,int horizontalSpan){
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = horizontalSpan;
		gridData.verticalAlignment = GridData.CENTER;

		Label txtFile = new Label(parent, SWT.NONE);
		txtFile.setText(text);
		txtFile.setLayoutData(gridData);

		
		return txtFile;	
	}
	
	public static Button createCheck(Composite parent, String text, ModelObject bean, String valueToBind){
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.CENTER;
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 1;
		gridData.verticalAlignment = GridData.CENTER;

		Button radio = new Button(parent, SWT.CHECK);
		radio.setText(text);
		radio.setLayoutData(gridData);
		
		DataBindingContext dbc = new DataBindingContext();
		dbc.bindValue(SWTObservables.observeSelection(radio),
				BeansObservables.observeValue(bean,valueToBind),null,null);
		
		return radio;	
	}
	
	public static Button createRadio(Composite parent, String text, ModelObject bean, String valueToBind){
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 1;
		gridData.verticalAlignment = GridData.CENTER;

		Button radio = new Button(parent, SWT.RADIO);
		radio.setText(text);
		radio.setLayoutData(gridData);
		
		DataBindingContext dbc = new DataBindingContext();
		dbc.bindValue(SWTObservables.observeSelection(radio),
				BeansObservables.observeValue(bean,valueToBind),null,null);
		
		return radio;	
	}
	
	
	public static TabFolder createTabFolder(Composite parent){

	    TabFolder tabFolder = new TabFolder(parent, SWT.BORDER);

		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		
		tabFolder.setLayoutData(gridData);
	    tabFolder.setLayout(new GridLayout());
	    
		return tabFolder;
	}
	
	public static TabItem createTabItem(TabFolder parent,String tabName){
		      TabItem tabItem = new TabItem(parent, SWT.NULL);
		      tabItem.setText(tabName);

			return tabItem;

	}
	
	public static Button createButton(Group group, String text){
		return createButton(group, text,100);
	}

	public static Button createButton(Group group, String text,int widthHint) {
		
		GridData gridBtn = new GridData();
		gridBtn.horizontalAlignment = GridData.CENTER;
		gridBtn.widthHint = widthHint;
		gridBtn.horizontalSpan = 1;
		gridBtn.verticalAlignment = GridData.CENTER;
	
		Button button = new Button(group, SWT.NONE);
		button.setText(text);
		button.setLayoutData(gridBtn);
		
		return button;
	}
	
	public static Button createImage(Group group) {
		
		GridData gridBtn = new GridData();
		gridBtn.grabExcessHorizontalSpace = true;
		gridBtn.grabExcessVerticalSpace = true;
		gridBtn.horizontalAlignment = GridData.CENTER;
		gridBtn.verticalAlignment = GridData.CENTER;
		gridBtn.horizontalSpan = 1;
	
		Button button = new Button(group, SWT.PUSH);
		button.setLayoutData(gridBtn);
		
		return button;
	}
	

	public static Table createTable(final Composite parent, int style, String[] col,
			int[] dimensioneColonne) {
		
		TableColumnLayout layout = new TableColumnLayout();

		final Table table = new Table(parent, style | SWT.CENTER | SWT.BORDER | SWT.FULL_SELECTION);
		parent.setLayout(layout);
		
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		for (int i = 0; i < col.length; i++) {
			TableColumn colon = new TableColumn(table, SWT.CENTER);
			layout.setColumnData( colon, new ColumnWeightData( dimensioneColonne[i] ) );
			colon.setText(col[i]);
			colon.setResizable(true);
			colon.setMoveable(true);
				
		}

		return table;
	}
	
}
