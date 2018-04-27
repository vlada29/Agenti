package com.matruskan.websocketexample.simple;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Singleton;
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
@ServerEndpoint("/websocketendpoint")
public class WebSocketEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketEndpoint.class);
    private final List<Session> sessions = new ArrayList<>();
    
    @OnMessage
    public void onMessage(Session session, String message) {
        LOGGER.info("Message received from session ID: {}, message: {}", session.getId(), message);
    }
    
    @OnOpen
    public void onOpen(Session session) {
        LOGGER.info("Session opened ID: {}", session.getId());
        sessions.add(session);
    }
    
    @OnClose
    public void onClose(Session session) {
        LOGGER.info("Session closed ID: {}", session.getId());
        sessions.remove(session);
    }
    
    @OnError
    public void onError(Session session, Throwable throwable) {
        LOGGER.error("Session error. Removing session ID: {}", session.getId(), throwable);
        sessions.remove(session);
        try {
            session.close();
        } catch (IOException ex) {
            LOGGER.warn("Error closing session ID: {}", session.getId());
        }
    }
    
    public void sendToAll(String message) {
        sessions.forEach(session -> {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException ex) {
                LOGGER.warn("Error sending message '{}' to session id: {}", message, session.getId(), ex);
            }
        });
    }
}
