package helpstack.views;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.part.ViewPart;

import helpstack.stackoverflow.model.Question;

public class ResultSearchSOView extends ViewPart{
	
	private Browser browserQuestion;
	private Browser browserAnswer;

	@Override
	public void createPartControl(Composite parent) {
		
		SashForm sashForm = new SashForm(parent, SWT.NONE);
		
		Group grpQuestion = new Group(sashForm, SWT.NONE);
		grpQuestion.setText("Question");
		grpQuestion.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		browserQuestion = new Browser(grpQuestion, SWT.NONE);
		
		Group grpAnswer = new Group(sashForm, SWT.NONE);
		grpAnswer.setText("Answer");
		grpAnswer.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		browserAnswer = new Browser(grpAnswer, SWT.NONE);
		sashForm.setWeights(new int[] {1, 1});
		
		System.out.println("[LOG-HS ResultSearchSOView created.");
		
	}
	
	public void updateView(Question question) {
		String body = ViewTools.prettify(question.getBody());
		String answer = ViewTools.prettify(question.getAnswer());
		
		try {
			browserQuestion.setText(ViewTools.wrapHtml(body));
			browserAnswer.setText(ViewTools.wrapHtml(answer));
		}catch (Exception e) {
			System.err.println(e);
			MessageDialog.openError(getSite().getShell(), "Error getting template", e.getMessage());
		}
	}
	
	

	@Override
	public void setFocus() {
		
		
	}

	
	
}
