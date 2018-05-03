package com.app;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.bson.Document;

import com.dbutils.MongoClientProvider;
import com.google.gson.Gson;
import com.model.Host;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.xmlconfig.XMLParser;

//@Singleton
//@Startup
@ApplicationPath("jaxrs")
public class JaxRSActivator extends Application{

	
	
	
    public static ArrayList<String> activeUsers = new ArrayList<String>();
}
