package interfaces;

import javax.ejb.Local;
import javax.ejb.Remote;

@Local
public interface Hello {
 
    public String sayHello();
}
