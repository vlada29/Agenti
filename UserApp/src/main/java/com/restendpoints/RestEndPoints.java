package com.restendpoints;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.interfaces.UserFinderInterface;

@Path("restendpoints")
@RequestScoped
public class RestEndPoints {
	@EJB
	UserFinderInterface uf;
	
	@GET
	@Path("/login/{username}/{password}")
	public String login(@PathParam("username") String username,@PathParam("password") String password) {
		System.out.println("RestEndPoint : Login");
		return uf.login(username, password);
	}
	
	@GET
	@Path("/register/{username}/{firstname}/{lastname}/{password}")
	public String register(
			@PathParam("username") String username,
			@PathParam("password") String password,
			@PathParam("firstname") String firstname,
			@PathParam("lastname") String lastname			
			) {
		System.out.println("RestEndPoint : Register");
		return uf.register(username, firstname, lastname, password);
	}
}
