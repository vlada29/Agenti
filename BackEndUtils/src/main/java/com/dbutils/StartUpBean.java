package com.dbutils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import com.model.Host;

@Singleton
@Startup
@LocalBean
public class StartUpBean {
	private static InetAddress ip;
    private static String hostname;
    
    private ArrayList<String> activeUsers;
    
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
    
    public ArrayList<String> getActiveUsers() {
		return activeUsers;
	}

	public void setActiveUsers(ArrayList<String> activeUsers) {
		this.activeUsers = activeUsers;
	}
}	
