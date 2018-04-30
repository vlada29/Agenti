package com.dbutils;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.bson.Document;

import com.google.gson.Gson;
import com.interfaces.UserFinderInterface;
import com.model.User;
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

	@Override
	public String addFriend(String user, String friend) {
		
		try {
			MongoClient m = mcp.getMongoClient();
			
			MongoDatabase db = m.getDatabase("test");		
			MongoCollection<Document> users = db.getCollection("users");
			
			FindIterable<Document> d = users.find(eq("username",user));
			
			if(d.first() == null) {
				throw new Exception();
			}
			
			Gson g = new Gson();
			
			User u = g.fromJson(d.first().toJson(), User.class);

			d = users.find(eq("username",friend));
			
			if(d.first() == null) {
				throw new Exception();
			}
			
			User f = g.fromJson(d.first().toJson(), User.class);
			
			u.getFriends().add(f);
			
			Document doc = Document.parse(g.toJson(u));
			
			users.findOneAndReplace(eq("username",user), doc);
			
			return "OK";
			
		} catch (Exception e) {
			return "ERROR";
		}

	}

	@Override
	public String getGroups(String user) {
		
		
		
		
		
		return "OK";
	}
	
	
}
