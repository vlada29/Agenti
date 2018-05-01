package com.dbutils;

import javax.ejb.Stateless;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import com.interfaces.RegisterInterface;
@Stateless
public class Register implements RegisterInterface {

	@Override
	public String register(String username, String password, String firstname, String lastname) {
		ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target("http://localhost:8080/user-app/jaxrs/restendpoints/register/"+username+"/"+password+"/"+firstname+"/"+lastname);
        Response response = target.request().get();
        String ret = response.readEntity(String.class);
        System.out.println("Register success? -> " + ret);

        if(ret!=null) {
        	return ret;
        }else {
        	return null;
        }
	}

}
