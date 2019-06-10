package pruebaplugin.stackoverflow;

import java.util.Arrays;
import java.util.List;

import pruebaplugin.stackoverflow.api.StackOverflowAPI;
import pruebaplugin.stackoverflow.model.Question;

public class Main {

	public static void main(String[] args) {
		StackOverflowAPI api = new StackOverflowAPI();
		List<Question> questions = api.searchQuestions("read file java", true, "read file", Arrays.asList("java","io"));
		System.out.println(questions.size());
	}
	
	
}
