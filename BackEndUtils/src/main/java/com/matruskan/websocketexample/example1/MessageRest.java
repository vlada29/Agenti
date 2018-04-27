package com.matruskan.websocketexample.example1;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 *
 * @author matruskan
 */
@Path("/v1/messages/example1")
@RequestScoped
public class MessageRest {
    @Inject
    WebSocketEndpointExample1 webSocketEndpoint;
    
    @POST
    @Path("/send")
    public void sendMessage(@FormParam("key") String key, @FormParam("message") String message) {
        webSocketEndpoint.send(message, key);
    }
    
    @GET
    @Path("/keys")
    public String keys() {
        return webSocketEndpoint.getKeys().toString();
    }
}
