package com.db;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import com.model.AID;
import com.model.Agent;
import com.model.AgentType;
import com.model.AgentskiCentar;

@Startup
@Singleton
//@DependsOn("StartupBean")
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class NodeUtils implements INodeUtils{
	private List<AgentskiCentar> agentCenters;
	private List<AgentType> supportedTypes;
	private List<Agent> runningAgents;
	private List<Agent> agentTypes2;
	
	@Override
	public List<AgentType> getSupportedTypes() {
		return supportedTypes;
	}
	
	public void setSupportedTypes(List<AgentType> supportedTypes) {
		this.supportedTypes = supportedTypes;
	}
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
		supportedTypes = new ArrayList<AgentType>();
		agentTypes2 = new ArrayList<Agent>();
		AgentType at1 = new AgentType("module1","non_master_name1");
		AgentType at2 = new AgentType("module2","non_master_name2");
		AgentType at3 = new AgentType("module2","non_master_name3");
		
		Agent a1 = new Agent();
		Agent a2 = new Agent();
		
		AgentskiCentar ac1 = new AgentskiCentar("non_master_1", "178.223.69.56");
		
		supportedTypes.add(at1);
		supportedTypes.add(at2);
		supportedTypes.add(at3);
		
		AID aid1 = new AID();
		aid1.setHost(ac1);
		aid1.setType(at1);
		aid1.setName("AID_1");
		

		AID aid2 = new AID();
		aid2.setHost(ac1);
		aid2.setType(at2);
		aid2.setName("AID_2");
		
		a1.setAid(aid1);
		a2.setAid(aid2);
		
		
		agentTypes2.add(a1);
		agentTypes2.add(a2);
		
		runningAgents = new ArrayList<Agent>();
		
		agentCenters.add(ac1);
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
	public List<Agent> getAgentTypes2() {
		return agentTypes2;
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

	@Override
	public List<Agent> getRunning() {
		return runningAgents;
	}

	@Override
	public void pokreniAgenta(AgentType type, String name) {
		Agent a = new Agent();
		AID aid = new AID();
		aid.setName(name);
		aid.setType(type);
		aid.setHost(localCenter);
		a.setAid(aid);
		runningAgents.add(a);
	}

	@Override
	public void zaustaviAgenta(AID aid) {
		for(Agent a : runningAgents) {
			if(a.getAid().equals(aid)) {
				runningAgents.remove(a);
				break;
			}
		}
		
	}
 

}
