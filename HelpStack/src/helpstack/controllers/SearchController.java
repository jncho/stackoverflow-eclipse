package helpstack.controllers;

import java.util.ArrayList;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import helpstack.interfaces.IQuestionRecomendation;
import helpstack.interfaces.IQuestionSearch;
import helpstack.recommendation.database.DatabaseRecommendation;
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

		// Handle button recommendation
		view.getButtonRecommendation().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IStructuredSelection selection = (IStructuredSelection) view.getViewer().getSelection();
				IQuestionRecomendation question = (IQuestionRecomendation) selection.getFirstElement();

				if (DatabaseRecommendation.getInstance().existQuestion(question)) {
					DatabaseRecommendation.getInstance().deleteQuestion(question);
					view.getButtonRecommendation().setText("Like");
				} else {
					DatabaseRecommendation.getInstance().insertQuestion(question);
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
				IQuestionRecomendation question = (IQuestionRecomendation) selection.getFirstElement();

				if (question != null) {
					view.getButtonRecommendation().setEnabled(true);
					if (DatabaseRecommendation.getInstance().existQuestion(question)) {
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
		String query = view.getTextSearch().getText();
		if (query.isEmpty()) {
			view.getViewer().setInput(null);
			view.getViewer().refresh();
			return;
		}

		try {
			ArrayList<IQuestionRecomendation> questions = SearchEngine.searchQuestion(query);

			// Update ui
			view.getViewer().setInput(questions);
			view.getViewer().refresh();
		} catch (ConnectionSOException e) {
			System.err.println(e);
			MessageDialog.openError(view.getSite().getShell(), "Connection Lost", e.getMessage());
		}

	}

}
