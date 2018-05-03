package com.restendpoints;


import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;


import com.app.JaxRSActivator;
import com.model.User;



@Path("/ChatAppRestEndPoint")
@RequestScoped
public class ChatAppRestEndPoint {

	@POST
	@Consumes("application/json")
	@Path("/updateActiveUsers")
	public void updateActiveUsers(User u) {
		JaxRSActivator.activeUsers.add(u);
		
		System.out.println("Aktivni: ");		
		
		for(User user : JaxRSActivator.activeUsers)
			System.out.println(user.getUsername()+", ");
	}
	
	@POST
	@Consumes("application/json")
	@Path("/removeActiveUsers")
	public void removeActiveUsers(User u) {
		JaxRSActivator.activeUsers.remove(u);
		
		System.out.println("Aktivni: ");		
		
		for(User user : JaxRSActivator.activeUsers)
			System.out.println(user.getUsername()+", ");
	}
	
	
}
