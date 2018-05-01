//package com.messagerecievers;
//
//import javax.ejb.ActivationConfigProperty;
//import javax.ejb.EJB;
//import javax.ejb.MessageDriven;
//import javax.jms.Message;
//import javax.jms.MessageListener;
//import javax.jms.TextMessage;
//
//import com.dbutils.UserFinder;
//@MessageDriven(activationConfig = {
//		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
//		@ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/userControlQueue")})
//public class FindUserReciever implements MessageListener {
//
//	@EJB
//	UserFinder uf;
//	
//	
//	@Override
//	public void onMessage(Message arg0) {
//		TextMessage tmsg = (TextMessage)arg0;
//		
//		System.out.println("Primo poruku ");
//	
//	}
//	
//	
//	
//}
