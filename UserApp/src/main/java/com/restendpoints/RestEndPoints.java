package com.restendpoints;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.google.gson.Gson;
import com.interfaces.GroupCRUDInterface;
import com.interfaces.UserFinderInterface;
import com.model.Grupa;

@Path("restendpoints")
@RequestScoped
public class RestEndPoints {
	@EJB
	UserFinderInterface uf;
	
	@EJB
	GroupCRUDInterface groupCrud;
	
	@GET
	@Path("/login/{username}/{password}")
	public String login(@PathParam("username") String username,@PathParam("password") String password) {
		System.out.println("RestEndPoint : Login");
		return uf.login(username, password);
	}
	
	@GET
	@Path("/register/{username}/{password}/{firstname}/{lastname}/{address}/{alias}")
	public String register(
			@PathParam("username") String username,
			@PathParam("password") String password,
			@PathParam("firstname") String firstname,
			@PathParam("lastname") String lastname,
			@PathParam("address") String address,
			@PathParam("alias") String alias	
			) {
		System.out.println("RestEndPoint : Register");
		return uf.register(username, firstname, lastname, password, address, alias);
	}
	
	@POST
	@Consumes("application/json")
	@Path("/createGroup")
	public String createGroup(String grupa) {
		System.out.println("RestEndPoint : Creating group");
		System.out.println(grupa);
		Gson g = new Gson();
		Grupa grupaObj = g.fromJson(grupa, Grupa.class);
		return groupCrud.createGroup(grupaObj);
	}
	
	@GET
	@Path("/deleteGroup/{nazivGrupe}")
	public String deleteGroup(@PathParam("nazivGrupe") String nazivGrupe) {
		System.out.println("RestEndPoint : Deleting group");
		System.out.println(nazivGrupe);
		return groupCrud.deleteGroup(nazivGrupe);
	}
	
	
	
	
	
	@GET
	@Path("/addToGroup/{idGrupe}/{username}")
	public String addUser(@PathParam("idGrupe") String idGrupe, @PathParam("username") String username) {
		System.out.println("RestEndPoint : Adding to group");
		System.out.println(idGrupe);
		return groupCrud.addUser(idGrupe, username);
	}
	
	@GET
	@Path("/removeFromGroup/{idGrupe}/{username}")
	public String removeFromGroup(@PathParam("idGrupe") String idGrupe, @PathParam("username") String username) {
		System.out.println("RestEndPoint : Removing from group");
		System.out.println(idGrupe);
		return groupCrud.removeUser(idGrupe, username);
	}
	
	@GET
	@Path("/leaveGroup/{idGrupe}/{username}")
	public String leaveGroup(@PathParam("idGrupe") String idGrupe, @PathParam("username") String username) {
		System.out.println("RestEndPoint : Leaving group");
		System.out.println(idGrupe);
		return groupCrud.leaveGroup(idGrupe, username);
	}
	
	
	
	
	
	
}
