package helpstack.stackoverflow.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import helpstack.interfaces.ICodeSearch;
import helpstack.interfaces.IQuestionRecomendation;
import helpstack.interfaces.IQuestionSearch;
import helpstack.views.ResultSearchSOView;

public class WQuestionSO implements ICodeSearch, IQuestionSearch, IQuestionRecomendation{
	
	private final String collection = "RecomendationSO";
	private final String source = "SO";
	private Question question;

	public WQuestionSO(Question question) {
		this.question = question;
	}
	
	public static List<WQuestionSO> toWQuestionIntraSE(List<Question> questions) {
		
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
		return collection;
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
		return source;
	}

	@Override
	public String getAnswer() {
		return question.getAnswer();
	}

}
