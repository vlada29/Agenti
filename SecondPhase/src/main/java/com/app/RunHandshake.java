package com.app;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Schedules;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timer;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
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
	public static ArrayList<String> centriips = new ArrayList<String>(Arrays.asList("79.175.95.73"));
	// @Schedule(hour = "*", minute = "*", persistent = false)
    protected void init(Timer timer)
    {
       tryHandshake();
    //   timer.cancel();
    }
	
	
	//@PostConstruct
	public void tryHandshake() {
		Thread thread = new Thread() {
	        @Override
	        public void run() {
			System.out.println("Try to register on master");
			ResteasyClient client = new ResteasyClientBuilder().build();
			//ResteasyWebTarget target = client.target("http://192.168.102.61:8080/PhaseTwo/rest/node/");
			ResteasyWebTarget target = client.target("http://90a7ba91.ngrok.io/SecondPhase/rest/node");	
			target.request().get();
			
			
			try {
				System.out.println(getIp());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tryRegister();
	        }
		};
		thread.start();
	}
	
	public void tryRegister(){
		System.out.println("Try to register on master");
		ResteasyClient client = new ResteasyClientBuilder().build();
		//ResteasyWebTarget target = client.target("http://192.168.102.61:8080/PhaseTwo/rest/node/");
		ResteasyWebTarget target = client.target("http://6de06cf7.ngrok.io/SecondPhase/rest/node");	
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
	@PreDestroy
	public void preDestroy() throws IllegalArgumentException, NullPointerException, IOException{
		//u non master-u
//		ResteasyClient client = new ResteasyClientBuilder().build();
//		//master ip
//		ResteasyWebTarget target = client.target("http://6de06cf7.ngrok.io/SecondPhase/rest/node/"+getIp());	
//		target.request().delete();
	}
	
	@Schedules({@Schedule(hour = "*", minute = "*", second = "*/15")})
	public void heartbeat(){
		String s = "";
		String fordelete = null;
		 for(String ip : centriips) {
		try {
 //90a7ba91
			s = read("http://6de06cf7.ngrok.io/SecondPhase/rest/node");
			System.out.println("At " + new java.util.Date()+", Alive nodes are:");
			if(s==null) {
			 
				System.out.println("Trying again: Node " +  ip);
				s = read("http://6de06cf7.ngrok.io/SecondPhase/rest/node");
				
				if(s==null) {
					System.out.println("Node "+ ip+" is down!");
					fordelete = ip;
				}
				 
				System.out.println("No alive nodes!");
				//centerUtils.removeNode(ip);
				
			} else {
				 
				System.out.println( ip+" is alive");
			}
		} catch (Exception e) {
			 
			e.printStackTrace();
		}
		 }
		
		 if(fordelete!=null) {
			 centriips.remove(fordelete);
		 }
		 if(centriips.size()==0)
			 System.out.println(new java.util.Date()+" - No nodes alive!");
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
	
	public String read(String url) throws Exception {
		

		ClientRequest request = new ClientRequest(url);
		request.accept("application/json");
		ClientResponse<String> response = request.get(String.class);
				
		if (response.getStatus() != 200 && response.getStatus()!=204) {
			//throw new RuntimeException("Failed : HTTP error code : "
			//	+ response.getStatus());
			return null;
		}
		String ret = "";
		if(response.getStatus()!=204) {
			BufferedReader br = new BufferedReader(new InputStreamReader(
				new ByteArrayInputStream(response.getEntity().getBytes())));
	
			String output;
			 
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
				ret+=output;
			}
		 }
		return ret;
	  

}
}
