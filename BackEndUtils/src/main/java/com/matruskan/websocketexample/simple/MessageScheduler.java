package com.matruskan.websocketexample.simple;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author matruskan
 */
@Stateless
public class MessageScheduler {
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM HH:mm:ss");
    
    @Inject
    private WebSocketEndpoint webSocketEndpoint;
    
    @Schedule(hour = "*", minute = "*", second = "*/10", persistent = false)
    private void schedule() {
        String message = getMessage();
        webSocketEndpoint.sendToAll(message);
    }
    
    private String getMessage() {
        return "Message from server at: " + DATE_FORMAT.format(new Date());
    }
}
