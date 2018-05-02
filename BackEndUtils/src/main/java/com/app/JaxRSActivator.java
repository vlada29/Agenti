package com.app;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.model.Host;

@Singleton
@Startup
public class JaxRSActivator {
    private static InetAddress ip;
    private static String hostname;
    
    @PostConstruct
    public void init() throws UnknownHostException{
    	ip = InetAddress.getLocalHost();
    	hostname = ip.getHostName();
    	System.out.println("IP: " + ip);
    	System.out.println("ADDRESS: " + ip.toString().split("/")[1] +", HOSTNAME: "+hostname);
    }
    
    public static Host getHost(){
    	return new Host(ip.toString().split("/")[1], hostname);
    }
}
