package jwbfs.ui.views;

import java.util.Iterator;

import jwbfs.i18n.Messages;
import jwbfs.model.Model;
import jwbfs.model.beans.GameBean;
import jwbfs.model.beans.SettingsBean;
import jwbfs.model.utils.CoreConstants;
import jwbfs.model.utils.FileUtils;
import jwbfs.model.utils.PlatformUtils;
import jwbfs.ui.listeners.mainView.AddButtonListener;
import jwbfs.ui.listeners.mainView.DeleteButtonListener;
import jwbfs.ui.listeners.mainView.DiskFolderSelectionListener;
import jwbfs.ui.listeners.mainView.ExportButtonListener;
import jwbfs.ui.listeners.mainView.UpdateGameListListener;
import jwbfs.ui.utils.GuiUtils;
import jwbfs.ui.views.table.GameCellModifiers;
import jwbfs.ui.views.table.GameTitleCellEditor;
import jwbfs.ui.views.table.ManagerViewContentProvider;
import jwbfs.ui.views.table.ManagerViewLabelProvider;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

public class ManagerView extends ViewPart implements ISelectionChangedListener{

	private Table table;
	private TableViewer tv;
	private ProgressBar progressBar;

	public ManagerView() {

	}

	private void addHandlerFolder(Button button) {
		//TODO FolderDialog for disk path
		button.addSelectionListener(new DiskFolderSelectionListener(CoreConstants.MAINVIEW_ID));
	}



	@Override
	public void createPartControl(Composite parent) {

		parent = WidgetCreator.createComposite(parent);

		Group group = WidgetCreator.createGroup(parent, Messages.view_disk_path, 4);
		Text text =  WidgetCreator.createText(group, false, (SettingsBean) getSettingsBean(), "diskPath",3); //$NON-NLS-1$
		Button button = WidgetCreator.createButton(group,Messages.view_disk_open);
		addHandlerFolder(button);

		group = WidgetCreator.createGroup(parent, Messages.view_actions, 4);
		button = WidgetCreator.createButton(group,Messages.view_add);
		addAction(button);
		button = WidgetCreator.createButton(group,Messages.view_export);
		exportAction(button);
		button = WidgetCreator.createButton(group,Messages.view_delete);
		deleteAction(button);
		button = WidgetCreator.createButton(group,Messages.view_gamelist_update);
		addHandlerUpdate(button);

		Composite tableComp = WidgetCreator.createComposite(parent);				
		String[] columnsNames = {
				Messages.view_gamelist_column_id, Messages.view_gamelist_column_name, /*"Region",*/Messages.view_gamelist_column_size };
		int[] columnsSize = {15, 60, /*15,*/ 10};

		table = WidgetCreator.createTable(tableComp, SWT.Selection | SWT.FULL_SELECTION, columnsNames, columnsSize);
		tv = new TableViewer(table);
		tv.setContentProvider(new ManagerViewContentProvider());
		tv.setLabelProvider(  new ManagerViewLabelProvider());
		
		//TEST CELL MOD

	    CellEditor[] editors = new CellEditor[4];
	    editors[0] = new TextCellEditor(table);
	    editors[1] = new GameTitleCellEditor(table, SWT.READ_ONLY);
	    editors[3] =new TextCellEditor(table);

	    tv.setCellModifier(new GameCellModifiers(tv));
	    tv.setCellEditors(editors);
	    String[] props = new String[] {
	    		Messages.view_gamelist_column_id,
	    		Messages.view_gamelist_column_name,
	    		Messages.view_gamelist_column_size};
	    
	    tv.setColumnProperties(props);
	    
		getSite().setSelectionProvider(tv);

//		addTableEditor();


		tv.addSelectionChangedListener(this);
		
		setProgressBar(WidgetCreator.createProgressBar(parent));


	}



	private void addTableEditor() {
		final TableEditor editor = new TableEditor(table);
		//The editor must have the same size as the cell and must
		//not be any smaller than 50 pixels.
		editor.horizontalAlignment = SWT.LEFT;
		editor.grabHorizontal = true;
		editor.minimumWidth = 50;
		
		table.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				
				updateCellAndTxtFile(editor,e);
			}
		});

	}

	protected void updateCellAndTxtFile(final TableEditor editor, SelectionEvent e) {
		

		// editing the second column
		final int EDITABLECOLUMN = 1;
		
		// Clean up any previous editor control
		Control oldEditor = editor.getEditor();
		if (oldEditor != null) oldEditor.dispose();

		// Identify the selected row
		final TableItem item = (TableItem)e.item;
		if (item == null) return;

		// The control that will be the editor must be a child of the Table
		Combo newEditor = new Combo(table, SWT.NONE);
		final String oldText = item.getText().trim();
		String[] temp =  GuiUtils.getGameSelectedFromTableView().getGameAlternativeTitlesAsArray();
		newEditor.setItems(temp);

		newEditor.setText(item.getText(EDITABLECOLUMN));
		newEditor.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent me) {
				Combo text = (Combo)editor.getEditor();
				String newText = text.getText().trim();
				editor.getItem().setText(EDITABLECOLUMN, newText);
				
				if(!oldText.equals(newText)){
					FileUtils.updateGameTxtFile(item.getText(),text.getText());
				}
			}
		});
	
		//				newEditor.selectAll();
		newEditor.setFocus();
		editor.setEditor(newEditor, item, EDITABLECOLUMN);
	
	
	}

	private void addHandlerUpdate(Button button) {
		button.addSelectionListener(new UpdateGameListListener(CoreConstants.MAINVIEW_ID));

	}
	
	private void deleteAction(Button button) {
		button.addSelectionListener(new DeleteButtonListener(CoreConstants.MAINVIEW_ID));

	}

	@Override
	public void setFocus() {

	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {

		ISelection selection = tv.getSelection();
		IStructuredSelection sel = (IStructuredSelection) selection;

		for (Iterator<GameBean> iterator = sel.iterator(); iterator.hasNext();) {
			GameBean selectedGame = iterator.next();
			Model.setSelectedGame(selectedGame);

			try {
				PlatformUtils.getHandlerService(CoreConstants.MAINVIEW_ID).executeCommand(CoreConstants.COMMAND_COVER_UPDATE_ID, null);
			} catch (ExecutionException e) {
				e.printStackTrace();
			} catch (NotDefinedException e) {
				e.printStackTrace();
			} catch (NotEnabledException e) {
				e.printStackTrace();
			} catch (NotHandledException e) {
				e.printStackTrace();
			}
		}
		//		GuiUtils.getManagerTableViewer().refresh();

	}

	private SettingsBean getSettingsBean() {
		return (SettingsBean) Model.getBeans().get(SettingsBean.INDEX);
	}

	private GameBean getProcessBean() {
		return (GameBean) Model.getBeans().get(GameBean.INDEX);
	}

	public TableViewer getTv() {
		return tv;
	}

	public void setTv(TableViewer tv) {
		this.tv = tv;
	}

	private void exportAction(Button button) {

		button.addSelectionListener(new ExportButtonListener(CoreConstants.MAINVIEW_ID));


	}

	public void addAction(Button button){

		button.addSelectionListener(new AddButtonListener(CoreConstants.MAINVIEW_ID));

	}

	public void setProgressBar(ProgressBar progressBar) {
		this.progressBar = progressBar;
	}

	public ProgressBar getProgressBar() {
		return progressBar;
	}

}