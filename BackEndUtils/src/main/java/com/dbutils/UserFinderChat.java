package com.dbutils;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;

import org.bson.Document;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import static com.mongodb.client.model.Filters.eq;

@Stateless
public class UserFinderChat implements UserFinderInterfaceChat{

	@Override
	public String searchForUser(String searchBy, String value) {
		System.out.println("Dosao u search for user");
		ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target("http://localhost:8080/UserApp/jaxrs/findUser/getU");
        Response response = target.request().get();
        String ret = response.readEntity(String.class);
        System.out.println("Pronadjen user: " + ret);
        return ret;
	}
	
	
}
