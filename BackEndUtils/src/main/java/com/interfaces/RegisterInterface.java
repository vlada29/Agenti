package com.interfaces;

import javax.ejb.Local;

@Local
public interface RegisterInterface {
	String register(String username, String password, String firstname, String lastname);
}
