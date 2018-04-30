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

import com.interfaces.AddFriendInterface;
import com.model.User;

@Singleton
@ServerEndpoint("/addFriend/{user}/{friend}")
public class WebSocketAddFriend {
	private Session session;
	
	@EJB
	AddFriendInterface af;
	
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
	public void onMessage(String msg, @PathParam("user") String user, @PathParam("friend") String friend) {

		System.out.println("add user:" + user );
		
		
		boolean dodat = af.addFriend(user, friend);
		
		if(dodat) {
			this.session.getAsyncRemote().sendText("OK");
		}else {
			this.session.getAsyncRemote().sendText("ERROR");
		}

	}
	
	
	
}
