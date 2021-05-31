package utils;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnection {

    public static synchronized MongoDatabase getDatabase() {
        MongoClient mongo = new MongoClient("localhost", 27017);
        MongoDatabase database = mongo.getDatabase("movies");
        return database;
    }
}
