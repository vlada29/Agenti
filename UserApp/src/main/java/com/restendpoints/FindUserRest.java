package com.restendpoints;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.dbutils.UserFinderInterface;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;

@Path("/findUser")
@RequestScoped
public class FindUserRest {
	
	@EJB
	UserFinderInterface uf;
	
	
	@GET
	@Path("/getU/{type}/{value}")
	public String sendInfo(@PathParam("type") String type,@PathParam("value") String value) {
		System.out.println("Pogodio REST "+type + value);
		return uf.searchForUser(type, value);
	}
}
