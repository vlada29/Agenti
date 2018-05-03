package com.dbutils;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.bson.Document;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import com.google.gson.Gson;
import com.interfaces.MessageSaverInterface;
import com.model.Message;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;

import static com.mongodb.client.model.Filters.eq;

@Stateless
public class MessageSaver implements MessageSaverInterface {

	@EJB
	MongoClientProvider mcp;
	
	@Override
	public boolean saveMessage(String message) {
		System.out.println("jesi ovde sve ti jebem");
		ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target("http://localhost:8080/websocket-example/jaxrs/saveMessage/post");
        Response response = target.request().post(Entity.entity(message, "application/json"));
        String ret = response.readEntity(String.class);

		if(ret.equals("OK")) {
			return true;
		}
		return false;
	}

	@Override
	public boolean insertIntoDB(Message message) {
		try {
		Gson g = new Gson();

		System.out.println("krajnja poruka pred bazu: ");
		System.out.println(message.toString());

		MongoClient mongoClient = mcp.getMongoClient();
		MongoDatabase db = mongoClient.getDatabase("test");		
		MongoCollection<Document> groups = db.getCollection("groups");
		
		Document poruka = Document.parse(g.toJson(message));
		

		
		groups.updateOne(eq("ime",message.getSubject()), Updates.addToSet("poruke", poruka));
		
		return true;
		
		}catch (Exception e) {
			return false;
		}
	}
}
