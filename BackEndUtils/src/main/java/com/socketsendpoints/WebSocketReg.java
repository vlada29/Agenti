package com.socketsendpoints;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.interfaces.LoginInterface;
import com.interfaces.RegisterInterface;

@Singleton
@ServerEndpoint("/register/{username}/{firstname}/{lastname}/{password}")
public class WebSocketReg {
private Session session;
	
	@EJB
	RegisterInterface ri;
	
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
			@PathParam("username") String username,
			@PathParam("password") String password,
			@PathParam("firstname") String firstname,
			@PathParam("lastname") String lastname) throws JsonGenerationException, JsonMappingException, IOException{
		System.out.println("WSendpoint Register");
		String userStr = ri.register(username, firstname, lastname, password);
		
		if(userStr!=null){
			this.session.getAsyncRemote().sendText(userStr);
		} else {
			this.session.getAsyncRemote().sendText("ERROR");
		}
	}
}
