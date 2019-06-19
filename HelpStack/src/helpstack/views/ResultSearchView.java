package helpstack.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import helpstack.stackoverflow.model.Answer;
import helpstack.stackoverflow.model.Question;

public class ResultSearchView extends ViewPart{
	
	private Browser browserQuestion;
	private Browser browserAnswer;

	@Override
	public void createPartControl(Composite parent) {
		
		SashForm sf = new SashForm(parent,SWT.HORIZONTAL);
		
		Composite compositeQuestion = new Composite(sf, SWT.NONE);
		compositeQuestion.setLayout(new FillLayout());
		browserQuestion = new Browser(compositeQuestion,SWT.BORDER);
		
		Composite compositeAnswer = new Composite(sf, SWT.NONE);
		compositeAnswer.setLayout(new FillLayout());
		browserAnswer = new Browser(compositeAnswer,SWT.BORDER);
		
	}
	
	public void updateView(Question question) {
		browserQuestion.setText(question.getBody());
		for (Answer a : question.getAnswers()) {
			if (a.getAnswer_id() == question.getAccepted_answer_id()) {
				browserAnswer.setText(a.getBody());
				break;
			}
		}
	}

	@Override
	public void setFocus() {
		
		
	}

	
	
}
