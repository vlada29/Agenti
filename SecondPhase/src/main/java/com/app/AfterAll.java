package com.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.Timer;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import com.model.AgentskiCentar;

@Stateless
public class AfterAll {
    @Schedule(hour = "*", minute = "*", persistent = false)
    protected void init(Timer timer)
    {
     
       tryHandshake();
       timer.cancel();
    }
    
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
			ac.setAlias("NonMasterNode1");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.entity(ac, MediaType.APPLICATION_JSON));
		//List<AgentskiCentar> centres = response.readEntity(new GenericType<List<AgentskiCentar>>(){});
		// TODO update all
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
