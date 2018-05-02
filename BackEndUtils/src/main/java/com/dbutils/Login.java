package com.dbutils;

import java.util.ArrayList;


import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import com.app.JaxRSActivator;
import com.google.gson.Gson;
import com.interfaces.LoginInterface;
import com.jms.JMSInterface;
import com.jms.JMSProducer;
import com.jms.JMSTopic;
import com.model.Host;
import com.model.User;
import com.xmlconfig.XMLParser;
@Stateless
public class Login implements LoginInterface{
	//@EJB
	//JMSInterface jmsi;

	//private ArrayList<String> activeUsers;
	
	@SuppressWarnings("unused")
	@Override
	public String login(String username, String password) throws JMSException, NamingException {
		String ret = null;
		if(!StartUpBean.getHost().getAlias().equals("Master")){
			System.out.println("Non master.");
			
			ResteasyClient client = new ResteasyClientBuilder().build();
	        ResteasyWebTarget target = client.target("http://localhost:8080/user-app/jaxrs/restendpoints/login/"+username+"/"+password);
	        Response response = target.request().get();
	        ret = response.readEntity(String.class);
	        System.out.println("Login as: " + ret);
	        
	        //temporary
	        //activeUsers.add((new Gson().fromJson(ret, User.class)).getUsername());
	        //
	        
	        //System.out.println("Active users:");
			//for(String s : activeUsers)
			//	System.out.println(s+", ");
		} else {
			System.out.println("Master.");
			//TODO JMS instead REST
			
			ResteasyClient client = new ResteasyClientBuilder().build();
	        ResteasyWebTarget target = client.target("http://localhost:8080/user-app/jaxrs/restendpoints/login/"+username+"/"+password);
	        Response response = target.request().get();
	        ret = response.readEntity(String.class);
	        System.out.println("Login as: " + ret);
	        ///////////////////////////////////////////////////////
			
	        //azuriraj
	        //activeUsers.add((new Gson().fromJson(ret, User.class)).getUsername());
			//System.out.println("Active users:");
			//for(String s : activeUsers)
				//System.out.println(s+", ");
			
			System.out.println("Notify ChatApp nodes:");
        	XMLParser parser = new XMLParser();
        	for(Host h : parser.getAllHosts()){
        		//rest
    			System.out.println("Rest to " + h.toString() );
    			ResteasyClient client_for_chat_app = new ResteasyClientBuilder().build();				//TODO change port
    	        ResteasyWebTarget target_chat_app = client_for_chat_app.target("http://"+h.getAddress()+":8080/websocket-example/ChatAppRestEndPoint/updateActiveUsers/"+username);
    	        Response response_activated = target_chat_app.request().get();
    	        String activated = response_activated.readEntity(String.class);
 
        	}
			

			
		}
		
		//vracanje na websocket i azuriranje angulara
        return ret;
	}

}
