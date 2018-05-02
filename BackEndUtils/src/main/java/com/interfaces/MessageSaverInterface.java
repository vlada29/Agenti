package com.interfaces;

import javax.ejb.Local;

import com.model.Message;

@Local
public interface MessageSaverInterface {
	boolean saveMessage(String message);
	boolean insertIntoDB(Message message);
}
