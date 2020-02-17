package helpstack.views;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import helpstack.controllers.SearchController;
import helpstack.interfaces.IQuestionRecommendation;
import helpstack.interfaces.IQuestionSearch;
import helpstack.recommendation.database.RecommendationProvider;

public class SearchView extends ViewPart {
	
	// UI controls
	private TableViewer viewer;
	private Button buttonSearch;
	private Button buttonRecommendation;
	private Text textSearch;
	private Label labelResults;
	

	@Override
	public void createPartControl(Composite parent) {

		Composite compositeTable = new Composite(parent, SWT.NONE);
		GridLayout gl = new GridLayout();
		gl.numColumns = 4;
		compositeTable.setLayout(gl);

		// Label search
		Label labelSearch = new Label(compositeTable, SWT.NONE);
		labelSearch.setText("Query:");

		// Text Search
		textSearch = new Text(compositeTable, SWT.BORDER);
		GridData gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
		textSearch.setLayoutData(gd);
		
		// Button Search
		buttonSearch = new Button(compositeTable, SWT.PUSH);
		buttonSearch.setText("Search");

		// Button Recommendation
		buttonRecommendation = new Button(compositeTable, SWT.PUSH);
		buttonRecommendation.setText("Like");

		// Table Viewer
		viewer = new TableViewer(compositeTable,
				SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);

		createColumns(compositeTable, viewer);
		final Table table = viewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		viewer.setContentProvider(new ArrayContentProvider());

		getSite().setSelectionProvider(viewer);

		// Layout Table Viewer
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.horizontalSpan = 4;
		viewer.getControl().setLayoutData(gd);
		
		// Results label
		labelResults = new Label(compositeTable,SWT.NONE);
		labelResults.setText("0 Results.");
		gd = new GridData(SWT.FILL, SWT.NONE, true, false);
		gd.horizontalSpan = 4;
		labelResults.setLayoutData(gd);
		// Controller
		SearchController.registerListeners(this);

	}

	private void createColumns(Composite composite, TableViewer viewer) {

		String[] titles = { "Like", "Source", "Title", "Score" };
		int[] bounds = { 50, 50, 350, 50 };

		// Columna titulo
		TableViewerColumn col = createTableColumn(titles[0], bounds[0]);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				IQuestionRecommendation q = (IQuestionRecommendation) element;
				return RecommendationProvider.getInstance().existQuestion(q) ? "Y" : "N";
			}

		});
		
		// Columna source
		col = createTableColumn(titles[1], bounds[1]);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				IQuestionSearch q = (IQuestionSearch) element;
				return q.getSource();
			}

		});

		// Columna titulo
		col = createTableColumn(titles[2], bounds[2]);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				IQuestionSearch q = (IQuestionSearch) element;
				return q.getTitle();
			}
		});

		// Columna Check
		col = createTableColumn(titles[3], bounds[3]);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				IQuestionSearch q = (IQuestionSearch) element;
				return q.getScore()<0 ? "-" : Integer.toString(q.getScore());
			}
		});
	}

	private TableViewerColumn createTableColumn(String title, int bound) {

		TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);

		return viewerColumn;
	}

	@Override
	public void setFocus() {

	}

	@Override
	public void dispose() {
		super.dispose();
		RecommendationProvider.getInstance().close();
	}

	public Button getButtonSearch() {
		return buttonSearch;
	}

	public Button getButtonRecommendation() {
		return buttonRecommendation;
	}

	public TableViewer getViewer() {
		return viewer;
	}

	public Text getTextSearch() {
		return textSearch;
	}

	public Label getLabelResults() {
		return labelResults;
	}

	public void setLabelResults(Label labelResults) {
		this.labelResults = labelResults;
	}
}
