package com.jms;

import javax.ejb.Local;
import javax.jms.MessageListener;

@Local
public interface JMSProxyInt {
	MessageListener getReciever();
}
