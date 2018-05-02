package com.dbutils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.bson.Document;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.gson.Gson;
import com.interfaces.UserFinderInterface;
import com.model.User;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;

import static com.mongodb.client.model.Filters.eq;

@Stateless
public class UserFinder implements UserFinderInterface{
	
	@EJB
	MongoClientProvider mcp;

	private ArrayList<String> activeUsers;
	
	@PostConstruct
	public void init() {
		activeUsers = new ArrayList<String>();
	}
	
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
			
			u.getFriends().add(f.getUsername());
			
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

	@Override
	public String login(String username, String password) {
		System.out.println("UserFinder Login: " + username +", "+password);
		try {
			MongoClient mongoClient = mcp.getMongoClient();
			MongoDatabase db = mongoClient.getDatabase("test");		
			MongoCollection<Document> users = db.getCollection("users");
	
			AggregateIterable<Document> d = users.aggregate(
					Arrays.asList(
					Aggregates.match(Filters.eq("username", username)),
		            Aggregates.match(Filters.eq("password", password))));
			System.out.println("Trying");
			if(d.first() == null) {
				System.out.println("No such user!");
				return null;
			} else {
				System.out.println("Found user: ");
				Gson g = new Gson();
				
				User user = g.fromJson(d.first().toJson(), User.class);
				activeUsers.add(username);
				//TODO 1 Notify ChattApp about new logged in user via JMS
				
				
			
				System.out.println("json: " + new Gson().toJson(user));
				return new Gson().toJson(user);
			}

		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String register(String username, String firstname, String lastname, String password, String address, String alias) {
		System.out.println("UserFinder Register: " + username +", "+password);
		try {
			MongoClient mongoClient = mcp.getMongoClient();
			MongoDatabase db = mongoClient.getDatabase("test");		
			MongoCollection<Document> users = db.getCollection("users");
	
			AggregateIterable<Document> d = users.aggregate(
					Arrays.asList(
					Aggregates.match(Filters.eq("username", username)),
		            Aggregates.match(Filters.eq("password", password))));
			System.out.println("Trying");
			if(d.first() == null) {
				System.out.println("Username available!");
				Document document = new Document();
				document.append("username", username);
				document.append("password", password);
				document.append("firstname", firstname);
				document.append("lastname", lastname);
				document.append("host", new Document().append("address", address).append("alias", alias));
				System.out.println("New user: " + document.toString());
				users.insertOne(document);
				return "OK";
			} else {
				return null;
			}

		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String logout(String username) {
		System.out.println("Logout... " + username);
		try {
			MongoClient mongoClient = mcp.getMongoClient();
			MongoDatabase db = mongoClient.getDatabase("test");		
			MongoCollection<Document> users = db.getCollection("users");
			FindIterable<Document> d = users.find(eq("username",username));
			
			if(d.first() != null) {
				activeUsers.remove(username);
				return "OK";
			} else {
				return null;
			}

		} catch (Exception e) {
			return null;
		}
	}
	
	
	
	
}
