package imp;

import javax.ejb.Stateless;

import interfaces.Hello;
@Stateless
public class HelloBean implements Hello{
	
	HelloBean(){}
	
	@Override
	public String sayHello() {
		return "EJB WildFly9 ChatApp";
	}

}
