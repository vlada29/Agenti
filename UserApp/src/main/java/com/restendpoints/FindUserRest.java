package com.restendpoints;

import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;

@Path("/findUser")
public class FindUserRest {

	
	@GET
	@Path("/getU")
	public String sendInfo() {
		System.out.println("Pogodio REST");
		return "Pogodio REST";
	}
}
