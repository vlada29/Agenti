package com.dbutils;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.ws.rs.core.Response;

import org.bson.Document;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import com.interfaces.UserFinderInterfaceChat;
import com.jms.CtxFactory;
import com.jms.JMSFactory;
import com.jms.JMSTopic;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import static com.mongodb.client.model.Filters.eq;

@Stateless
public class UserFinderChat implements UserFinderInterfaceChat {
	
//	@EJB
//	JMSFactory jmsf;
	
	
	@Override
	public String searchForUser(String searchBy, String value) {
		System.out.println("Dosao u search for user");
		ResteasyClient client = new ResteasyClientBuilder().build();
		System.out.println("Trazimo: "+"http://localhost:8080/user-app/jaxrs/findUser/getU/");
        ResteasyWebTarget target = client.target("http://localhost:8080/user-app/jaxrs/findUser/getU/"+searchBy+"/"+value);
        Response response = target.request().get();
        String ret = response.readEntity(String.class);
        System.out.println("Pronadjen user: " + ret);
    
        /*
        try {
        	System.out.println(" Dosao u jms search for user ");
        	
        	/*Context context = new InitialContext();
        	ConnectionFactory cf = (ConnectionFactory) context.lookup("jms/RemoteConnectionFactory"); //TODO razlika
        	final Topic topic = (Topic) context.lookup("jms/topic/mojTopic");
        	final Topic topic2 = (Topic) context.lookup("jms/topic/mojTopicOdgovori");
        	
        	context.close();
        	Connection connection = cf.createConnection();//"guest", "guestguest");
        	final Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        	
        	
        	
        	
        	
        	
        	
			
			
			
			
			// create and publish a message
			TextMessage msg = session.createTextMessage();
			msg.setText(searchBy+","+value);
			msg.setJMSReplyTo(topic2);
			
			
			MessageProducer producer = session.createProducer(topic);
			producer.send(msg);
			
			// Get only that reply that matches my request message id.
            String selector = "JMSCorrelationID='" + msg.getJMSMessageID()+"'";
            
            // Create consumer with selector
            consumer = session.createConsumer(topic2, selector);
			
			
            connection.start();
            
            
            
            Message receivedMessage = consumer.receive(50000);
            if(receivedMessage != null) {
            	TextMessage tmsg = (TextMessage)receivedMessage;
                System.out.println("requestor recieved message "+tmsg.getText());
                return tmsg.getText();
            }else {
                System.out.println("No message received");
            }

            
            
            
            
            
			
//			producer.close();
//			consumer.close();
//			connection.stop();


        	
        	
        	
			
			Session session = jmsf.getSession();

			
			System.out.println("ovde 2");
			
			TextMessage msg = session.createTextMessage();
			msg.setText("username,User1");
			
			System.out.println("ovde 3");
			
			MessageProducer producer = jmsf.getDefaultProducer(session);
			producer.send(msg);
			
			System.out.println("Poruka poslata");
        	
        } catch (Exception e) {
        	System.out.println("a jel ima neki exception mozda a");
        	e.printStackTrace();
        }
        
        
        */
        
        
        
        
        return ret;
	}
	
	
}
