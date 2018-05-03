package com.socketsendpoints;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;
import com.interfaces.AddFriendInterface;
import com.interfaces.GroupCRUDInterface;
import com.model.Grupa;

@Singleton
@ServerEndpoint("/createGroup")
public class WebSocketCreateNewGroup {
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
	public void onMessage(String msg) {

		Gson g = new Gson();
		Grupa grupaObj = g.fromJson(msg, Grupa.class);
		System.out.println("grupaObj u socketu: "+ grupaObj.getId());
	
		this.session.getAsyncRemote().
		sendText(groupCrud.createGroup(
				grupaObj.getOsnivac(), 
				grupaObj.getIme(), 
				grupaObj.getClanovi()));


	}
	
	
	
}
