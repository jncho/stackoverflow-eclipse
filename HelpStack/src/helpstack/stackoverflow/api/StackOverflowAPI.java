package helpstack.stackoverflow.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import helpstack.stackoverflow.model.Filter;
import helpstack.stackoverflow.model.Question;
import helpstack.stackoverflow.model.ResultRest;

public class StackOverflowAPI {
	
	private final String url_root = "http://api.stackexchange.com/2.2/";
	private ArrayList<String> fieldFilters;
	private Filter filter;
	
	private Gson gson;

	public StackOverflowAPI() {
		gson = new Gson();
		populateFieldFilters();
		generateFilter();
		
	}
	
	public String sendGet(String url) {
		
		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			con.setRequestProperty("Content-Type", "application/json");
			
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(new GZIPInputStream(con.getInputStream())));
			
			
			String inputLine;
			StringBuffer response = new StringBuffer();
	
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
		
			return response.toString();
		}catch(Exception e) {
			return "";
		}
	}
	
	//TODO posible implementacion automatica mediante reflection de las clases del modelo.
	public void populateFieldFilters() {
		fieldFilters = new ArrayList<String>();
		
		//.Wrapper
		fieldFilters.add(".items");
		fieldFilters.add(".has_more");
		
		// Answer
		fieldFilters.add("answer.answer_id");
		fieldFilters.add("answer.body");
		fieldFilters.add("answer.is_accepted");
		fieldFilters.add("answer.score");
		
		// Question
		fieldFilters.add("question.question_id");
		fieldFilters.add("question.title");
		fieldFilters.add("question.tags");
		fieldFilters.add("question.accepted_answer_id");
		fieldFilters.add("question.answer_count");
		fieldFilters.add("question.answers");
		fieldFilters.add("question.body");
		fieldFilters.add("question.creation_date");
		fieldFilters.add("question.is_answered");
		fieldFilters.add("question.score");
		fieldFilters.add("question.view_count");
	}
	
	public void generateFilter() {
		String url_request = url_root + "filters/create?";
		// Clean all default include fields
		url_request += "base=none&";
		// set Unsafe
		url_request += "unsafe=false&";
		
		// include fields from fieldFilters attribute
		url_request += "include=";
		for (String field : fieldFilters) {
			url_request += field + ";";
		}
		
		// request filter
		String response = sendGet(url_request);
		
		Type resultRestType = new TypeToken<ResultRest<Filter>>(){}.getType();
		ResultRest<Filter> result = gson.fromJson(response, resultRestType);
		
		this.filter = result.getItems().get(0);
		
	}
	
    public List<Question> searchQuestions(String q, boolean accepted, String title, List<String> tagged) {
    	// Generate url request
    	String url_request = url_root + "search/advanced?";
    	
    	// site
    	url_request += "site=stackoverflow&";
    	
    	// order
    	url_request += "sort=relevance&";
    	
    	// q
    	q = q.replaceAll(" ", "%20");
    	url_request += "q="+q+"&";
    	
    	// accepted
    	url_request += "accepted="+accepted+"&";
    	
    	// title
    	if (title != null) {
	    	title = title.replaceAll(" ", "%20");
	    	url_request += "title="+title+"&";
    	}
    	
    	// filter
    	url_request += "filter="+this.filter.getFilter()+"&";
    	
    	// tagged
    	if (tagged != null) {
	    	url_request += "tagged=";
	    	for (String tag : tagged) {
	    		url_request += tag + ";";
	    	}
    	}
    	
    	// Request questions
    	String response = sendGet(url_request);
		
		Type resultRestType = new TypeToken<ResultRest<Question>>(){}.getType();
		ResultRest<Question> result = gson.fromJson(response, resultRestType);
		
		return result.getItems();
    }
	
}
