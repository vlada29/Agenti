package com.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.db.INodeUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.model.AID;
import com.model.Agent;
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
	public List<Agent> getClasses() {
		System.out.println("dobavalj klase");
		return centerUtils.getAgentTypes2();
	}
	
	@GET
	@Path("/running")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Agent> getRunning(){
		System.out.println("Getting running agents");
		List<Agent> ala = centerUtils.getRunning();
		System.out.println("broj pokrenutih agenata: "+ala.size());
		
		return centerUtils.getRunning();
	}
	
	@PUT
	@Path("/running/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Agent pokreniAgenta(String type,@PathParam("name") String name) {
		System.out.println("pokrece se agent "+name);
		Gson g = new Gson();
		AgentType at = g.fromJson(type, AgentType.class);
		centerUtils.pokreniAgenta(at, name);
		return null;
	}
	
	@DELETE
	@Path("/delete/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public AID zaustaviAgenta(@PathParam("name") String name) {

		System.out.println("Zaustavljanje agenta --- "+name);
		centerUtils.zaustaviAgenta(name);
		return new AID();
	}
}
