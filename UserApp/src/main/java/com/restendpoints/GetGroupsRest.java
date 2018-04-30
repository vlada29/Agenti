package com.restendpoints;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.interfaces.UserFinderInterface;

@Path("/getGroups")
@RequestScoped
public class GetGroupsRest {

	@EJB
	UserFinderInterface uf;
	
	
	@GET
	@Path("/get/{user}")
	public String sendInfo(@PathParam("user") String user) {
		System.out.println("Pogodio REST getGroups "+user);
		return uf.getGroups(user);
	}
	
}
