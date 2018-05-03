package com.jms;

import javax.jms.MessageListener;

public class JMSProxy implements JMSProxyInt {
	
	@Override
	public MessageListener getReciever() {
		return new JMSReciever2();
	}

}
