package com.rest;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.client.ClientProtocolException;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.client.core.BaseClientResponse;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import com.app.RunHandshake;
import com.db.INodeUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.model.Agent;
import com.model.AgentType;
import com.model.AgentskiCentar;

@Stateless
@LocalBean
@Path("/node")
public class NodeEndpoint implements RemoteNodeEndpoint{
	
	@EJB
	INodeUtils centerUtils;
	
	@GET
	public AgentskiCentar getNode() {
		//GET /node
		System.out.println("OK");
		return null;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public void registerNode(AgentskiCentar newCenter) {
		System.out.println("Registracija novog cvora, with address: " + newCenter.getAddress());
		centerUtils.addNode(newCenter);
		RunHandshake.centriips.add("79.175.95.73");
		//RunHandshake.centriips.add(newCenter.getAddress());
		
		//GET /agents/classes	 
		String s = "";
		try {
			//s = read("http://7fbd0a86.ngrok.io/SecondPhase/rest/agents/test");
			//s = read("http://" + newCenter.getAddress() + ":8080/SecondPhase/rest/agents/test");
			s = read("http://6de06cf7.ngrok.io/SecondPhase/rest/agents/classes");
		} catch (Exception e) {
			 
			e.printStackTrace();
		}
		 
		System.out.println("Dobavljeni tipovi sa non mastera: " + s);
		Gson gson = new Gson();
		Collection<Agent> agents  = gson.fromJson(s, new TypeToken<List<Agent>>(){}.getType());
		
		for(Agent a : agents) {
			System.out.println(a.getAid().toString());
			centerUtils.getSupportedTypes().add(a.getAid().getType());
		}
		
		
		//updateAllCenters(newCenter);
		
	}
	//POST /node
	public void updateAllCenters(AgentskiCentar newCenter){
		for(AgentskiCentar ac : centerUtils.getCenters()){
			ResteasyClient client = new ResteasyClientBuilder().build();
			ResteasyWebTarget target = client.target("http://" + ac.getAddress() + ":8080/SecondPhase/rest/node");
			Response response = target.request().post(Entity.entity(newCenter, MediaType.APPLICATION_JSON));	 
		}
	}
	
	//POST /agent/classes
	public void updateWithNewTypes(Collection<Agent> agents){
		if(agents.size()>0) { //azuriraj sve cvorose s novim tipovima
			for(AgentskiCentar ac : centerUtils.getCenters()){
				ResteasyClient client = new ResteasyClientBuilder().build();
				ResteasyWebTarget target = client.target("http://" + ac.getAddress() + ":8080/SecondPhase/rest/agent/classes");
				Response response = target.request().post(Entity.entity(agents, MediaType.APPLICATION_JSON));	 
			}
		}
	}
	
	//POST /node
	public void updateWithNewCenters(Collection<AgentskiCentar> centers){
		if(centers.size()>0) {
			for(AgentskiCentar ac : centerUtils.getCenters()){
				ResteasyClient client = new ResteasyClientBuilder().build();
				ResteasyWebTarget target = client.target("http://" + ac.getAddress() + ":8080/SecondPhase/rest/node");
				Response response = target.request().post(Entity.entity(centers, MediaType.APPLICATION_JSON));	 
			}
		}
	}
	
	//POST /agents/classes
	public void updateNewNodeWithTypes(Collection<AgentType> types, AgentskiCentar new_ac){
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target("http://" + new_ac.getAddress() + ":8080/SecondPhase/agent/classes");
		Response response = target.request().post(Entity.entity(types, MediaType.APPLICATION_JSON));	 
		 
	}
	
	//POST /agents/running
	public void updateRunningAgents(Collection<AgentType> types, AgentskiCentar new_ac){
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target("http://" + new_ac.getAddress() + ":8080/SecondPhase/agent/classes");
		Response response = target.request().post(Entity.entity(types, MediaType.APPLICATION_JSON));	 
		 
	}
	
	
	
	public String read(String url) throws Exception {
		

				ClientRequest request = new ClientRequest(url);
				request.accept("application/json");
				ClientResponse<String> response = request.get(String.class);

				if (response.getStatus() != 200) {
					throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
				}

				BufferedReader br = new BufferedReader(new InputStreamReader(
					new ByteArrayInputStream(response.getEntity().getBytes())));

				String output;
				String ret = "";
				System.out.println("Output from Server .... \n");
				while ((output = br.readLine()) != null) {
					System.out.println(output);
					ret+=output;
				}
				return ret;
			  

	}
	
	@DELETE
	@Path("/{alias}")
	public void removeNode(@PathParam("alias") String alias) {
		System.out.println("Removing: "+alias);
		if(centerUtils.removeNode(alias)) {
			for(AgentskiCentar ac: centerUtils.getCenters()) {
				if(!ac.getAlias().equals("master")) {
					System.out.println("Removing on node: "+ac.getAlias());
					ResteasyClient client = new ResteasyClientBuilder().build();
					ResteasyWebTarget target = client.target(ac.getAddress()+":8080/PhaseTwo/rest/node/"+alias);
					Response r = target.request().delete();
					System.out.println(r.toString());
				}
			}	
		}
	}
	
	

}
