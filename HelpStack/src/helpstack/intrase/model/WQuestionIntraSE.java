package helpstack.intrase.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import helpstack.interfaces.ICodeSearch;
import helpstack.interfaces.IQuestionRecommendation;
import helpstack.interfaces.IQuestionSearch;
import helpstack.views.ResultSearchISEView;

public class WQuestionIntraSE implements IQuestionSearch, IQuestionRecommendation, ICodeSearch{
	
	private final String COLLECTION = "RecomendationIntraSE";
	private final String SOURCE = "ISE";
	private QuestionIntraSE question;
	

	public WQuestionIntraSE(QuestionIntraSE question) {
		this.question = question;
	}

	@Override
	public void showView() {
		try {
			ResultSearchISEView rsv = (ResultSearchISEView)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("resultsearch_ise");
			rsv.updateView(question);
		} catch (PartInitException e1) {
			e1.printStackTrace();
		}
	}
	
	@Override
	public int getId() {
		return question.getQuestion_id();
	}
	
	@Override
	public String getCollection() {
		return COLLECTION;
	}
	
	@Override
	public String getTitle() {
		return question.getTitle();
	}
	
	@Override
	public int getScore() {
		return -1;
	}
	
	@Override
	public String getSource() {
		return SOURCE;
	}
	
	public static List<WQuestionIntraSE> toWQuestionIntraSE(List<QuestionIntraSE> questions) {
		
		ArrayList<WQuestionIntraSE> wquestions= new ArrayList<WQuestionIntraSE>();
		for (QuestionIntraSE q : questions) {
			wquestions.add(new WQuestionIntraSE(q));
		}
		
		return wquestions;
	}

	public QuestionIntraSE getQuestion() {
		return question;
	}

	public void setQuestion(QuestionIntraSE question) {
		this.question = question;
	}

	@Override
	public String getAnswer() {
		return question.getAnswer();
	}

}
