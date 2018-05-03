package com.dbutils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.bson.Document;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import com.google.gson.Gson;
import com.model.Host;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.xmlconfig.XMLParser;

@Singleton
@Startup
@LocalBean
public class StartUpBean {
	private static String ip;
    private static String hostname = "Alias";
    
    private ArrayList<String> activeUsers;
    
	@EJB
	MongoClientProvider mcp;	
    
    @PostConstruct
    public void init() throws IOException{
    	URL whatismyip = new URL("http://checkip.amazonaws.com");
		BufferedReader in = new BufferedReader(new InputStreamReader(
		                whatismyip.openStream()));

		String ip = in.readLine(); //you get the IP as a String
		
    	
		XMLParser xmlp = new XMLParser();
		Host h = xmlp.getHostByAlias("Master");
		
    	System.out.println("IP ajde: " + ip + " master adress ajde "+h.getAddress());
    	
    	ResteasyClient c = new ResteasyClientBuilder().build();				//TODO change port
        ResteasyWebTarget target_chat_app = c.target("http://"+h.getAddress()+"/websocket-example/ChatAppRestEndPoint/registerNewNode");
        Response response_activated = target_chat_app.request().post(Entity.entity(h, "application/json"));
        
    	
		
		
		
		
    	//MongoClient mongoClient = mcp.getMongoClient();
		//MongoDatabase db = mongoClient.getDatabase("test");
		
		//MongoCollection<Document> users = db.getCollection("users");
		//MongoCollection<Document> groups = db.getCollection("groups");
		//users.deleteMany(new Document());
		//groups.deleteMany(new Document());

    }
    
    public static Host getHost(){
    	return new Host(ip, hostname);
    }
    
    public ArrayList<String> getActiveUsers() {
		return activeUsers;
	}

	public void setActiveUsers(ArrayList<String> activeUsers) {
		this.activeUsers = activeUsers;
	}
}	
