package com.interfaces;

import javax.ejb.Local;

import com.model.User;

@Local
public interface AddFriendInterface {
	boolean addFriend(String user,String friend);
}
