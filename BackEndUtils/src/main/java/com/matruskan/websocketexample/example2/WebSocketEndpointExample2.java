package com.matruskan.websocketexample.example2;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.ejb.Singleton;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.websocket.EndpointConfig;
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
@ServerEndpoint(value = "/example2/", configurator = CookieServerConfigurator.class)
public class WebSocketEndpointExample2 {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketEndpointExample2.class);
    private final Map<String, Session> sessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        String username = getUsername((List<String>)endpointConfig.getUserProperties().get("cookie"));
        if (username == null) {
            throw new RuntimeException("Username cookie not found");
        }
        LOGGER.info("Session opened username: {} session ID: {}", username, session.getId());
        sessions.put(username, session);
        updateUsersList();
    }
    
    public void updateUsersList() {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        sessions.keySet().forEach(user -> arrayBuilder.add(user));
        builder.add("type", "users");
        builder.add("content", arrayBuilder);
        String message = builder.build().toString();
        sessions.values().parallelStream()
                .forEach(session -> session.getAsyncRemote().sendText(message));
    }
    
    private String getUsername(List<String> cookies) {
        return cookies.stream()
                .filter(cookieString -> cookieString.startsWith("username"))
                .map(cookieString -> cookieString.split("=")[1])
                .findAny().get();
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        LOGGER.info("Message received from session ID: {}, message: {}", session.getId(), message);
        try (JsonReader jr = Json.createReader(new StringReader(message))) {
            JsonObject jo = jr.readObject();
            String toUser = jo.getString("toUser");
            sessions.get(toUser).getAsyncRemote().sendText(message);
        }
    }

    @OnClose
    public void onClose(Session session) {
        LOGGER.info("Session closed ID: {}", session.getId());
        String user = getUserForSession(session);
        sessions.remove(user);
        updateUsersList();
    }

    private String getUserForSession(Session session) {
        String key = sessions.keySet().stream()
                .filter(t -> sessions.get(t).equals(session))
                .findAny().get();
        return key;
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        LOGGER.error("Session error. Removing session: {}", session.getId(), throwable);
        try {
            session.close();
        } catch (IOException ex) {
            LOGGER.warn("Error closing session ID: {}", session.getId());
        }
    }
}
