package helpstack.views;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import helpstack.intrase.database.DatabaseIntraSE;
import helpstack.intrase.model.QuestionIntraSE;

public class NewQuestionISEView extends ViewPart{

	@Override
	public void createPartControl(Composite parent) {
		
		parent.setLayout(new GridLayout(2, false));
		
		Label lblTitulo = new Label(parent, SWT.NONE);
		lblTitulo.setText("Title* :");
		
		Text textTitle = new Text(parent, SWT.BORDER);
		textTitle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblCreador = new Label(parent, SWT.NONE);
		lblCreador.setText("Creator* :");
		
		Text textOwner = new Text(parent, SWT.BORDER);
		textOwner.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		SashForm sashForm = new SashForm(parent, SWT.NONE);
		sashForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		Group grpQuestion = new Group(sashForm, SWT.NONE);
		grpQuestion.setText("Question*");
		grpQuestion.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Text textQuestion = new Text(grpQuestion, SWT.MULTI);
		
		Group grpAnswer = new Group(sashForm, SWT.NONE);
		grpAnswer.setText("Answer*");
		grpAnswer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Text textAnswer = new Text(grpAnswer, SWT.MULTI);
		sashForm.setWeights(new int[] {1, 1});
		
		Button btnPost = new Button(parent, SWT.NONE);
		btnPost.setText("Create");
		
		btnPost.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// Get fields
				String title = textTitle.getText();
				String owner = textOwner.getText();
				String question = textQuestion.getText();
				String answer = textAnswer.getText();
				
				
				// Validate fields
				if (title.isEmpty() 
						|| owner.isEmpty() 
						|| question.isEmpty()
						|| answer.isEmpty()) {
					
					MessageDialog.openError(getSite().getShell(), "Field Error", "Please, Fill in the required fields.");
					return;
				}
				
				// Create question
				int new_id = DatabaseIntraSE.getInstance().getNewId();
				QuestionIntraSE qise = new QuestionIntraSE(new_id,title,question,answer,owner);
				// Post question in database
				DatabaseIntraSE.getInstance().insertQuestionIntraSE(qise);
				// Alert to user
				MessageDialog.openInformation(getSite().getShell(), "Question created successfully", "The question has been created successfully");
				// Clean all fields
				textTitle.setText("");
				textOwner.setText("");
				textQuestion.setText("");
				textAnswer.setText("");
			}
		});
	}

	@Override
	public void setFocus() {
		
	}

}
