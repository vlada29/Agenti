package com.socketsendpoints;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.jms.JMSException;
import javax.naming.NamingException;
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
import com.interfaces.LoginInterface;
import com.jms.JMSInterface;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;


@Singleton
@ServerEndpoint("/login/{username}/{password}")
public class WebSocketUserUtils {
	private Session session;
	
	@EJB
	LoginInterface lf;
	
	@OnOpen
	public void connect(Session session){
		this.session = session;
		System.out.println("Session: " + session);
		System.out.println("login");
	}
	
	@OnClose
	public void close(){
		this.session = null;
		System.out.println("Closed");
	}
	
	@OnMessage
	public void onMessage(String msg,  
			@PathParam("username") String username,
			@PathParam("password") String password) throws JsonGenerationException, JsonMappingException, IOException, JMSException, NamingException{
		System.out.println("WSendpoint Login");
		String userStr = null;
		try {
			userStr = lf.login(username, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("UserStr: " + userStr);
		if(userStr!=null){
			this.session.getAsyncRemote().sendText(userStr);
		} else {
			this.session.getAsyncRemote().sendText("ERROR");
		}
	}
}