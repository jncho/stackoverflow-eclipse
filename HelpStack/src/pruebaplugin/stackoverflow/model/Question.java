package pruebaplugin.stackoverflow.model;

import java.util.List;

public class Question {
	private int question_id;
	private String title;
	private List<String> tags;
	private int accepted_answer_id;
	private int answer_count;
	private List<Answer> answers;
	private String body;
	//private int creation_date
	private boolean is_answered;
	private int score;
	private int view_count;
	
	public Question() {
		
	}

	public boolean isIs_answered() {
		return is_answered;
	}

	public void setIs_answered(boolean is_answered) {
		this.is_answered = is_answered;
	}

	public int getView_count() {
		return view_count;
	}

	public void setView_count(int view_count) {
		this.view_count = view_count;
	}

	public int getAnswered_count() {
		return answer_count;
	}

	public void setAnswered_count(int answered_count) {
		this.answer_count = answered_count;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getQuestion_id() {
		return question_id;
	}

	public void setQuestion_id(int question_id) {
		this.question_id = question_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Override
	public String toString() {
		
		return "[Item]\n"
				+"(is_answered="+this.is_answered+")\n"
				+"(view_count="+this.view_count+")\n"
				+"(answer_count="+this.answer_count+")\n"
				+"(score="+this.score+")\n"
				+"(question_id="+this.question_id+")\n"
				+"(title="+this.title+")\n";
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public int getAccepted_answer_id() {
		return accepted_answer_id;
	}

	public void setAccepted_answer_id(int accepted_answer_id) {
		this.accepted_answer_id = accepted_answer_id;
	}

	public int getAnswer_count() {
		return answer_count;
	}

	public void setAnswer_count(int answer_count) {
		this.answer_count = answer_count;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}
