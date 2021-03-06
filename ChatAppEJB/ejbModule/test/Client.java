package test;

import java.io.File;
import java.util.Hashtable;
import java.util.Properties;

import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

import imp.HelloBean;
import interfaces.Hello;

public class Client {
    public static void main(String[] args) throws NamingException {
    	try {
			Context context = new InitialContext();

			System.err.println("Looking up for: " + LOOKUP_STRING);
			Hello bean = (Hello) context.lookup(LOOKUP_STRING);
			//context.close();
			System.out.println("bean je: " + bean);
	        System.out.println(bean.sayHello());

	        
		} catch (NamingException e) {
			e.printStackTrace();
		}

    }
    private static final String LOOKUP_STRING = "ejb:/ChatAppEJB/HelloBean!"+Hello.class.getName();
    
//    //static calls
//    private static Hello doLookup() {
//        Context context = null;
//        Hello bean = null;
//        try {
//            // 1. Obtaining Context
//            context = getInitialContext();
//            
//            // 2. Lookup and cast
//            //System.out.println(context);
//           // System.out.println(LOOKUP_STRING);
//         
//            bean = (Hello) context.lookup(LOOKUP_STRING);
//            System.out.println("bean je: " + bean);
//        } catch (NamingException e) {
//        	System.out.println("exception");
//            e.printStackTrace();
//        }
//        return bean;
//    }
//
//    //private static final String LOOKUP_STRING = "ejb:app/ChatAppEAR/ejbModule/Hello!com.chatapp.interfaces.RemoteInt";
//   // private static final String LOOKUP_STRING = "ejb:ChatAppEAR/ChatAppEJB//HelloBean!"+Hello.class.getName();
//    
////    "ejb:ChatAppEAR/ChatAppEJB//StarterBean!"
////	+ StarterRemote.class.getName();
//    		//java:global/emyed-ear/emyed-ejb-1.0/WhiteBoardService!com.zreflect.emyed.ejb.interfaces.IWhiteBoardServiceRemote
//    /*
//     * location of JBoss JNDI Service provider the client will use. It should be
//     * URL string.
//     */
//    private static final String PROVIDER_URL = "jnp://localhost:8080";
// 
//    /*
//     * specifying the list of package prefixes to use when loading in URL
//     * context factories. colon separated
//     */
//    private static final String JNP_INTERFACES = "org.jboss.naming:org.jnp.interfaces";
// 
//    /*
//     * Factory that creates initial context objects. fully qualified class name.
//     */
//    private static final String INITIAL_CONTEXT_FACTORY = "org.jnp.interfaces.NamingContextFactory";
// 
//    private static Context initialContext;
// 
//    public static Context getInitialContext() throws NamingException {
//        if (initialContext == null) {
//            // Properties extends HashTable
//            Properties prop = new Properties();
//            prop.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
//            prop.put(Context.URL_PKG_PREFIXES, JNP_INTERFACES);
//            prop.put(Context.PROVIDER_URL, PROVIDER_URL);
//            initialContext = new InitialContext(prop);
//        }
//        return initialContext;
//    }
 
}
