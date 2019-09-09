package helpstack.intrase.model;

import org.bson.Document;

public class QuestionIntraSE {
	
	private int question_id;
	private String title;
	private String body;
	private String answer;
	private String owner;
	
	
	public QuestionIntraSE() {
	}
	
	
	
	public QuestionIntraSE(int question_id, String title, String body, String answer, String owner) {
		super();
		this.question_id = question_id;
		this.title = title;
		this.body = body;
		this.answer = answer;
		this.owner = owner;
	}



	public static QuestionIntraSE toQuestionIntraSE(Document document) {
		QuestionIntraSE question = new QuestionIntraSE();
		question.setQuestion_id((int)document.get("question_id"));
		question.setBody((String)document.get("body"));
		question.setAnswer((String)document.get("answer"));
		question.setTitle((String)document.get("title"));
		question.setOwner((String)document.get("owner"));
		
		return question;
	}
	
	public static Document toDocument(QuestionIntraSE question) {
		
		Document document = new Document("question_id", question.getQuestion_id())
				.append("body", question.getBody())
				.append("answer",question.getAnswer())
				.append("title", question.getTitle())
				.append("owner", question.getOwner());
		
		return document;
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
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

}
