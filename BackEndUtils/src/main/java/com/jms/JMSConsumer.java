package com.jms;
import com.google.gson.Gson;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import javax.websocket.Session;
import java.io.IOException;
import java.util.List;

@MessageDriven(
        activationConfig = {
                @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
                @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
                @ActivationConfigProperty(propertyName = "destination", propertyValue = "topic/mojTopic")
        }
)
public class JMSConsumer implements MessageListener {

    @Override
    public void onMessage(Message msg) {
    	 System.out.println("Consumer primio novu poruku.");
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
