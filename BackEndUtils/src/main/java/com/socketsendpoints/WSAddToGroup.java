package com.socketsendpoints;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.interfaces.GroupCRUDInterface;

@Singleton
@ServerEndpoint("/addToGroup/{idGrupe}/{username}")
public class WSAddToGroup {
	private Session session;
	
	@EJB
	GroupCRUDInterface groupCrud;
	
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
	public void onMessage(String msg, @PathParam("idGrupe") String idGrupe, @PathParam("username") String username) {
		System.out.println(msg);

		this.session.getAsyncRemote().
		sendText(groupCrud.addUser(idGrupe, username));


	}
	
	
	
}
