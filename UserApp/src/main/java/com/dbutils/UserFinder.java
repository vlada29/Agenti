package com.dbutils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;

import org.bson.Document;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import com.google.gson.Gson;
import com.interfaces.UserFinderInterface;
import com.model.Grupa;
import com.model.Host;
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
				System.out.println("1 NULL JE");
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
		try {
			MongoClient mongoClient = mcp.getMongoClient();
			MongoDatabase db = mongoClient.getDatabase("test");		
			MongoCollection<Document> groups = db.getCollection("groups");
			
			FindIterable<Document> d = groups.find();
			
			Gson g = new Gson();
			
			String ret = "[";
			
			boolean prvi = true;
			
			for(Document doc : d) {
				Grupa grupa = g.fromJson(doc.toJson(), Grupa.class);
				System.out.println("Grupa: " + grupa.getIme()+", Osnivac: "+grupa.getOsnivac());
				if(grupa.getClanovi().contains(user)) {
					if(prvi) {
						prvi = false;
					}else {
						ret+=",";
					}
					ret+=doc.toJson();
				}
				
			}
			
			ret+="]";
			
			return ret;
			
		} catch(Exception e) {
			return "ERROR";
		}
	}

	@Override
	public String login(String username, String password) {
		System.out.println("UserFinder Login: " + username +", "+password);
		try {
			MongoClient mongoClient = mcp.getMongoClient();
			MongoDatabase db = mongoClient.getDatabase("test");		
			MongoCollection<Document> users = db.getCollection("users");
			System.out.println("pera: " + users.find(eq("username","pera")).first());
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
				
				//User user = g.fromJson(d.first().toJson(), User.class);
				//activeUsers.add(username);
				//TODO 1 Notify ChattApp about new logged in user via JMS
				
				//rest temp
				ResteasyClient client = new ResteasyClientBuilder().build();
		        ResteasyWebTarget target = client.target("http://localhost:8080/websocket-example/jaxrs/ChatAppRestEndPoint/updateActiveUsers/"+username);
		        Response response = target.request().get();
		        String ret = response.readEntity(String.class);
		        /////////////
			
				System.out.println("json: " +d.first().toJson());
				return d.first().toJson();
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
				
				User noviUser = new User(username, password, firstname, lastname, new Host(address, alias), new ArrayList<String>());
				Gson g = new Gson();
				Document doc = Document.parse(g.toJson(noviUser));
				System.out.println("New user: " + doc.toString());
				users.insertOne(doc);
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
