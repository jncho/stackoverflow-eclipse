package helpstack.views;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import helpstack.controllers.SearchCodeController;

public class SearchCodeDialog extends Dialog {
	
	private Text text;
	private Button btnSearch;
	TableViewer tableViewer;
	Browser browser;
	

	public SearchCodeDialog(Shell parentShell) {
		super(parentShell);
	}
	
	@Override
	protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setSize(500,600);
        newShell.setText("Search Code");
    }

    @Override
	protected Control createDialogArea(Composite parent) {
        Composite root = (Composite) super.createDialogArea(parent);

        root.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		SashForm sashForm = new SashForm(root, SWT.VERTICAL);
		
		Composite composite = new Composite(sashForm, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		
		this.text = new Text(composite, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		this.btnSearch = new Button(composite, SWT.NONE);
		btnSearch.setText("Search");
		
		this.tableViewer = new TableViewer(composite, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		createColumns(tableViewer);
		Table table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		tableViewer.setContentProvider(new ArrayContentProvider());
		
		
		
		Composite composite_1 = new Composite(sashForm, SWT.NONE);
		composite_1.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		this.browser = new Browser(composite_1, SWT.NONE);
		
		SearchCodeController.registerListeners(this);
		
		return root;
    }
    
    private void createColumns(TableViewer tableViewer) {
    	
    	String[] titles = { "Preview Code" };

		// Columna titulo
		TableViewerColumn col = createTableColumn(titles[0]);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				String code = (String) element;
				return code;
			}

		});
    	
    }
    
	private TableViewerColumn createTableColumn(String title) {

		TableViewerColumn viewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setResizable(true);
		column.setMoveable(true);
		column.setWidth(400);

		return viewerColumn;
	}
    
    @Override
    protected boolean isResizable() {
    	return true;
    }

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}

	public Button getBtnSearch() {
		return btnSearch;
	}

	public void setBtnSearch(Button btnSearch) {
		this.btnSearch = btnSearch;
	}

	public TableViewer getTableViewer() {
		return tableViewer;
	}

	public void setTableViewer(TableViewer tableViewer) {
		this.tableViewer = tableViewer;
	}

	public Browser getBrowser() {
		return browser;
	}

	public void setBrowser(Browser browser) {
		this.browser = browser;
	}

}
