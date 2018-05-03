package com.interfaces;

import java.util.ArrayList;

import javax.ejb.Local;

import com.model.Grupa;

@Local
public interface GroupCRUDInterface {
	String createGroup(Grupa grupa);
	String deleteGroup(String nazivGrupe);
	String addUser(String idGrupe, String username);
	String removeUser(String idGrupe, String username);
	String leaveGroup(String idGrupe, String username);
}