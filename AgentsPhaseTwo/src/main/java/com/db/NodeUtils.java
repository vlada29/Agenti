package com.db;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import com.model.AgentskiCentar;

//@Startup
@Singleton
//@DependsOn("StartupBean")
public class NodeUtils implements INodeUtils{
	private List<AgentskiCentar> agentCenters;
	private String masterAlias;
	
	@Override
	public String getMasterAlias() {
		return masterAlias;
	}

	public void setMasterAlias(String masterAlias) {
		this.masterAlias = masterAlias;
	}
	private AgentskiCentar localCenter;
	@Override
	public AgentskiCentar getCentarByAlias(String alias){
		for(AgentskiCentar ac : agentCenters){
			if(ac.equals(alias)){
				return ac;
			}
		}
		return null;
	}
	
	@PostConstruct
	@Override
	public void initCenters() {
		agentCenters = new ArrayList<AgentskiCentar>();
	}

	public List<AgentskiCentar> getAgentCenters() {
		return agentCenters;
	}

	public void setAgentCenters(List<AgentskiCentar> agentCenters) {
		this.agentCenters = agentCenters;
	}

	public AgentskiCentar getLocalCenter() {
		return localCenter;
	}

	public void setLocalCenter(AgentskiCentar localCenter) {
		this.localCenter = localCenter;
	}

	@Override
	public boolean addNode(AgentskiCentar ac) {
		for(AgentskiCentar cen : agentCenters){
			if(cen.getAlias().equals(ac.getAlias())){
				return false;
			}
		}
		agentCenters.add(ac);
		return true;
	}

	@Override
	public boolean removeNode(String alias) {
		for(AgentskiCentar cen : agentCenters){
			if(cen.getAlias().equals(alias)){
				agentCenters.remove(cen);
				return true;
			}
		}
		return false;
	}

	@Override
	public List<AgentskiCentar> getCenters() {
		return agentCenters;
	}
 

}
