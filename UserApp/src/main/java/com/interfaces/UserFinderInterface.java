package com.interfaces;

import javax.ejb.Local;

import com.model.User;

@Local
public interface UserFinderInterface {
	String searchForUser(String searchBy,String value);
	String addFriend(String user,String friend);
	String getGroups(String user);
	String login(String username, String password);
	String register(String username, String firstname, String lastname, String password);
}
