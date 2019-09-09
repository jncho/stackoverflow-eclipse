package helpstack.search;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import helpstack.interfaces.ICodeSearch;
import helpstack.interfaces.IQuestionRecomendation;
import helpstack.intrase.database.DatabaseIntraSE;
import helpstack.intrase.model.WQuestionIntraSE;
import helpstack.recommendation.database.DatabaseRecommendation;
import helpstack.stackoverflow.api.StackOverflowAPI;
import helpstack.stackoverflow.exceptions.ConnectionSOException;
import helpstack.stackoverflow.model.WQuestionSO;

public class  SearchEngine {

	public SearchEngine() {
		
	}
	
	public static ArrayList<IQuestionRecomendation> searchQuestion(String query) throws ConnectionSOException{
		// Search in IntraSE
		DatabaseIntraSE dbISE = DatabaseIntraSE.getInstance();
		List<WQuestionIntraSE> wquestionsISE = WQuestionIntraSE.toWQuestionIntraSE(dbISE.searchByQuery(query));
		ArrayList<IQuestionRecomendation> questionsISE = new ArrayList<IQuestionRecomendation>(wquestionsISE);
		DatabaseRecommendation.getInstance().sortQuestions(questionsISE);

		// Search in SO
		List<WQuestionSO> wquestionsSO = WQuestionSO
				.toWQuestionIntraSE(StackOverflowAPI.getInstance().searchQuestions(query, true, null, null));
		ArrayList<IQuestionRecomendation> questionsSO = new ArrayList<IQuestionRecomendation>(wquestionsSO);
		DatabaseRecommendation.getInstance().sortQuestions(questionsSO);

		// join lists
		questionsISE.addAll(questionsSO);
		
		return questionsISE;
	}
	
	public static ArrayList<String> searchQuestionCode(String query) throws ConnectionSOException{
		// Search in IntraSE
		DatabaseIntraSE dbISE = DatabaseIntraSE.getInstance();
		List<WQuestionIntraSE> wquestionsISE = WQuestionIntraSE.toWQuestionIntraSE(dbISE.searchByQuery(query));
		ArrayList<IQuestionRecomendation> questionsISE = new ArrayList<IQuestionRecomendation>(wquestionsISE);
		DatabaseRecommendation.getInstance().sortQuestions(questionsISE);
		List<ICodeSearch> questionsISEcode = questionsISE.stream().map(c -> (ICodeSearch)c).collect(Collectors.toList());

		// Search in SO
		List<WQuestionSO> wquestionsSO = WQuestionSO
				.toWQuestionIntraSE(StackOverflowAPI.getInstance().searchQuestions(query, true, null, null));
		ArrayList<IQuestionRecomendation> questionsSO = new ArrayList<IQuestionRecomendation>(wquestionsSO);
		DatabaseRecommendation.getInstance().sortQuestions(questionsSO);
		List<ICodeSearch> questionsSOcode = questionsSO.stream().map(c -> (ICodeSearch)c).collect(Collectors.toList());

		// join lists
		ArrayList<ICodeSearch> questions = new ArrayList<ICodeSearch>(questionsISEcode);
		questions.addAll(questionsSOcode);
	    
		ArrayList<String> codes = new ArrayList<String>();
		// Get codes from answers
		for (ICodeSearch q : questions) {
			String answer = q.getAnswer();
			Document doc = Jsoup.parse(answer);
			Iterator<Element> iterator = doc.select("code").iterator();
			
			while(iterator.hasNext()) {
				codes.add(iterator.next().html());
			}
			
		}
		
		// map with method to get codes
		
		return codes;
		
	}

}
