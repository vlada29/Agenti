package com.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.db.INodeUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.AgentType;

@Path("/agents")
@Stateless
public class ACentarEndpoint {
	@EJB
	INodeUtils centerUtils;

	@GET
	@Path("/test")
	public String test() {
		System.out.println("ne master primio");
		return "{\"poruka\":\"ne master primio\"}";
	}
	
	@GET
	@Path("/classes")
	@Produces(MediaType.APPLICATION_JSON)
	public String getClasses() throws JsonProcessingException {
		System.out.println("Getting classes");
		centerUtils.getSupportedTypes();
		
		//Gson g = new Gson();
		ObjectMapper mapper = new ObjectMapper();

		   String u = mapper.writeValueAsString( centerUtils.getSupportedTypes());
		   return u;
	}
}
