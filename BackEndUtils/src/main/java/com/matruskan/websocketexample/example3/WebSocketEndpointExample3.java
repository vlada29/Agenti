package com.matruskan.websocketexample.example3;

import com.matruskan.websocketexample.tags.Tags;
import java.io.IOException;
import java.io.StringReader;
import javax.ejb.Singleton;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author matruskan
 */
@Singleton
@ServerEndpoint(value = "/example3/")
public class WebSocketEndpointExample3 {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketEndpointExample3.class);
    private final Tags<Session, String> tags = new Tags<>();
    
    @OnOpen
    public void onOpen(Session session) {
        LOGGER.info("Session ID: {} opened", session.getId());
        tags.add(session, session.getId());
    }
    
    @OnMessage
    public void onMessage(Session sessionFrom, String message) {
        LOGGER.info("Message received from session ID: {}, message: {}", sessionFrom.getId(), message);
        try (JsonReader jr = Json.createReader(new StringReader(message))) {
            JsonObject jo = jr.readObject();
            String toTag = jo.getString("toTag", null);
            String fromUser = jo.getString("fromUser", null);
            String content = jo.getString("content", null);
            String addTag = jo.getString("addTag", null);
            String removeTag = jo.getString("removeTag", null);
            if (toTag != null) {
                sendMessage(fromUser, toTag, content);
            }
            if (addTag != null) {
                addTag(sessionFrom, addTag);
            }
            if (removeTag != null) {
                removeTag(sessionFrom, removeTag);
            }
        }
    }
    
    private void sendMessage(String fromUser, String toTag, String content) {
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        objectBuilder.add("toTag", toTag);
        objectBuilder.add("fromUser", fromUser);
        objectBuilder.add("content", content);
        String message = objectBuilder.build().toString();
        tags.getObjectsWith(toTag).parallelStream().forEach(sessionTo -> sessionTo.getAsyncRemote().sendText(message));
        LOGGER.info("Message sent from session user {} to all sessions with tag {}: {}", fromUser, toTag, content);
    }
    
    private void addTag(Session session, String tag) {
        tags.add(session, tag);
        LOGGER.info("Session ID: {} received tag {}", session.getId(), tag);
    }
    
    private void removeTag(Session session, String tag) {
        tags.removeTagFrom(session, tag);
        LOGGER.info("Session ID: {} removed tag {}", session.getId(), tag);
    }
    
    @OnClose
    public void onClose(Session session) {
        LOGGER.info("Session closed ID: {}", session.getId());
        tags.removeObject(session);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        LOGGER.error("Session error. Removing session ID: " + session.getId(), throwable);
        try {
            session.close();
        } catch (IOException ex) {
            LOGGER.warn("Error closing session ID: " + session.getId(), ex);
        }
    }
}
