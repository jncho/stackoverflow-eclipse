package helpstack.controllers;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import helpstack.intrase.database.IntraSEProvider;
import helpstack.intrase.model.QuestionIntraSE;
import helpstack.views.NewQuestionISEView;

public class NewQuestionISEController {



	public static void registerListeners(NewQuestionISEView view) {
		view.getBtnPost().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// Get fields
				String title = view.getTextTitle().getText();
				String owner = view.getTextOwner().getText();
				String question = view.getTextQuestion().getText();
				String answer = view.getTextAnswer().getText();
				
				
				// Validate fields
				if (title.isEmpty() 
						|| owner.isEmpty() 
						|| question.isEmpty()
						|| answer.isEmpty()) {
					
					MessageDialog.openError(view.getSite().getShell(), "Field Error", "Please, Fill in the required fields.");
					return;
				}
				
				// Create question
				int new_id = IntraSEProvider.getInstance().getNewId();
				QuestionIntraSE qise = new QuestionIntraSE(new_id,title,question,answer,owner);
				// Post question in database
				IntraSEProvider.getInstance().insertQuestion(qise);
				// Alert to user
				MessageDialog.openInformation(view.getSite().getShell(), "Question created successfully", "The question has been created successfully");
				// Clean all fields
				view.getTextTitle().setText("");
				view.getTextTitle().setText("");
				view.getTextTitle().setText("");
				view.getTextTitle().setText("");
			}
		});
	}
			
	

}
