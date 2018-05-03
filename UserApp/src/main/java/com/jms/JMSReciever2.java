package com.jms;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;

import com.interfaces.UserFinderInterface;

@MessageDriven(
        activationConfig = {
                @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
                @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
                @ActivationConfigProperty(propertyName = "destination", propertyValue = "topic/mojTopic")
        }
)
public class JMSReciever2 implements MessageListener{
	
	@EJB
	UserFinderInterface uf;
	
	@Override
    public void onMessage(Message msg) {
		
		
    	 if (msg instanceof TextMessage) {
 			TextMessage tm = (TextMessage) msg;
 			try {
// 				Context context = new InitialContext();
// 	        	ConnectionFactory cf = (ConnectionFactory) context.lookup("jms/RemoteConnectionFactory"); //TODO razlika
// 	        	
// 	        	context.close();
// 	        	Connection connection = cf.createConnection();//"guest", "guestguest");
// 	        	session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
// 				
// 	        	connection.start();
 				
 				String text = tm.getText();
 				System.out.println("Received new message user app : " + text);
 				
 				String[] split = text.split(",");
 				
 				
 				
 				
 				String userJson = uf.searchForUser(split[0], split[1]);
 				
 				System.out.println("Pronadjen korisnik: " + userJson);
// 				
// 				repQ = msg.getJMSReplyTo();
//                producer = session.createProducer(repQ);
//                
//                Message requestMessage = session.createTextMessage(userJson);
//                
//                requestMessage.setJMSCorrelationID(msg.getJMSMessageID());
//                producer.send(requestMessage);
 				
 				
 			} catch (Exception e) {
 				e.printStackTrace();
 			}
 		}

    }
	
}
