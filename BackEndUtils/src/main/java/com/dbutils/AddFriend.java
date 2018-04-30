package com.dbutils;

import javax.ejb.Stateless;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import com.interfaces.AddFriendInterface;

@Stateless
public class AddFriend implements AddFriendInterface {

	@Override
	public boolean addFriend(String user, String friend) {

		ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target("http://localhost:8080/user-app/jaxrs/addFriend/add/"+user+"/"+friend);
        Response response = target.request().get();
        String ret = response.readEntity(String.class);
        System.out.println("Dodavanje uspesno? -> " + ret);
		
        if(ret.equals("OK")) {
        	return true;
        }else {
        	return false;
        }
	}

}
