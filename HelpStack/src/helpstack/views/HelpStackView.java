package helpstack.views;

import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import helpstack.recommendation.database.DatabaseRecommendation;
import helpstack.stackoverflow.api.StackOverflowAPI;
import helpstack.stackoverflow.model.Answer;
import helpstack.stackoverflow.model.Question;

public class HelpStackView extends ViewPart {

	private TableViewer viewer;
	private StackOverflowAPI api;
	private DatabaseRecommendation recommendationDB;

	@Override
	public void createPartControl(Composite parent) {
		
		
		SashForm sf = new SashForm(parent,SWT.HORIZONTAL);
		Composite compositeTable = new Composite(sf,SWT.NONE);
		GridLayout gl = new GridLayout();
		gl.numColumns = 4;
		compositeTable.setLayout(gl);

		// Label search
		Label labelSearch = new Label(compositeTable, SWT.NONE);
		labelSearch.setText("Consulta:");

		// Text Search
		Text textSearch = new Text(compositeTable, SWT.BORDER);
		GridData gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
		textSearch.setLayoutData(gd);
		
		// Button Search
		Button buttonSearch = new Button(compositeTable, SWT.PUSH);
		buttonSearch.setText("Buscar");
		
		// Button Recommendation
		Button buttonRecommendation = new Button(compositeTable, SWT.PUSH);
		buttonRecommendation.setText("Like");
		

		// Table Viewer
		viewer = new TableViewer(compositeTable, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		
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
		
		// Sash question and answer
		Composite compositeQuestion = new Composite(sf, SWT.NONE);
		compositeQuestion.setLayout(new FillLayout());
		Browser browserQuestion = new Browser(compositeQuestion,SWT.BORDER);
		
		Composite compositeAnswer = new Composite(sf, SWT.NONE);
		compositeAnswer.setLayout(new FillLayout());
		Browser browserAnswer = new Browser(compositeAnswer,SWT.BORDER);
		
		sf.setWeights(new int[] {2,3,3});

		api = new StackOverflowAPI();
		
		// Listeners
		buttonSearch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String query = textSearch.getText();
				if (query.isEmpty()) {
					viewer.setInput(null);
					viewer.refresh();
					return;
				}
				List<Question> questions = api.searchQuestions(query , true, null , null);
				recommendationDB.sortQuestions(questions);
				viewer.setInput(questions);
				viewer.refresh();
				
			}
		});
		
		buttonRecommendation.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IStructuredSelection selection = (IStructuredSelection)viewer.getSelection();
				Question question = (Question)selection.getFirstElement();
				
				if (recommendationDB.existQuestion(question)) {
					recommendationDB.deleteQuestion(question);
					buttonRecommendation.setText("Like");
				}else {
					recommendationDB.insertQuestion(question);
					buttonRecommendation.setText("UnLike");
				}
				
				viewer.refresh(true);
				
			}
		});
		
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			
			@Override
			public void doubleClick(DoubleClickEvent e) {
				IStructuredSelection selection = (IStructuredSelection)e.getSelection();
				Question question = (Question)selection.getFirstElement();
				
				browserQuestion.setText(question.getBody());
				for (Answer a : question.getAnswers()) {
					if (a.getAnswer_id() == question.getAccepted_answer_id()) {
						browserAnswer.setText(a.getBody());
						break;
					}
				}
				
			}
		});
		
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent e) {
				IStructuredSelection selection = (IStructuredSelection)e.getSelection();
				Question question = (Question)selection.getFirstElement();
				
				if (question != null) {
					buttonRecommendation.setEnabled(true);
					if (recommendationDB.existQuestion(question)) {
						buttonRecommendation.setText("UnLike");
						
					}else {
						buttonRecommendation.setText("Like");
					}
				}else {
					buttonRecommendation.setEnabled(false);
				}
			}
		});
		
		
		
		this.recommendationDB = new DatabaseRecommendation("localhost", 27017);
		
		
	}

	private void createColumns(Composite composite, TableViewer viewer) {

		String[] titles = { "Like", "Title", "Score", "Answered" };
		int[] bounds = {50,350,50,80};
		
		// Columna titulo
		TableViewerColumn col = createTableColumn(titles[0],bounds[0]);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Question q = (Question) element;
				//TODO para evitar estar accediendo a la base de datos tantas veces se puede implementar un WRAPPER
				return recommendationDB.existQuestion(q) ? "Y" : "N"; 	
			}
			
		});
		
		// Columna titulo
		col = createTableColumn(titles[1],bounds[1]);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Question q = (Question) element;
				return q.getTitle();
			}
		});

		// Columna Check
		col = createTableColumn(titles[2],bounds[2]);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Question q = (Question) element;
				return Integer.toString(q.getScore());
			}
		});

		// Columna Votes
		col = createTableColumn(titles[3],bounds[3]);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Question q = (Question) element;
				return q.isIs_answered() ? "Yes" : "No";
			}
		});
	}

	private TableViewerColumn createTableColumn(String title,int bound) {

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
		this.recommendationDB.close();
	}
}
