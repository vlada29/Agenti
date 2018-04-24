package com.chatapp.interfaces;

import java.util.LinkedList;

import javax.ejb.Local;

import com.chatapp.model.Host;

@Local
public interface Parser {
	public LinkedList<Host> getAllHosts();
	public void addNewHost(Host h);
	public Host getHostByAlias(String alias);
	public void removeAllHosts();
}
