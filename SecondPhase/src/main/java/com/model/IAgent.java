package com.model;

import java.io.Serializable;

public interface IAgent extends Serializable{
	void init(AID aid);
	void stop();
	void handleMessage(ACLMessage msg);
	String ping();
	AID getAid();
}
