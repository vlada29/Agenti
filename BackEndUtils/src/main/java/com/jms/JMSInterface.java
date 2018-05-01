package com.jms;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.naming.NamingException;

import com.model.Host;
import com.model.User;

public interface JMSInterface {
    String register(String username, String password)
           throws JMSException;

    String login(String username, String password, Host host)
            throws JMSException, NamingException;

    String logout(User user) throws JMSException;

}
