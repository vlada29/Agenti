package com.rest.testpackage;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("jaxrs")
public class TestRest {
	@GET
	@Path("/test")
	public Response test(){
		return Response.status(200).entity("Rest works!").build();
	}
}
