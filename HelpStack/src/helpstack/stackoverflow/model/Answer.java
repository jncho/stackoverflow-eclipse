package helpstack.stackoverflow.model;

public class Answer {
	private int answer_id;
	private String body;
	private boolean is_accepted;
	private int score;
	
	public Answer() {
		
	}

	public int getAnswer_id() {
		return answer_id;
	}

	public void setAnswer_id(int answer_id) {
		this.answer_id = answer_id;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public boolean isIs_accepted() {
		return is_accepted;
	}

	public void setIs_accepted(boolean is_accepted) {
		this.is_accepted = is_accepted;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	
}
