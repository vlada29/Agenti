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

import com.interfaces.LogoutInterface;

@Singleton
@ServerEndpoint("/logout/{username}")
public class WebSocketLogout {
private Session session;
	
	@EJB
	LogoutInterface li;
	
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
			@PathParam("username") String username
			) throws JsonGenerationException, JsonMappingException, IOException{
		System.out.println("WSendpoint Logout");
		li.logout(username);

		this.session.getAsyncRemote().sendText("OK");

	}
}
