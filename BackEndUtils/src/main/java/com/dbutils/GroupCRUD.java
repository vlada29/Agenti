package com.dbutils;

import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import com.interfaces.GroupCRUDInterface;
import com.model.Grupa;
import com.model.Message;

@Stateless
public class GroupCRUD implements GroupCRUDInterface{
	@Override
	public String createGroup(String creator, String name, ArrayList<String> clanovi) {
		// TODO Auto-generated method stub
		Grupa grupa = new Grupa(-1, creator, name, clanovi, new ArrayList<Message>());
		System.out.println("pre resta : " + grupa.getIme());
		
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target("http://localhost:8080/user-app/jaxrs/restendpoints/createGroup");
        Response response = target.request().post(Entity.entity(grupa, "application/json"));
        String ret = response.readEntity(String.class);
        
        
        return ret;
	}

	@Override
	public String deleteGroup(String nazivGrupe) {
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target("http://localhost:8080/user-app/jaxrs/restendpoints/deleteGroup/"+nazivGrupe);
        Response response = target.request().get();
        String ret = response.readEntity(String.class);
       
        return ret;
	}

	@Override
	public String addUser(String idGrupe, String username) {
		ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target("http://localhost:8080/user-app/jaxrs/restendpoints/addToGroup/"+idGrupe+"/"+username);
        Response response = target.request().get();
        String ret = response.readEntity(String.class);
       
        return ret;
	}

	@Override
	public String removeUser(String idGrupe, String username) {
		ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target("http://localhost:8080/user-app/jaxrs/restendpoints/removeFromGroup/"+idGrupe+"/"+username);
        Response response = target.request().get();
        String ret = response.readEntity(String.class);
       
        return ret;
	}

	@Override
	public String leaveGroup(String idGrupe, String username) {
		ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target("http://localhost:8080/user-app/jaxrs/restendpoints/leaveGroup/"+idGrupe+"/"+username);
        Response response = target.request().get();
        String ret = response.readEntity(String.class);
       
        return ret;
	}
}
