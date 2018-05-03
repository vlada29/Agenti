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
		
		System.out.println("Aktivni: ");		
		
		for(String u : JaxRSActivator.activeUsers)
			System.out.println(u+", ");
	}
	
	@GET
	@Path("/removeActiveUsers/{username}")
	public void removeActiveUsers(@PathParam("username") String username) {
		JaxRSActivator.activeUsers.remove(username);
		
		System.out.println("Aktivni: ");		
		
		for(String u : JaxRSActivator.activeUsers)
			System.out.println(u+", ");
	}
	
	
}
