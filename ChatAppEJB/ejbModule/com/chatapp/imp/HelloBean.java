package com.chatapp.imp;

import javax.ejb.Stateless;

import com.chatapp.interfaces.Hello;
@Stateless
public class HelloBean implements Hello{
	
	HelloBean(){}
	
	@Override
	public String sayHello() {
		return "EJB WildFly9 ChatApp";
	}

}
