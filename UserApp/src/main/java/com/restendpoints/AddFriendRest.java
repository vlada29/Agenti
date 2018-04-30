package com.restendpoints;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.interfaces.UserFinderInterface;

@Path("/addFriend")
@RequestScoped
public class AddFriendRest {

	@EJB
	UserFinderInterface uf;
	
	
	@GET
	@Path("/add/{user}/{friend}")
	public String sendInfo(@PathParam("user") String user,@PathParam("friend") String friend) {
		System.out.println("Pogodio REST "+user + friend);
		return uf.addFriend(user, friend);
	}
	
}
