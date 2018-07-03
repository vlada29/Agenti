package com.rest;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
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

import com.db.INodeUtils;
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
		System.out.println("OK");
		return null;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public void registerNode(AgentskiCentar newCenter) {
		System.out.println("Registracija novog cvora, with address: " + newCenter.getAddress());
		centerUtils.addNode(newCenter);
		//GET /agents/classes
		//ResteasyClient client = new ResteasyClientBuilder().build();
		//ResteasyWebTarget target = client.target("http://" + newCenter.getAddress() + ":8080/SecondPhase/rest/agents/classes");	
		//ResteasyWebTarget target = client.target("http://acd24056.ngrok.io/SecondPhase/rest/agents/test");		
		//Response response = target.request(MediaType.APPLICATION_JSON).get();
		//String ret = "";
				 
		//System.out.println("ne master odgovorio" + "");
		
		
		//GET /agents/classes
		//ResteasyClient client = new ResteasyClientBuilder().build();
		//target = client.target("http://" + newCenter.getAddress() + ":8080/SecondPhase/rest/agents/classes");		
		//target = client.target("http://acd24056.ngrok.io/SecondPhase/rest/agents/classes");
		String s = "";
		try {
			s = read("http://acd24056.ngrok.io/SecondPhase/rest/agents/test");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//String s = target.request().get(Object.class).toString();
		System.out.println("Ajde: " + s);
		
		//BaseClientResponse<?> r = (BaseClientResponse<?>) response;
		// System.out.println("Response Types: " + response.getEntity(String.class));
		//System.out.println("Klase: " + r.getEntity(String.class));
		//ArrayList<AgentType> types = (ArrayList<AgentType>) r.cge
		
		
		//updateAllCenters(newCenter);
		
	}
	
	public void updateAllCenters(AgentskiCentar newCenter){
		for(AgentskiCentar ac : centerUtils.getCenters()){
			if(!ac.getAlias().equals(centerUtils.getMasterAlias())){
				ResteasyClient client = new ResteasyClientBuilder().build();
				ResteasyWebTarget target = client.target("http://" + ac.getAddress() + ":8080/PhaseTwo/rest/node");
				Response response = target.request().post(Entity.entity(newCenter, MediaType.APPLICATION_JSON));
			}
		}
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
