package com.dbutils;

import javax.ejb.Stateless;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import com.interfaces.GroupFinderInterface;

@Stateless
public class GroupFinder implements GroupFinderInterface {

	@Override
	public String getGroups(String user) {
		
		ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target("http://localhost:8080/user-app/jaxrs/getGroups/get/"+user);
        Response response = target.request().get();
        String ret = response.readEntity(String.class);
        System.out.println("Pronadjene grupe? -> " + ret);
		
		
		
		return ret;
	}

}
