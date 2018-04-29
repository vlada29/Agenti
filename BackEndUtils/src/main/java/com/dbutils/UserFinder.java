package com.dbutils;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import static com.mongodb.client.model.Filters.eq;

@Stateless
public class UserFinder implements UserFinderInterface{
	
	@EJB
	MongoClientProvider mcp;

	@Override
	public String searchForUser(String searchBy, String value) {
		
		MongoClient m = mcp.getMongoClient();
		
		MongoDatabase db = m.getDatabase("test");		
		MongoCollection<Document> users = db.getCollection("users");
		
		
		
		FindIterable<Document> d = users.find(eq(searchBy,value));
		
		if(d.first() == null) {
			return null;
		}
		
		Document doc = d.first();
		
		
		
		return doc.toJson();
	}
	
	
}
