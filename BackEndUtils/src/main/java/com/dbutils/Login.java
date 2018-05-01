package com.dbutils;

import javax.ejb.EJB;
import javax.ejb.Stateless;
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

import com.interfaces.LoginInterface;
import com.jms.JMSInterface;
import com.model.Host;
@Stateless
public class Login implements LoginInterface{
	@EJB
	JMSInterface jmsi;
	
	@SuppressWarnings("unused")
	@Override
	public String login(String username, String password) throws JMSException, NamingException {
		if(true){
			ResteasyClient client = new ResteasyClientBuilder().build();
	        ResteasyWebTarget target = client.target("http://localhost:8080/user-app/jaxrs/restendpoints/login/"+username+"/"+password);
	        Response response = target.request().get();
	        String ret = response.readEntity(String.class);
	        System.out.println("Login success? -> " + ret);
	
	        if(ret!=null) {
	        	return ret;
	        }else {
	        	return null;
	        }
		} else {
			String ret = jmsi.login(username, password, new Host("",""));
			System.out.println("JMS search");
			if(ret!=null) {
	        	return ret;
	        }else {
	        	return null;
	        }
			
		}
	}

}
