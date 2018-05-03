package com.dbutils;

import javax.ejb.Stateless;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import com.interfaces.LogoutInterface;
@Stateless
public class Logout implements LogoutInterface{

	@Override
	public void logout(String username) {
		ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target("http://localhost:8080/user-app/jaxrs/restendpoints/logout/"+username);
        Response response = target.request().get();
        String ret = response.readEntity(String.class);
        System.out.println("Logout: " + ret);
	}

}
