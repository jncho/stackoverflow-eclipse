package helpstack.stackoverflow.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import helpstack.interfaces.ICodeSearch;
import helpstack.interfaces.IQuestionRecommendation;
import helpstack.interfaces.IQuestionSearch;
import helpstack.views.ResultSearchSOView;

public class WQuestionSO implements ICodeSearch, IQuestionSearch, IQuestionRecommendation{
	
	private final String COLLECTION = "RecomendationSO";
	private final String SOURCE = "SO";
	private Question question;

	public WQuestionSO(Question question) {
		this.question = question;
	}
	
	public static List<WQuestionSO> toWQuestionSO(List<Question> questions) {
		
		ArrayList<WQuestionSO> wquestions= new ArrayList<WQuestionSO>();
		for (Question q : questions) {
			wquestions.add(new WQuestionSO(q));
		}
		
		return wquestions;
	}

	@Override
	public void showView() {
		try {
			ResultSearchSOView rsv = (ResultSearchSOView)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("resultsearch_so");
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
		return question.getScore();
	}
	
	@Override
	public String getSource() {
		return SOURCE;
	}

	@Override
	public String getAnswer() {
		return question.getAnswer();
	}

}
