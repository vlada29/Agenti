package com.restendpoints;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import com.app.JaxRSActivator;
import com.model.Host;
import com.model.User;
import com.xmlconfig.XMLParser;



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
	
	@GET
	@Path("/removeActiveUsers/{user}")
	public void removeActiveUsers(@PathParam("user") String username) {
		
		
		for(User user : JaxRSActivator.activeUsers) {
			if(user.getUsername().equals(username)) {
				JaxRSActivator.activeUsers.remove(user);
				break;
			}
		}
	}
	
	
	@POST
	@Consumes("application/json")
	@Path("/registerNewNode")
	public void registerNewNode(Host h) throws IOException {
		//Dodajemo novi node u cluster
		
		XMLParser xmlp = new XMLParser();
		xmlp.addNewHost(h);
		
		
		
		URL whatismyip = new URL("http://checkip.amazonaws.com");
		BufferedReader in = new BufferedReader(new InputStreamReader(
		                whatismyip.openStream()));

		String ip = in.readLine(); //you get the IP as a String
		
		System.out.println("Registrovan node "+h.getAddress() + " u nodu "+ip);

		Host ho = xmlp.getHostByAlias("Master");
		
		//ako smo u master nodu prosledjujemo node svim ostalima
		if(ho.getAddress().equals(ip+":8080")) {
			for(Host host : xmlp.getAllHosts()) {
				//ako smo u master nodu prosledjujemo node svim ostalima
				if(!host.getAddress().equals(h.getAddress())) {
					ResteasyClient c = new ResteasyClientBuilder().build();				//TODO change port
			        ResteasyWebTarget target_chat_app = c.target("http://"+host.getAddress()+"/websocket-example/ChatAppRestEndPoint/registerNewNode");
			        Response response_activated = target_chat_app.request().post(Entity.entity(h, "application/json"));
				}
			}
			
			//i prosledjujemo ostale nodove novom nodu
			
			for(Host host : xmlp.getAllHosts()) {
				if(!host.getAddress().equals(h.getAddress())) {
					ResteasyClient c = new ResteasyClientBuilder().build();				//TODO change port
			        ResteasyWebTarget target_chat_app = c.target("http://"+h.getAddress()+"/websocket-example/ChatAppRestEndPoint/registerNewNode");
			        Response response_activated = target_chat_app.request().post(Entity.entity(host, "application/json"));
				}
			}
			
		}
		
		
		
		
	}
	
	
}
