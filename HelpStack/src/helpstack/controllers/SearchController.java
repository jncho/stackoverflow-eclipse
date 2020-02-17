package helpstack.controllers;

import java.util.ArrayList;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import helpstack.interfaces.IQuestionRecommendation;
import helpstack.interfaces.IQuestionSearch;
import helpstack.recommendation.database.RecommendationProvider;
import helpstack.search.SearchEngine;
import helpstack.stackoverflow.exceptions.ConnectionSOException;
import helpstack.views.SearchView;

public class SearchController {

	public static void registerListeners(SearchView view) {
		// Handle button search
		view.getButtonSearch().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				search(view);

			}
		});
		
		view.getTextSearch().addListener(SWT.Traverse,new Listener() {
			@Override
	        public void handleEvent(Event event)
	        {
	            if(event.detail == SWT.TRAVERSE_RETURN)
	            {
	            	search(view);
	            }
	        }
		});

		// Handle button recommendation
		view.getButtonRecommendation().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IStructuredSelection selection = (IStructuredSelection) view.getViewer().getSelection();
				IQuestionRecommendation question = (IQuestionRecommendation) selection.getFirstElement();

				if (RecommendationProvider.getInstance().existQuestion(question)) {
					RecommendationProvider.getInstance().deleteQuestion(question);
					view.getButtonRecommendation().setText("Like");
				} else {
					RecommendationProvider.getInstance().insertQuestion(question);
					view.getButtonRecommendation().setText("UnLike");
				}

				view.getViewer().refresh(true);
			}
		});

		// Handle double click in table viewer
		view.getViewer().addDoubleClickListener(new IDoubleClickListener() {

			@Override
			public void doubleClick(DoubleClickEvent e) {
				IStructuredSelection selection = (IStructuredSelection) e.getSelection();
				IQuestionSearch question = (IQuestionSearch) selection.getFirstElement();

				question.showView();
			}
		});

		// Handle click in table viewer
		view.getViewer().addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent e) {
				IStructuredSelection selection = (IStructuredSelection) e.getSelection();
				IQuestionRecommendation question = (IQuestionRecommendation) selection.getFirstElement();

				if (question != null) {
					IQuestionSearch questionSearch = (IQuestionSearch) selection.getFirstElement();
					questionSearch.showView();
					view.getButtonRecommendation().setEnabled(true);
					if (RecommendationProvider.getInstance().existQuestion(question)) {
						view.getButtonRecommendation().setText("UnLike");

					} else {
						view.getButtonRecommendation().setText("Like");
					}
				} else {
					view.getButtonRecommendation().setEnabled(false);
				}
			}
		});
	}

	public static void search(SearchView view) {
		// Update views
		view.getButtonSearch().setEnabled(false);
		view.getButtonSearch().redraw();
		view.getViewer().setInput(null);
		view.getViewer().refresh();
		view.getLabelResults().setText("Searching...");
		view.getLabelResults().redraw();
		
		String query = view.getTextSearch().getText();
		if (query.isEmpty()) {
			view.getViewer().setInput(null);
			view.getViewer().refresh();
			view.getLabelResults().setText("0 Results.");
			view.getLabelResults().redraw();
			return;
		}

		try {
			ArrayList<IQuestionRecommendation> questions = SearchEngine.searchQuestions(query);

			// Update ui
			view.getViewer().setInput(questions);
			view.getViewer().refresh();
			view.getLabelResults().setText(questions.size() + " Results.");
			view.getLabelResults().redraw();
		} catch (ConnectionSOException e) {
			System.err.println(e);
			MessageDialog.openError(view.getSite().getShell(), "Connection Lost", e.getMessage());
			view.getLabelResults().setText("Connection Lost.");
			view.getLabelResults().redraw();
		} finally {
			view.getButtonSearch().setEnabled(true);
			view.getButtonSearch().redraw();
		}

	}

}
