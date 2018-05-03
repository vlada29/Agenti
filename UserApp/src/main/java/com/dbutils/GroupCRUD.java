package com.dbutils;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.bson.Document;

import com.google.gson.Gson;
import com.interfaces.GroupCRUDInterface;
import com.model.Grupa;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
@Stateless
public class GroupCRUD implements GroupCRUDInterface {
	
	@EJB
	MongoClientProvider mcp;
	
	@Override
	public String createGroup(Grupa grupa) {
		// TODO Auto-generated method stub
		try {
			MongoClient mongoClient = mcp.getMongoClient();
			MongoDatabase db = mongoClient.getDatabase("test");		
			MongoCollection<Document> groups = db.getCollection("groups");
			
			System.out.println("Stigao do baze");
			
			int id = (int)groups.count();
			grupa.setId(id);
			
			System.out.println("nova grupa inserting: " + grupa.getIme() + ", ID: "+grupa.getId());
			
			Document docGrupa = Document.parse(new Gson().toJson(grupa));
			groups.insertOne(docGrupa);


			
			
			
			
			
			MongoCollection<Document> check = db.getCollection("groups");
			FindIterable<Document> d = check.find();
			
			Gson g = new Gson();
			
			
			for(Document doc : d) {
				Grupa gg = g.fromJson(doc.toJson(), Grupa.class);
				System.out.println(gg.getIme());
				
			}

			
			
			
			
			
			
			
		} catch(Exception e) {
			return null;
		}
		return "OK";
	}

	
	@Override
	public String deleteGroup(String nazivGrupe) {
		try {
			MongoClient mongoClient = mcp.getMongoClient();
			MongoDatabase db = mongoClient.getDatabase("test");		
			MongoCollection<Document> groups = db.getCollection("groups");
			
			groups.deleteOne(new Document("id", Integer.parseInt(nazivGrupe)));

		} catch(Exception e) {
			return null;
		}
		return "OK";
	}

	@Override
	public String addUser(String idGrupe, String username) {
		try {
			MongoClient mongoClient = mcp.getMongoClient();
			MongoDatabase db = mongoClient.getDatabase("test");		
			MongoCollection<Document> groups = db.getCollection("groups");
			
			FindIterable<Document> d = groups.find(eq("id",Integer.parseInt(idGrupe)));
		
			Gson g = new Gson();
			
			Grupa grupa = g.fromJson(d.first().toJson(), Grupa.class);
			
			grupa.getClanovi().add(username);
			
			String prom_json = g.toJson(grupa);
			org.bson.Document prom_document = org.bson.Document.parse(prom_json);

			groups.findOneAndReplace(eq("id",Integer.parseInt(idGrupe)), prom_document);
			
		} catch(Exception e) {
			return null;
		}
		return "OK";
	}

	@Override
	public String removeUser(String idGrupe, String username) {
		try {
			MongoClient mongoClient = mcp.getMongoClient();
			MongoDatabase db = mongoClient.getDatabase("test");		
			MongoCollection<Document> groups = db.getCollection("groups");
			
			FindIterable<Document> d = groups.find(eq("id",Integer.parseInt(idGrupe)));
			
			Gson g = new Gson();
			
			Grupa grupa = g.fromJson(d.first().toJson(), Grupa.class);
			
			grupa.getClanovi().remove(username);
			
			String prom_json = g.toJson(grupa);
			org.bson.Document prom_document = org.bson.Document.parse(prom_json);

			groups.findOneAndReplace(eq("id",Integer.parseInt(idGrupe)), prom_document);
			
		} catch(Exception e) {
			return null;
		}
		return "OK";
	}

	@Override
	public String leaveGroup(String idGrupe, String username) {
		try {
			MongoClient mongoClient = mcp.getMongoClient();
			MongoDatabase db = mongoClient.getDatabase("test");		
			MongoCollection<Document> groups = db.getCollection("groups");
			
			FindIterable<Document> d = groups.find(eq("id",Integer.parseInt(idGrupe)));
			
			Gson g = new Gson();
			
			Grupa grupa = g.fromJson(d.first().toJson(), Grupa.class);
			
			grupa.getClanovi().remove(username);
			
			String prom_json = g.toJson(grupa);
			org.bson.Document prom_document = org.bson.Document.parse(prom_json);

			groups.findOneAndReplace(eq("id",Integer.parseInt(idGrupe)), prom_document);
			
		} catch(Exception e) {
			return null;
		}
		return "OK";
	}

	

}
