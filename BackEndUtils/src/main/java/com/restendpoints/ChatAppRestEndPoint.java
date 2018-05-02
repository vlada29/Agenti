package com.restendpoints;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.app.JaxRSActivator;



@Path("/ChatAppRestEndPoint")
@RequestScoped
public class ChatAppRestEndPoint {

	@GET
	@Path("/updateActiveUsers/{username}")
	public void updateActiveUsers(@PathParam("username") String username) {
		JaxRSActivator.activeUsers.add(username);
		
		System.out.println("Dodavanje novog aktivnog u chat appu.");		
		
		for(String u : JaxRSActivator.activeUsers)
			System.out.println(u+", ");
	}
}
