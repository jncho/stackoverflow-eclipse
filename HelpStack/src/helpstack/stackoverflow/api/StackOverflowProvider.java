package helpstack.stackoverflow.api;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import helpstack.stackoverflow.exceptions.ConnectionSOException;
import helpstack.stackoverflow.model.Filter;
import helpstack.stackoverflow.model.Question;
import helpstack.stackoverflow.model.ResultRest;

public class StackOverflowProvider {

	private final String URL_ROOT = "http://api.stackexchange.com/2.2/";
	private ArrayList<String> fieldFilters;
	private Filter filter;

	private Gson gson;

	// Singleton
	private static StackOverflowProvider instance;

	public static StackOverflowProvider getInstance() throws ConnectionSOException{
		if (instance == null) {
			instance = new StackOverflowProvider();
		}

		return instance;
	}

	private StackOverflowProvider() throws ConnectionSOException{
		gson = new Gson();
		populateFieldFilters();
		generateFilter();

	}

	private String sendGet(String url) throws IOException {
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);
		HttpResponse responseHttp = httpClient.execute(request);
		
		return EntityUtils.toString(responseHttp.getEntity());

	}

	private void populateFieldFilters() {
		fieldFilters = new ArrayList<String>();

		// .Wrapper
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

	private void generateFilter() throws ConnectionSOException {
		String url_request = URL_ROOT + "filters/create?";
		// Clean all default include fields
		url_request += "base=none&";
		// set Unsafe
		url_request += "unsafe=false&";

		// include fields from fieldFilters attribute
		url_request += "include=";
		for (String field : fieldFilters) {
			url_request += field + ";";
		}

		try {
			// request filter
			String response = sendGet(url_request);

			Type resultRestType = new TypeToken<ResultRest<Filter>>() {
			}.getType();
			ResultRest<Filter> result = gson.fromJson(response, resultRestType);

			this.filter = result.getItems().get(0);

		} catch (IOException e) {
			e.printStackTrace();
			throw new ConnectionSOException();
		}

	}

	public List<Question> searchQuestions(String q, boolean accepted, String title, List<String> tagged)
			throws ConnectionSOException {
		// Generate url request
		String url_request = URL_ROOT + "search/advanced?";

		// site
		url_request += "site=stackoverflow&";

		// order
		url_request += "sort=relevance&";

		// q
		q = q.replaceAll(" ", "%20");
		url_request += "q=" + q + "&";

		// accepted
		url_request += "accepted=" + accepted + "&";

		// title
		if (title != null) {
			title = title.replaceAll(" ", "%20");
			url_request += "title=" + title + "&";
		}

		// filter
		url_request += "filter=" + this.filter.getFilter() + "&";

		// tagged
		if (tagged != null) {
			url_request += "tagged=";
			for (String tag : tagged) {
				url_request += tag + ";";
			}
		}

		// Request questions
		try {
			String response = sendGet(url_request);

			Type resultRestType = new TypeToken<ResultRest<Question>>() {
			}.getType();
			ResultRest<Question> result = gson.fromJson(response, resultRestType);

			return result != null ? result.getItems() : new ArrayList<Question>();
		} catch (IOException e) {
			e.printStackTrace();
			throw new ConnectionSOException();
		}
	}

}
