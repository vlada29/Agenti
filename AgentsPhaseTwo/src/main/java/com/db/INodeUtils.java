package com.db;

import java.util.List;

import javax.ejb.Local;

import com.model.AgentskiCentar;

@Local
public interface INodeUtils {
	public void initCenters();
	public boolean addNode(AgentskiCentar ac);
	public boolean removeNode(String alias);
	public List<AgentskiCentar> getCenters();
	public AgentskiCentar getCentarByAlias(String alias);
	public String getMasterAlias();
	
}
