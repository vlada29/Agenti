package com.socketsendpoints;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
@ServerEndpoint("/chat/{user}")
public class WebSocketChat {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketChat.class);
    private final Map<String, List<Session>> sessions = new ConcurrentHashMap<>();

    @OnMessage
    public void onMessage(Session session, String message, @PathParam("user") String key) {
        LOGGER.info("Message received from session ID: {}, message: {}, key: {}", session.getId(), message, key);
        sessions.get(key).parallelStream().forEach(session2 -> {
            if (session == session2) {
                return;
            }
            session2.getAsyncRemote().sendText(message);
        });
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
