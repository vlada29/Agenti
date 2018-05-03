package com.app;


import java.util.ArrayList;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.model.User;


//@Singleton
//@Startup
@ApplicationPath("jaxrs")
public class JaxRSActivator extends Application{

	
	
	
    public static ArrayList<User> activeUsers = new ArrayList<User>();
}
