package com.messagerecievers;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@MessageDriven(
        activationConfig = {
                @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
                @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
                @ActivationConfigProperty(propertyName = "destination", propertyValue = "topic/mojTopic")
        }
)
public class JMSReciever implements MessageListener {
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
