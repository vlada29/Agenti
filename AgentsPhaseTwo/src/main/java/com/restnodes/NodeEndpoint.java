package com.restnodes;

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
		return null;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public void registerNode(AgentskiCentar newCenter) {
		System.out.println("New node register request, with address: " + newCenter.getAddress());
		centerUtils.addNode(newCenter);
		if(false){
			
			
			ResteasyClient client = new ResteasyClientBuilder().build();
			ResteasyWebTarget target = client.target("http://" + newCenter.getAddress() + ":8080/PhaseTwo/rest/agentskiCentar/agents/classes");		
			Response response = target.request(MediaType.APPLICATION_JSON).get();
			List<AgentType> agentTypes = (List<AgentType>)response.readEntity(new GenericType<List<AgentType>>(){});
			
			updateAllCenters(newCenter);
		}
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
