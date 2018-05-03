package com.jms;
import javax.ejb.EJB;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;


/**
 * 
 * @author minja
 *
 */


public class JMSTopic  {
	
	
	public JMSTopic() {
		try {
			Context context = CtxFactory.get();
			ConnectionFactory cf = (ConnectionFactory) context.lookup("jms/RemoteConnectionFactory"); //TODO razlika
			final Topic topic = (Topic) context.lookup("jms/topic/mojTopic");
			context.close();
			Connection connection = cf.createConnection();//"guest", "guestguest");
			final Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);




			
			
			
			
//			MessageConsumer consumer = session.createConsumer(topic);
//			
//			MessageListener ml = new JMSConsumer();
//			
//			consumer.setMessageListener(ml);
//			
//			
			connection.start();
			
			
			
			
			// create and publish a message
			TextMessage msg = session.createTextMessage();
			msg.setText("username,User1");
			
			
			
			MessageProducer producer = session.createProducer(topic);
			producer.send(msg);
			
			
			
			
			Thread.sleep(1000);
			System.out.println("Message published. Please check application server's console to see the response from MDB.");
			
//			producer.close();
//			consumer.close();
//			connection.stop();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new JMSTopic();
	}

//	public void onMessage(Message msg) {
//		System.out.println("Received a message.");
//		if (msg instanceof TextMessage) {
//			TextMessage tm = (TextMessage) msg;
//			try {
//				String text = tm.getText();
//				System.out.println("Received new message : " + text);
//			} catch (JMSException e) {
//				e.printStackTrace();
//			}
//		}
//	}
}
