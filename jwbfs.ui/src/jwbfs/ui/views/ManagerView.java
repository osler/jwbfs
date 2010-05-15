package jwbfs.ui.views;

import jwbfs.model.Model;
import jwbfs.model.beans.ModelObject;
import jwbfs.model.beans.ProcessBean;
import jwbfs.model.beans.SettingsBean;
import jwbfs.ui.listeners.FolderDialogListener;
import jwbfs.ui.listeners.UpdateGameListListener;
import jwbfs.ui.views.table.ManagerViewContentProvider;
import jwbfs.ui.views.table.ManagerViewLabelProvider;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

public class ManagerView extends ViewPart implements ISelectionChangedListener{
	
	private Table table;
	private TableViewer tv;
	
	public static final String ID = "ManagerView";

	public ManagerView() {

	}
	
	private void addHandlerFolder(Button button) {
		//TODO FolderDialog for disk path
		button.addSelectionListener(new FolderDialogListener(ID));
}
	
	

	@Override
	public void createPartControl(Composite parent) {
		
		parent = WidgetCreator.createComposite(parent);

		Group group = WidgetCreator.createGroup(parent, "Disk", 4);
		
		Label label = WidgetCreator.createLabel(group,"Path");
		Button button = WidgetCreator.createButton(group,"open");
		addHandlerFolder(button);
		label = WidgetCreator.createLabel(group,"");
		button = WidgetCreator.createButton(group,"Update List");
		addHandlerUpdate(button);
		Text text =  WidgetCreator.createText(group, false, (SettingsBean) getSettingsBean(), "diskPath",4);
		
		Composite tableComp = WidgetCreator.createComposite(parent);
		
		String[] columnsNames = {
				"ID", "Name", "Region","Size" };

		table = WidgetCreator.createTable(tableComp, SWT.Selection | SWT.FULL_SELECTION, columnsNames, 200);
		GridData data2 = new GridData(SWT.DEFAULT, table.getSize().y);
		table.setLayoutData(data2);

		tv = new TableViewer(table);
		tv.setContentProvider(new ManagerViewContentProvider());
		tv.setLabelProvider(  new ManagerViewLabelProvider());
		getSite().setSelectionProvider(tv);

		tv.addSelectionChangedListener(this);
		

		
	}

	private void addHandlerUpdate(Button button) {
		//TODO FolderDialog for disk path
		button.addSelectionListener(new UpdateGameListListener(ID));
		
	}

	@Override
	public void setFocus() {

	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		// TODO Auto-generated method stub
		if(!((SettingsBean) Model.getTabs().get(SettingsBean.INDEX)).isManagerMode()){
			((SettingsBean) Model.getTabs().get(SettingsBean.INDEX)).setManagerMode(true);
		}
//		ProcessBean sel = ((ProcessBean[])tv.getInput())[tv.getTable().getSelectionIndex()];
//		
//		Model.getTabs().put(ProcessBean.INDEX,sel);
		
	}

	private SettingsBean getSettingsBean() {
		return (SettingsBean) Model.getTabs().get(SettingsBean.INDEX);
	}

	private ProcessBean getProcessBean() {
		return (ProcessBean) Model.getTabs().get(ProcessBean.INDEX);
	}

	public TableViewer getTv() {
		return tv;
	}

	public void setTv(TableViewer tv) {
		this.tv = tv;
	}

}