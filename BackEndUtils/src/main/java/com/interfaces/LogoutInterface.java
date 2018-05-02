package com.interfaces;

import javax.ejb.Local;

@Local
public interface LogoutInterface {
	void logout(String username);
}
