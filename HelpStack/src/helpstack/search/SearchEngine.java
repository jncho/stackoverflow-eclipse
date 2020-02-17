package helpstack.search;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import helpstack.interfaces.ICodeSearch;
import helpstack.interfaces.IQuestionRecommendation;
import helpstack.intrase.database.IntraSEProvider;
import helpstack.intrase.model.WQuestionIntraSE;
import helpstack.recommendation.database.RecommendationProvider;
import helpstack.stackoverflow.api.StackOverflowProvider;
import helpstack.stackoverflow.exceptions.ConnectionSOException;
import helpstack.stackoverflow.model.WQuestionSO;

public class  SearchEngine {

	public SearchEngine() {
		
	}
	
	public static ArrayList<IQuestionRecommendation> searchQuestions(String query) throws ConnectionSOException{
		// Search in IntraSE
		IntraSEProvider dbISE = IntraSEProvider.getInstance();
		List<WQuestionIntraSE> wquestionsISE = WQuestionIntraSE.toWQuestionIntraSE(dbISE.searchByQuery(query));
		ArrayList<IQuestionRecommendation> questionsISE = new ArrayList<IQuestionRecommendation>(wquestionsISE);
		RecommendationProvider.getInstance().sortQuestions(questionsISE);

		// Search in SO
		List<WQuestionSO> wquestionsSO = WQuestionSO
				.toWQuestionSO(StackOverflowProvider.getInstance().searchQuestions(query, true, null, null));
		ArrayList<IQuestionRecommendation> questionsSO = new ArrayList<IQuestionRecommendation>(wquestionsSO);
		RecommendationProvider.getInstance().sortQuestions(questionsSO);

		// join lists
		questionsISE.addAll(questionsSO);
		
		return questionsISE;
	}
	
	public static ArrayList<String> searchQuestionsCode(String query) throws ConnectionSOException{
		// Search in IntraSE
		IntraSEProvider dbISE = IntraSEProvider.getInstance();
		List<WQuestionIntraSE> wquestionsISE = WQuestionIntraSE.toWQuestionIntraSE(dbISE.searchByQuery(query));
		ArrayList<IQuestionRecommendation> questionsISE = new ArrayList<IQuestionRecommendation>(wquestionsISE);
		RecommendationProvider.getInstance().sortQuestions(questionsISE);
		List<ICodeSearch> questionsISEcode = questionsISE.stream().map(c -> (ICodeSearch)c).collect(Collectors.toList());

		// Search in SO
		List<WQuestionSO> wquestionsSO = WQuestionSO
				.toWQuestionSO(StackOverflowProvider.getInstance().searchQuestions(query, true, null, null));
		ArrayList<IQuestionRecommendation> questionsSO = new ArrayList<IQuestionRecommendation>(wquestionsSO);
		RecommendationProvider.getInstance().sortQuestions(questionsSO);
		List<ICodeSearch> questionsSOcode = questionsSO.stream().map(c -> (ICodeSearch)c).collect(Collectors.toList());

		// join lists
		ArrayList<ICodeSearch> questions = new ArrayList<ICodeSearch>(questionsISEcode);
		questions.addAll(questionsSOcode);
	    
		ArrayList<String> codes = new ArrayList<String>();
		// Get codes from answers
		for (ICodeSearch q : questions) {
			String answer = q.getAnswer();
			Document doc = Jsoup.parse(answer);
			Iterator<Element> iterator = doc.select("pre code").iterator();
			
			while(iterator.hasNext()) {
				codes.add(iterator.next().html());
			}
			
		}
		
		// map with method to get codes
		
		return codes;
		
	}

}
