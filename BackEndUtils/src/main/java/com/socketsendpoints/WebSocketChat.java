package com.socketsendpoints;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.app.JaxRSActivator;
import com.google.gson.Gson;
import com.interfaces.MessageSaverInterface;
import com.model.Message;
import com.model.User;

@Singleton
@ServerEndpoint("/chat/{user}")
public class WebSocketChat {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketChat.class);
    private final Map<String, List<Session>> sessions = new ConcurrentHashMap<>();

    @EJB
    MessageSaverInterface ms;
    
    @OnMessage
    public void onMessage(Session session, String message, @PathParam("user") String key) {
    	//Cuvanje poruke u bazu
    	ms.saveMessage(message);
    	
    	Gson g = new Gson();
    	
    	Message m = g.fromJson(message, Message.class);
    	
    	ArrayList<User> activeUsers = JaxRSActivator.activeUsers;
    	
    	for(User s : activeUsers) {
    		for(String to : m.getTo()) {
    			//ako je korisnik koji prima poruku aktivan i nije onaj koji je salje, treba mu poslati poruku
    			if(s.getUsername().equals(to) && !s.getUsername().equals(key)) {
    				Session sess = sessions.get(to).get(0);
    				sess.getAsyncRemote().sendText(message);
    			}
    		}
    	}
    	
    	
    	/*
    	ako nisu na istom hostu treba sledeci rest poziv sa odgovarajucom IP adresom
    	
    	
    	ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target("http://localhost:8080/websocket-example/jaxrs/forwardMessage/send/"+"IMEKORISNIKA");
        Response response = target.request().post(Entity.entity(message, "application/json"));
        
    	*/

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
        LOGGER.info("Session closed ID: {}, key: {}", session.getId(), key);
        sessions.get(key).remove(session);
    }

    @OnError
    public void onError(Session session, @PathParam("user") String key, Throwable throwable) {
        LOGGER.error("Session error. Removing session: {}", Arrays.toString(new Object[]{session.getId(), key}), throwable);
        sessions.get(key).remove(session);
        try {
            session.close();
        } catch (IOException ex) {
            LOGGER.warn("Error closing session ID: {}", session.getId());
        }
    }

    public void send(String message, String key) {
        if (!sessions.containsKey(key)) {
            LOGGER.warn("Key '{}' not registered, can't send message '{}'", key, message);
            return;
        }
        sessions.get(key).parallelStream().forEach(session -> {
            session.getAsyncRemote().sendText(message);
        });
    }
    
    public List<String> getKeys() {
        return new ArrayList<>(sessions.keySet());
    }

}
