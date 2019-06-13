package helpstack.recommendation.database;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import helpstack.stackoverflow.model.Question;

public class DatabaseRecommendation {
	
	private String host;
	private int port;
	private String name_db = "RecommendationDB";
	private String name_collection = "Recommendation";
	
	private MongoClient mongoClient;
	private MongoDatabase database;
	private MongoCollection<Document> collection;

	public DatabaseRecommendation(String host, int port) {
		this.host = host;
		this.port = port;
		
		this.mongoClient = new MongoClient(host,port);
		this.database = this.mongoClient.getDatabase(this.name_db);
		this.collection = database.getCollection(this.name_collection);
	}
	
	public void insertQuestion(Question question) {
		int question_id = question.getQuestion_id();
		int answer_id = question.getAccepted_answer_id();
		
		// Check if question exists in database
		FindIterable<Document> iterable = collection.find(Filters.eq("question_id",question_id));
		if (iterable.first() == null) {
			Document document = new Document("question_id",question_id)
					.append("answer_id", answer_id);
			collection.insertOne(document);
		}
	}
	
	public void deleteQuestion(Question question) {
		int question_id = question.getQuestion_id();
		collection.deleteOne(Filters.eq("question_id",question_id));
	}
	
	public boolean existQuestion(Question question) {
		int question_id = question.getQuestion_id();
		FindIterable<Document> iterable = collection.find(Filters.eq("question_id",question_id));
		return iterable.first() != null;
	}
	
	public List<Question> sortQuestions(List<Question> questions) {
		// Get ids from questions
		List<Integer> ids = questions.stream()
			.map(Question::getQuestion_id)
			.collect(Collectors.toList());
		
		// Find questions in recommendations
		FindIterable<Document> iterable = collection.find(Filters.in("question_id", ids));
		
		Iterator<Document> it = iterable.iterator();
		Document doc = null;
		
		// Each element in recommendations is moved to first position in questions
		while(it.hasNext()) {
			doc = it.next();
			int question_id = (Integer)doc.get("question_id");
			
			questions.add(0,questions.remove(ids.indexOf(question_id)));
			ids.add(0,ids.remove(ids.indexOf(question_id)));
		}
		
		return questions;
		
	}
	
	public void close() {
		mongoClient.close();
	}
	
	// SETTERS AND GETTERS

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getName_db() {
		return name_db;
	}

	public void setName_db(String name_db) {
		this.name_db = name_db;
	}

	public String getName_collection() {
		return name_collection;
	}

	public void setName_collection(String name_collection) {
		this.name_collection = name_collection;
	}
	
	
	public MongoClient getMongoClient() {
		return mongoClient;
	}

	public void setMongoClient(MongoClient mongoClient) {
		this.mongoClient = mongoClient;
	}

	public MongoDatabase getDatabase() {
		return database;
	}

	public void setDatabase(MongoDatabase database) {
		this.database = database;
	}

	public MongoCollection<Document> getCollection() {
		return collection;
	}

	public void setCollection(MongoCollection<Document> collection) {
		this.collection = collection;
	}

}
