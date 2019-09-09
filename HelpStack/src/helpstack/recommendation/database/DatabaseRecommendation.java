package helpstack.recommendation.database;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.Document;
import org.eclipse.jface.preference.IPreferenceStore;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import helpstack.Activator;
import helpstack.interfaces.IQuestionRecomendation;
import helpstack.preferences.PreferenceConstants;

public class DatabaseRecommendation {
	
	public static final String D_HOST = "localhost";
	public static final int D_PORT = 27017;
	private String name_db = "RecommendationDB";
	
	private MongoClient mongoClient;
	private MongoDatabase database;
	
	// Singleton
	private static DatabaseRecommendation api;
	
	public static DatabaseRecommendation getInstance() {
		if (api == null) {
			api = new DatabaseRecommendation();
		}
		
		return api;
	}
	
	public static void updateInstance() {
		api = new DatabaseRecommendation();
	}

	private DatabaseRecommendation() {
		
		IPreferenceStore ps = Activator.getDefault().getPreferenceStore();
		String host = ps.getString(PreferenceConstants.P_SERVER_DR);
		int port = ps.getInt(PreferenceConstants.P_PORT_DR);
		
		this.mongoClient = new MongoClient(host,port);
		this.database = this.mongoClient.getDatabase(this.name_db);
	}
	
	public void insertQuestion(IQuestionRecomendation question) {
		int question_id = question.getId();
		MongoCollection<Document> collection = database.getCollection(question.getCollection());
		
		// Check if question exists in database
		FindIterable<Document> iterable = collection.find(Filters.eq("question_id",question_id));
		if (iterable.first() == null) {
			Document document = new Document("question_id",question_id);
			collection.insertOne(document);
		}
	}
	
	public void deleteQuestion(IQuestionRecomendation question) {
		int question_id = question.getId();
		MongoCollection<Document> collection = database.getCollection(question.getCollection());
		
		collection.deleteOne(Filters.eq("question_id",question_id));
	}
	
	public boolean existQuestion(IQuestionRecomendation question) {
		int question_id = question.getId();
		MongoCollection<Document> collection = database.getCollection(question.getCollection());
		
		FindIterable<Document> iterable = collection.find(Filters.eq("question_id",question_id));
		return iterable.first() != null;
	}
	
	public List<IQuestionRecomendation> sortQuestions(List<IQuestionRecomendation> questions) {
		
		if (questions.isEmpty()) {
			return questions;
		}
		
		MongoCollection<Document> collection = database.getCollection(questions.get(0).getCollection());
		
		// Get ids from questions
		List<Integer> ids = questions.stream()
			.map(IQuestionRecomendation::getId)
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

	public String getName_db() {
		return name_db;
	}

	public void setName_db(String name_db) {
		this.name_db = name_db;
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

}
