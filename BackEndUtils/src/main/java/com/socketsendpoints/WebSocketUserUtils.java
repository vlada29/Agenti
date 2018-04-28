package com.socketsendpoints;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.bson.Document;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;


import com.dbutils.MongoClientProvider;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;


@Singleton
@ServerEndpoint("/uservice/{action}/{username}/{password}")
public class WebSocketUserUtils {
	private Session session;
	
	@EJB
	MongoClientProvider mongoClientProvider;
	
	@OnOpen
	public void connect(Session session){
		this.session = session;
		System.out.println("Session: " + session);
	}
	
	@OnClose
	public void close(){
		this.session = null;
		System.out.println("Closed");
	}
	
	@OnMessage
	public void onMessage(String msg,  
			@PathParam("action") String action,
			@PathParam("username") String username,
			@PathParam("password") String password) throws JsonGenerationException, JsonMappingException, IOException{
		MongoClient mongoClient = mongoClientProvider.getMongoClient();
		MongoDatabase db = mongoClient.getDatabase("test");		
		MongoCollection<Document> users = db.getCollection("users");
		if(action.equals("login")){
			BasicDBObject criteria = new BasicDBObject();
			criteria.append("username", "username");
			criteria.append("password", "password");
			if(users.find(criteria)!=null){
				ObjectMapper mapper = new ObjectMapper();
				String jsonInString = mapper.writeValueAsString(users.find(Filters.regex("username", username)));
				this.session.getAsyncRemote().sendText(jsonInString);
			} else {
				this.session.getAsyncRemote().sendText(null);
			}
			
		} else if (action.equals("register")){
			if(users.find(Filters.regex("username", username)) == null){
				Document user = new Document("username", username)
						.append("password", password)
						.append("host", new BasicDBObject("",""));
				users.insertOne(user);
				this.session.getAsyncRemote().sendText("registered");
			}
		}
	}
}