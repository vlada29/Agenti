package com.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.db.INodeUtils;
import com.model.AgentType;

@Path("/agents")
public class ACentarEndpoint {
	@EJB
	INodeUtils centerUtils;

	@GET
	@Path("/test")
	public void test() {
		System.out.println("ne master primio");
	}
	
	@GET
	@Path("/classes")
	@Produces(MediaType.APPLICATION_JSON)
	public List<AgentType> getClasses() {
		System.out.println("Getting classes");
		return centerUtils.getSupportedTypes();
	}
}
