package com.jms;


import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JMSInitializer {

	
	public static void main(String[] args) throws NamingException, JMSException {
		Context context = new InitialContext();
		ConnectionFactory cf = (ConnectionFactory) context.lookup("jms/RemoteConnectionFactory"); //TODO razlika
		final Queue queue = (Queue) context.lookup("jms/queue/testqueue");
		context.close();
		Connection connection = cf.createConnection();//"guest", "guestguest");
		final Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);

		MessageConsumer consumer = session.createConsumer(queue);
		
		MessageListener ml = new JMSReciever2();
		
		consumer.setMessageListener(ml);
		
		
		connection.start();
		
		
		
	}

}
