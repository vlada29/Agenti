package com.model;

public class AgentskiCentar {
	private String address;
	private String alias;
	public AgentskiCentar(){}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public AgentskiCentar(String address, String alias) {
		super();
		this.address = address;
		this.alias = alias;
	}
	@Override
	public String toString() {
		return "AgentskiCentar [address=" + address + ", alias=" + alias + "]";
	}
}
