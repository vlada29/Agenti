package com.dbutils;

import javax.ejb.Local;

import com.model.User;

@Local
public interface UserFinderInterfaceChat {
	String searchForUser(String searchBy,String value);
}
