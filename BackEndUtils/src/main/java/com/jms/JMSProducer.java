package com.jms;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
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

@Stateless
public class JMSProducer implements JMSInterface {

    @Override
    public String register(String username, String password)
            throws JMSException {
        String message = "";
        
        return message;
    }

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
		
        return message;
    }

    @Override
    public String logout(User user) throws JMSException {
    	String message = "";
        
        return message;
    }


}
