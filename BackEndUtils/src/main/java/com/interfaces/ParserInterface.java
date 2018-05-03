package com.interfaces;

import java.util.LinkedList;

import javax.ejb.Local;

import com.model.Host;



@Local
public interface ParserInterface {
	LinkedList<Host> getAllHosts();
	void addNewHost(Host h);
	Host getHostByAlias(String alias);
	void removeAllHosts();
}
