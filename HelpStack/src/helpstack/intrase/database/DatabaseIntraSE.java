package helpstack.intrase.database;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bson.Document;
import org.eclipse.jface.preference.IPreferenceStore;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import helpstack.Activator;
import helpstack.intrase.model.QuestionIntraSE;
import helpstack.preferences.PreferenceConstants;

public class DatabaseIntraSE {

	public static final String D_HOST = "localhost";
	public static final int D_PORT = 27017;
	private final String name_db = "IntraseDB";
	private final String name_collection = "IntraSE";

	private MongoClient mongoClient;
	private MongoDatabase database;
	private MongoCollection<Document> collection;
	
	// Singleton
	private static DatabaseIntraSE db;
	
	public static DatabaseIntraSE getInstance() {
		
		if (db == null) {
			db = new DatabaseIntraSE();
		}
		
		return db;
	}
	
	public static void updateInstance() {
		db = new DatabaseIntraSE();
	}

	private DatabaseIntraSE() {
		
		IPreferenceStore ps = Activator.getDefault().getPreferenceStore();
		String host = ps.getString(PreferenceConstants.P_SERVER_DISE);
		int port = ps.getInt(PreferenceConstants.P_PORT_DISE);

		this.mongoClient = new MongoClient(host, port);
		this.database = this.mongoClient.getDatabase(this.name_db);
		this.collection = database.getCollection(this.name_collection);
	}
	


	public void insertQuestionIntraSE(QuestionIntraSE question) {
		int question_id = question.getQuestion_id();

		// Check if question exists in database
		FindIterable<Document> iterable = collection.find(Filters.eq("question_id", question_id));
		if (iterable.first() == null) {
			Document document = new Document("question_id", question_id).append("title", question.getTitle())
					.append("body", question.getBody()).append("answer", question.getAnswer())
					.append("owner", question.getOwner());
			collection.insertOne(document);
		}
	}
	
	public void updateQuestionIntraSE(QuestionIntraSE question) {
		collection.updateOne(
				Filters.eq("question_id", question.getQuestion_id()), 
				new Document("$set",QuestionIntraSE.toDocument(question)));
	}
	
	public int getNewId() {
		FindIterable<Document> iterable = collection.find().sort(new Document("question_id",-1)).limit(1);
		if (iterable.first()==null) {
			return 1;
		}
		int max_question_id = (int)iterable.first().get("question_id");
		return max_question_id+1;
		
	}

	public void deleteQuestion(QuestionIntraSE question) {
		int question_id = question.getQuestion_id();
		collection.deleteOne(Filters.eq("question_id", question_id));
	}

	public boolean existQuestion(QuestionIntraSE question) {
		int question_id = question.getQuestion_id();
		FindIterable<Document> iterable = collection.find(Filters.eq("question_id", question_id));
		return iterable.first() != null;
	}

	public List<QuestionIntraSE> searchByQuery(String query) {
		String[] words = query.split(" ");
		
		// positive lookahead pattern to search
		String pat = "^";
		for (String word : words) {
			pat += "(?=.*" + word + ")";
		}
		System.out.println(pat);
		FindIterable<Document> iterable = collection.find(Filters.regex("title", pat));
		
		Iterator<Document> it = iterable.iterator(); 
	    
		List<QuestionIntraSE> questions = new ArrayList<QuestionIntraSE>();
	    while (it.hasNext()) {  
	       
	      questions.add(QuestionIntraSE.toQuestionIntraSE(it.next()));
	    }
		
		return questions;
	}
	
	

	public void close() {
		mongoClient.close();
	}

}
