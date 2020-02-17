package helpstack.controllers;

import java.util.ArrayList;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import helpstack.interfaces.IQuestionRecommendation;
import helpstack.intrase.database.IntraSEProvider;
import helpstack.intrase.model.QuestionIntraSE;
import helpstack.views.ResultSearchISEView;
import helpstack.views.SearchView;

public class ResultSearchISEController {

	public static void registerListeners(ResultSearchISEView view) {
		
		// Listeners view
		// Button edit
		view.getBtnEdit().addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				view.getStackLayout().topControl = view.getEditQuestion();
				view.updateEditView();
				view.getParent().layout();
			}
			
		});
		
		// Listeners edit view
		view.getBtnCancel().addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				view.getStackLayout().topControl = view.getViewQuestion();
				view.updateView();
				view.getParent().layout();
			}
			
		});
		
		view.getBtnApply().addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				// Get fields
				String title = view.getTextTitle().getText();
				String owner = view.getTextOwner().getText();
				String question_text = view.getTextQuestion().getText();
				String answer_text = view.getTextAnswer().getText();
				
				
				// Validate fields
				if (title.isEmpty() 
						|| owner.isEmpty() 
						|| question_text.isEmpty()
						|| answer_text.isEmpty()) {
					
					MessageDialog.openError(view.getSite().getShell(), "Field Error", "Please, Fill in the required fields.");
					return;
				}
				
				// Update attributes question object
				QuestionIntraSE question = view.getQuestion();
				
				question.setTitle(title);
				question.setOwner(owner);
				question.setBody(question_text);
				question.setAnswer(answer_text);
				
				// Update question in database	
				IntraSEProvider.getInstance().updateQuestion(question);
				
				// Back to view composite
				view.getStackLayout().topControl = view.getViewQuestion();
				view.updateView();
				view.getParent().layout();
				
				
			}
			
		});
		
		view.getBtnDelete().addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				IntraSEProvider.getInstance().deleteQuestion(view.getQuestion());
				
				// Refresh table viewer
				try {
					SearchView sv = (SearchView)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("helpstack");
					ArrayList<IQuestionRecommendation> input = (ArrayList<IQuestionRecommendation>)sv.getViewer().getInput();
					
					for (IQuestionRecommendation q : input) {
						if (q.getId() == view.getQuestion().getQuestion_id()) {
							input.remove(q);
							MessageDialog.openInformation(view.getSite().getShell(), "Question deleted successfully", "The question has been deleted successfully");
							break;
						}
					}
					
					sv.getViewer().refresh(true);
				} catch (PartInitException e1) {
					e1.printStackTrace();
				}
				
				// Close view composite
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().hideView(view);
			}
			
			
			
		});
	}

}
