package com.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import com.db.INodeUtils;
import com.model.AgentskiCentar;
 
 

@Startup
@Singleton
//@LocalBean
public class RunHandshake {
	@EJB
	INodeUtils centerUtils;
	
	@PostConstruct
	public void tryHandshake() {
		System.out.println("Try to register on master");
		ResteasyClient client = new ResteasyClientBuilder().build();
		//ResteasyWebTarget target = client.target("http://192.168.102.61:8080/PhaseTwo/rest/node/");
		ResteasyWebTarget target = client.target("http://3334f2d2.ngrok.io/SecondPhase/rest/node");	
		target.request().get();
		
		
		try {
			System.out.println(getIp());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tryRegister();
	}
	
	public void tryRegister(){
		System.out.println("Try to register on master");
		ResteasyClient client = new ResteasyClientBuilder().build();
		//ResteasyWebTarget target = client.target("http://192.168.102.61:8080/PhaseTwo/rest/node/");
		ResteasyWebTarget target = client.target("http://3334f2d2.ngrok.io/SecondPhase/rest/node");	
		AgentskiCentar ac = new AgentskiCentar();
		try {
			ac.setAddress(getIp());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.entity(ac, MediaType.APPLICATION_JSON));
		//List<AgentskiCentar> centres = response.readEntity(new GenericType<List<AgentskiCentar>>(){});
		// TODO update all
	}
	@PreDestroy
	public void preDestroy(){
		 
	}
	public static String getIp() throws IOException{
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
