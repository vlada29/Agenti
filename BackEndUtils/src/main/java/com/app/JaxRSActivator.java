package com.app;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.model.Host;
import com.xmlconfig.XMLParser;

//@Singleton
//@Startup
@ApplicationPath("jaxrs")
public class JaxRSActivator extends Application{
    public static ArrayList<String> activeUsers = new ArrayList<String>();
}
