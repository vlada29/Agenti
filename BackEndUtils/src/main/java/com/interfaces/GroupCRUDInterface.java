package com.interfaces;

import java.util.ArrayList;

import javax.ejb.Local;

@Local
public interface GroupCRUDInterface {
	String createGroup(String creator, String name, ArrayList<String> clanovi);
	String deleteGroup(String nazivGrupe);
	String addUser(String idGrupe, String username);
	String removeUser(String idGrupe, String username);
	String leaveGroup(String idGrupe, String username);
}
