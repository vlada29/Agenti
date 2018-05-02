package com.restendpoints;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;



@Path("ChatAppRestEndPoint")
@RequestScoped
public class ChatAppRestEndPoint {

	@GET
	@Path("/updateActiveUsers/{username}")
	public void updateActiveUsers(@PathParam("username") String username) {
		System.out.println("Dodavanje novog aktivnog u chat appu.");		
	}
}
