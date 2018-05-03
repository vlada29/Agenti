package com.restendpoints;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.socketsendpoints.WebSocketChat;


/**
 *
 * @author matruskan
 */
@Path("/forwardMessage")
@RequestScoped
public class MessageRest {
    @Inject
    WebSocketChat webSocketEndpoint;
    
    @POST
    @Path("/send/{key}")
    @Consumes("application/json")
    public void sendMessage(@PathParam("key") String key, String message) {
    	
    	System.out.println("Poruka namenjena useru: "+key +" stigla preko resta: " +message);
    	
        webSocketEndpoint.send(message, key);
    }
    
    @GET
    @Path("/keys")
    public String keys() {
    	System.out.println("Message rest woriking");
        return webSocketEndpoint.getKeys().toString();
    }
}
