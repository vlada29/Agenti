package com.dbutils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.bson.Document;

import com.google.gson.Gson;
import com.model.Host;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Singleton
@Startup
@LocalBean
public class StartUpBean {
	private static InetAddress ip;
    private static String hostname;
    
    private ArrayList<String> activeUsers;
    
	@EJB
	MongoClientProvider mcp;	
    
    @PostConstruct
    public void init() throws UnknownHostException{
    	ip = InetAddress.getLocalHost();
    	hostname = ip.getHostName();
    	System.out.println("IP: " + ip);
    	System.out.println("ADDRESS: " + ip.toString().split("/")[1] +", HOSTNAME: "+hostname);
    	
    	MongoClient mongoClient = mcp.getMongoClient();
		MongoDatabase db = mongoClient.getDatabase("test");
		
		MongoCollection<Document> users = db.getCollection("users");
		MongoCollection<Document> groups = db.getCollection("groups");
		//users.deleteMany(new Document());
		//groups.deleteMany(new Document());

    }
    
    public static Host getHost(){
    	return new Host(ip.toString().split("/")[1], hostname);
    }
    
    public ArrayList<String> getActiveUsers() {
		return activeUsers;
	}

	public void setActiveUsers(ArrayList<String> activeUsers) {
		this.activeUsers = activeUsers;
	}
}	
