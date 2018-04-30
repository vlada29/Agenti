package com.interfaces;

import javax.ejb.Local;

@Local
public interface GroupFinderInterface {
	String getGroups(String user);
}
