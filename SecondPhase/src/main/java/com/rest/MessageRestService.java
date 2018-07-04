package com.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.model.ACLMessage;

//http://localhost:8080/SecondPhase/rest/message/hello%20world
@Path("/message")
public class MessageRestService {

	@GET
	@Path("/{param}")
	public Response printMessage(@PathParam("param") String msg) {

		String result = "Restful example : " + msg;

		return Response.status(200).entity(result).build();

	}
	
	@POST
	@Path("/messages")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ACLMessage postMessage(ACLMessage msg) {
		System.out.println("Primljena poruka "+msg.toString());
		return msg;
	}

}