package jwbfs.ui.views.manager;

import java.util.Iterator;
import java.util.LinkedHashMap;

import jwbfs.i18n.Messages;
import jwbfs.model.ModelStore;
import jwbfs.model.beans.GameBean;
import jwbfs.model.utils.CoreConstants;
import jwbfs.model.utils.FileUtils;
import jwbfs.ui.ContextActivator;
import jwbfs.ui.listeners.mainView.DiskFolderSelectionListener;
import jwbfs.ui.utils.GuiUtils;
import jwbfs.ui.views.WidgetCreator;
import jwbfs.ui.views.table.GameCellModifiers;
import jwbfs.ui.views.table.GameTitleCellEditor;
import jwbfs.ui.views.table.ManagerViewContentProvider;
import jwbfs.ui.views.table.ManagerViewLabelProvider;
import jwbfs.ui.views.table.ManagerViewTableCellActivationStrategy;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;
import org.eclipse.jface.viewers.FocusCellOwnerDrawHighlighter;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerEditor;
import org.eclipse.jface.viewers.TableViewerFocusCellManager;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

public class ManagerView extends ViewPart implements ISelectionChangedListener{

	private Table table;
	private TableViewer tv;
	private ProgressBar progressBar;

	protected String viewID = null;

	public ManagerView() {
	}
	
	public ManagerView(String viewID) {
		this.viewID = viewID;
	}

	private void addHandlerFolder(Button button) {
		button.addSelectionListener(new DiskFolderSelectionListener(viewID));
	}



	@Override
	public void createPartControl(Composite parent) {

		parent = WidgetCreator.createComposite(parent);

		Group group = WidgetCreator.createGroup(parent, Messages.view_disk_path, 4);
		WidgetCreator.createText(group, false, ModelStore.getDisk(viewID), "diskPath",3); //$NON-NLS-1$
		Button button = WidgetCreator.createButton(group,Messages.view_disk_open);
		addHandlerFolder(button);
		
//		group = WidgetCreator.createGroup(parent, Messages.view_actions, 4);
//		button = WidgetCreator.createButton(group,Messages.view_add);
//		addAction(button);
//		button = WidgetCreator.createButton(group,Messages.view_export);
//		exportAction(button);
//		button = WidgetCreator.createButton(group,Messages.view_delete);
//		deleteAction(button);
//		button = WidgetCreator.createButton(group,Messages.view_gamelist_update);
//		addHandlerUpdate(button);

		Composite tableComp = WidgetCreator.createComposite(parent);				
		String[] columnsNames = {
				Messages.view_gamelist_column_selection,
				Messages.view_gamelist_column_id,
				Messages.view_gamelist_column_name, /*"Region",*/
				Messages.view_gamelist_column_size };
		int[] columnsSize = {3, 15, 60, 10};

		table = WidgetCreator.createTable(tableComp, SWT.Selection | SWT.FULL_SELECTION, columnsNames, columnsSize);
		tv = new TableViewer(table);
		tv.setContentProvider(new ManagerViewContentProvider(viewID));
		tv.setLabelProvider(  new ManagerViewLabelProvider());

		//Controls the cell activation
		TableViewerFocusCellManager focusCellManager = new TableViewerFocusCellManager(tv,new FocusCellOwnerDrawHighlighter(tv));
        ColumnViewerEditorActivationStrategy actSupport = new ManagerViewTableCellActivationStrategy(tv);
        	                
        TableViewerEditor.create(tv, focusCellManager, actSupport, ColumnViewerEditor.TABBING_HORIZONTAL
        	                                | ColumnViewerEditor.TABBING_MOVE_TO_ROW_NEIGHBOR
        	                                | ColumnViewerEditor.TABBING_VERTICAL | ColumnViewerEditor.KEYBOARD_ACTIVATION);
		
	    CellEditor[] editors = new CellEditor[4];
	    editors[0] = new CheckboxCellEditor(table);
	    editors[1] = new TextCellEditor(table);
	    editors[2] = new GameTitleCellEditor(table, SWT.READ_ONLY);
	    editors[3] =new TextCellEditor(table);

	    tv.setCellModifier(new GameCellModifiers(tv));
	    tv.setCellEditors(editors);
	    String[] props = new String[] {
	    		Messages.view_gamelist_column_selection,
	    		Messages.view_gamelist_column_id,
	    		Messages.view_gamelist_column_name,
	    		Messages.view_gamelist_column_size};
	    
	    tv.setColumnProperties(props);
	    
		getSite().setSelectionProvider(tv);

		tv.addSelectionChangedListener(this);
//		setProgressBar(WidgetCreator.createProgressBar(parent));
		
		tv.setInput(ModelStore.getGames(viewID));
		tv.refresh();
		
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getPartService().addPartListener(new IPartListener() {
			
			@Override
			public void partOpened(IWorkbenchPart part) {
				System.out.println("opened");
				ContextActivator.reloadContext(viewID);
			}
			
			@Override
			public void partDeactivated(IWorkbenchPart part) {}
			
			@Override
			public void partClosed(IWorkbenchPart part) {
				System.out.println("closed");
				ContextActivator.reloadContext();
			}
			
			@Override
			public void partBroughtToTop(IWorkbenchPart part) {}
			
			@Override
			public void partActivated(IWorkbenchPart part) {}
		});
	}

	protected void updateCellAndTxtFile(final TableEditor editor, SelectionEvent e) throws Exception {
		

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
					FileUtils.updateGameTxtFile(item.getText(),text.getText(),GuiUtils.getActiveViewID());
				}
			}
		});
	
		//				newEditor.selectAll();
		newEditor.setFocus();
		editor.setEditor(newEditor, item, EDITABLECOLUMN);
	
	
	}

//	private void addHandlerUpdate(Button button) {
//		button.addSelectionListener(new UpdateGameListListener(viewID));
//
//	}
//	
//	private void deleteAction(Button button) {
//		button.addSelectionListener(new DeleteButtonListener(viewID));
//
//	}

	@Override
	public void setFocus() {

	}

	@SuppressWarnings("unchecked")
	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		GameBean oldSelected = ModelStore.getSelectedGame();
		ModelStore.setActiveDiskID(viewID);
		
		ISelection selection = tv.getSelection();
		IStructuredSelection sel = (IStructuredSelection) selection;
		
		Iterator<GameBean> iterator = sel.iterator();
		while (iterator.hasNext()) {
			GameBean selectedGame = iterator.next();
			ModelStore.setSelectedGame(selectedGame);
			if(selectedGame.equals(oldSelected)){
				return;
			}
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					
					LinkedHashMap<String,String> parametri = new LinkedHashMap<String,String>();
					parametri.put("diskID",viewID);
					GuiUtils.executeParametrizedCommand(
							CoreConstants.COMMAND_COVER_UPDATE_ID, parametri, null);
				}
			});	
		}
	}

	public synchronized TableViewer getTv() {
		return tv;
	}

	public void setTv(TableViewer tv) {
		this.tv = tv;
	}

//	private void exportAction(Button button) {
//		button.addSelectionListener(new ExportButtonListener(viewID));
//	}

//	private void addAction(Button button){
//		button.addSelectionListener(new AddButtonListener(viewID));
//	}

	public void setProgressBar(ProgressBar progressBar) {
		this.progressBar = progressBar;
	}

	public ProgressBar getProgressBar() {
		return progressBar;
	}

}