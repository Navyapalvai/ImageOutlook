package Apache.ActiveMQ;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class MongoSend {
	public void getmessage(String s){
	 MongoClient mongo = new MongoClient();
		
	  DB db = mongo.getDB("ACTIVEDATA");
	 
 DBCollection collectionName = db.getCollection("MQDATA");

 BasicDBObject data = new BasicDBObject();
 
data.put("message", s);
collectionName.insert(data);
System.out.println("the data is"+data);

 
	} 
}
