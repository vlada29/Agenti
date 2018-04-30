package com.socketsendpoints;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.interfaces.GroupFinderInterface;
import com.interfaces.UserFinderInterfaceChat;

@Singleton
@ServerEndpoint("/findGroups/{user}")
public class WebSocketFindGroups {
private Session session;
	
	@EJB
	GroupFinderInterface gf;
	
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
	public void onMessage(String msg, @PathParam("user") String user) {
		
		System.out.println("Find groups for: "+user);
		String u = gf.getGroups(user);
		System.out.println(u);
		
		this.session.getAsyncRemote().sendText(u);
	}
	
}
