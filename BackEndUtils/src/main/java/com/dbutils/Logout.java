package com.dbutils;

import javax.ejb.Stateless;

import com.interfaces.LogoutInterface;
@Stateless
public class Logout implements LogoutInterface{

	@Override
	public void logout(String username) {
		// TODO remove from active users
		
	}

}
