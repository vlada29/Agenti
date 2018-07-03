package com.model;

public class Agent implements IAgent{
	private AID id;

	@Override
	public void init(AID aid) {
		// TODO Auto-generated method stub
		this.id = aid;
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub]
		System.out.println("Stopping agent...");
		
	}

	@Override
	public void handleMessage(ACLMessage msg) {
		// TODO Auto-generated method stub
		System.out.println("Handling message");
		
	}

	@Override
	public String ping() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AID getAid() {
		// TODO Auto-generated method stub
		return this.id;
	}
	
	public void setAid(AID id){
		this.id = id;
	}
	
	public Agent(){}

	public AID getId() {
		return id;
	}

	public void setId(AID id) {
		this.id = id;
	}
}
