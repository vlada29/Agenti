package com.db;

import java.util.List;

import javax.ejb.Local;

import com.model.AID;
import com.model.Agent;
import com.model.AgentType;
import com.model.AgentskiCentar;

@Local
public interface INodeUtils {
	public void initCenters();
	public boolean addNode(AgentskiCentar ac);
	public boolean removeNode(String alias);
	public List<AgentskiCentar> getCenters();
	public AgentskiCentar getCentarByAlias(String alias);
	public String getMasterAlias();
	public List<AgentType> getSupportedTypes();
	public List<Agent> getRunning();
	public void pokreniAgenta(AgentType type,String name);
	public void zaustaviAgenta(AID aid);
}
