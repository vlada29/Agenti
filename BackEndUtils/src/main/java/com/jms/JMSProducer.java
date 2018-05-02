package com.jms;
import java.util.Hashtable;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.model.Host;
import com.model.User;


public class JMSProducer implements JMSInterface, MessageListener {

    @Override
    public String register(String username, String password)
            throws JMSException {
        String message = "";
        
        return message;
    }
    public JMSProducer(){}
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public String login(String username, String password, Host host)
            throws JMSException, NamingException {
        String message = "";
       
//        Context context = new InitialContext();
//        ConnectionFactory cf = (ConnectionFactory) context
//				.lookup("jms/RemoteConnectionFactory");
//        final Topic topic = (Topic) context
//				.lookup("jms/topic/mojTopic");
//        context.close();
//        Connection connection = cf.createConnection();
//        final Session session = connection.createSession(false,
//				Session.AUTO_ACKNOWLEDGE); 
//        connection.start();
//        
//        
//        
//		TextMessage msg = session.createTextMessage();
//		msg.setText("Zeli da se loginuje!");
//		MessageProducer producer = session.createProducer(topic);
//		producer.send(msg);
//     			
//		producer.close();
//		connection.stop();
        
//        Hashtable env = new Hashtable();
//		env.put("java.naming.factory.initial",
//				"org.jboss.naming.remote.client.InitialContextFactory");
//		env.put("java.naming.provider.url",
//		     "http-remoting://localhost:8080");
//		
//		Context ctx = new InitialContext(env);
//		ConnectionFactory cf = (ConnectionFactory) ctx.lookup("jms/RemoteConnectionFactory");
//		final Topic topic = (Topic) ctx.lookup("jms/topic/myTopic");
//		ctx.close();
//		
//		Connection connection = cf.createConnection();
//        final Session session = connection.createSession(false,
//				Session.AUTO_ACKNOWLEDGE); 
//        connection.start();
//        
//        
//        MessageConsumer consumer = session.createConsumer(topic);
//		consumer.setMessageListener(this);
//		TextMessage msg = session.createTextMessage("Topic message!");
//        
//        
//		MessageProducer producer = session.createProducer(topic);
//		producer.send(msg);
//        
//		session.commit();
//        
//        
//		producer.close();
//		consumer.close();
//		connection.stop();
//		connection.close();
		
        
        
        Context context = new InitialContext();
		ConnectionFactory cf = (ConnectionFactory) context
				.lookup("jms/RemoteConnectionFactory");
		final Topic topic = (Topic) context
				.lookup("jms/topic/mojTopic");
		context.close();
		Connection connection = cf.createConnection();//"guest", "guestguest");
		final Session session = connection.createSession(false,
				Session.AUTO_ACKNOWLEDGE);

		connection.start();

		
		
		
		
		
		
		
		
		
		
		MessageConsumer consumer = session.createConsumer(topic);
		consumer.setMessageListener(this);
		
		
		
		// create and publish a message
		TextMessage msg = session.createTextMessage();
		msg.setText("Text!!!");
		MessageProducer producer = session.createProducer(topic);
		producer.send(msg);
		

		
		producer.close();
		consumer.close();
		connection.stop();
        
        
        
        
        return message;
    }

    @Override
    public String logout(User user) throws JMSException {
    	String message = "";
        
        return message;
    }

	@Override
	public void onMessage(Message msg) {
		System.out.println("Primio poruku u JMSProducer");
		if (msg instanceof TextMessage) {
			TextMessage tm = (TextMessage) msg;
			try {
				String text = tm.getText();
				System.out.println("Received new message : " + text);
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
		
	}


}
