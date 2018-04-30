package com.socketsendpoints;


import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.jms.TextMessage;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.interfaces.UserFinderInterfaceChat;


@Singleton
@ServerEndpoint("/findFriend/{type}/{value}")
public class WebSocketFindFriends {
	private Session session;
	
	@EJB
	UserFinderInterfaceChat uf;
	
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
			@PathParam("type") String type,
			@PathParam("value") String value) {
		
		
		TextMessage msgs;
		
		System.out.println("Search by: "+type+" search for: "+value);
		System.out.println("Da li se promenio?");
		String u = uf.searchForUser(type, value);
		System.out.println(u);
		
		this.session.getAsyncRemote().sendText(u);
	}
	
	
}
