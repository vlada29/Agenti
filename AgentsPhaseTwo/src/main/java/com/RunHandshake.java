package com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import com.db.INodeUtils;
import com.model.AgentskiCentar;
 
 

@Startup
@Singleton
public class RunHandshake {
	@EJB
	INodeUtils centerUtils;
	
	@PostConstruct
	public void tryHandshake() throws IOException {
		System.out.println(getIp());
		tryRegister();
	}
	
	public void tryRegister() throws IOException{
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target("http://192.168.102.61:8080/PhaseTwo/rest/node/");
		AgentskiCentar ac = new AgentskiCentar();
		ac.setAddress(getIp());
		Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.entity(ac, MediaType.APPLICATION_JSON));
		List<AgentskiCentar> centres = response.readEntity(new GenericType<List<AgentskiCentar>>(){});
		// TODO update all
	}
	
	public String getIp() throws IOException{
		URL whatismyip = new URL("http://checkip.amazonaws.com");
		BufferedReader in = new BufferedReader(new InputStreamReader(
		                whatismyip.openStream()));

		String ip = in.readLine(); //you get the IP as a String
    	System.out.println("IP: " + ip);
    	
    	//ResteasyClient c = new ResteasyClientBuilder().build();				//TODO change port
       // ResteasyWebTarget target_chat_app = c.target("http://"+h.getAddress()+"/websocket-example/ChatAppRestEndPoint/registerNewNode");
      //  Response response_activated = target_chat_app.request().post(Entity.entity(h, "application/json"));
    	
    	return ip;
	}
}