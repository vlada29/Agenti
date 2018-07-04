package com.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.ejb.Singleton;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@Singleton
@ServerEndpoint("/logger/{user}")
public class WsLogger {
	private final Map<String, List<Session>> sessions = new ConcurrentHashMap<>();
	
	
	@OnMessage
	public void onMessage(String message) {
		System.out.println(message);
		send("test","vlada");
	}
	

    @OnOpen
    public void onOpen(Session session, @PathParam("user") String key) {
        System.out.println(" sesija i kljuc: "+session.getId()+ key);
        if (!sessions.containsKey(key)) {
            sessions.put(key, new ArrayList<>());
        }
        sessions.get(key).add(session);
    }

    @OnClose
    public void onClose(Session session, @PathParam("user") String key) {
        sessions.get(key).remove(session);
    }

    @OnError
    public void onError(Session session, @PathParam("user") String key, Throwable throwable) {
        sessions.get(key).remove(session);
        try {
            session.close();
        } catch (IOException ex) {
 
        }
    }

    public void send(String message, String key) {
        if (!sessions.containsKey(key)) {
            return;
        }
        sessions.get(key).parallelStream().forEach(session -> {
            session.getAsyncRemote().sendText(message);
        });
    }
	
}
