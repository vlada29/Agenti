package com.restendpoints;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.interfaces.MessageSaverInterface;
import com.model.Message;

@Path("/saveMessage")
@RequestScoped
public class SaveMessageRest {

	@EJB
	MessageSaverInterface ms;
	
	@POST
	@Path("/post")
	@Consumes("application/json")
	public String sendInfo(Message message) {
		System.out.println("Pogodio REST saveMessage "+message.toString());
		
		if(ms.insertIntoDB(message)) {
			return "OK";
		}else {
			return "ERROR";
		}
	}
	
}
