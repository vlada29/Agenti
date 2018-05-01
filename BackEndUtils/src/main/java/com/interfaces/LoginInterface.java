package com.interfaces;

import javax.ejb.Local;
import javax.jms.JMSException;
import javax.naming.NamingException;

@Local
public interface LoginInterface {
	String login(String username, String password) throws JMSException, NamingException;
}
