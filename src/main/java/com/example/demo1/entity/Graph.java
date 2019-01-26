package com.example.demo1.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.simple.parser.ParseException;

import com.example.demo1.utils.GeneralUtils;

public class Graph {
	private List<Entity> entityList;
	private List<Link> linkList;
	private long initialParentId;
	private Map<Long,List<Long>> map = new HashMap<Long,List<Long>>();
	private Map<Long,Entity> entityMap = new HashMap<Long,Entity>();
	
	public Map<Long, List<Long>> getMap() {
		return map;
	}

	public void setMap(Map<Long, List<Long>> map) {
		this.map = map;
	}

	public Graph(List<Entity> entityList, List<Link> linkList) {
		this.entityList = entityList;
		this.linkList = linkList;
	}
	
	public void buildGraph(Long inputEntityid) {
		for(Entity entity : this.entityList) {
			long key = entity.getId();
			if(!entityMap.containsKey(key))
				entityMap.put(key, entity);
		}
		
		for(Link link : this.linkList) {
			if(link.getTo() == inputEntityid) this.initialParentId = link.getFrom();
			long key = link.getFrom();
			if (map.get(key) == null) {
			    map.put(key, new ArrayList<Long>());
			}
			map.get(key).add(link.getTo());
		}
	}
	
	public void updateGraphAfterCloning(long parentId, long newId, Entity clonedObj) {
		/*entityMap.put(newId, clonedObj);
		map.put(newId, value)*/					//to do or not to do?
		this.entityList.add(clonedObj);
		this.linkList.add(new Link(parentId, newId));
	}
	
	public void clone(Entity entity)  {
		long parentId;
		if(this.initialParentId > 0) {		//special case for initialEntity's parent
			parentId = initialParentId;
			initialParentId = 0;
		}
		else {
			parentId = GeneralUtils.maxId;
		}
		long newId = ++GeneralUtils.maxId;
    	Entity clonedObj = new Entity(newId, entity.getName(), entity.getDescription());
    	//System.out.print(clonedObj + "IS CLONE OF "); 
    	this.updateGraphAfterCloning(parentId, newId, clonedObj);
    	
    }
	
	public void cloneRelatedEntities(Entity initialEntity) {
		Set<Entity> visited = new HashSet<>(); 
		visited.add(initialEntity); 
		this.clone(initialEntity);
		
	    // Call the recursive helper function to print DFS traversal 
	    DFS(initialEntity, visited);
	    
	    System.out.println("EntityList: " + this.entityList.toString());
	    System.out.println("LinkList: " + this.linkList.toString());
	    try {
			GeneralUtils.writetoJSON(this.entityList, this.linkList);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.out.println("[ERROR]: JSON write failed.");
			e.printStackTrace();
		}
		
	}

	public void DFS(Entity initialEntity, Set<Entity> visited) {
		// Mark the current entity as visited
		if(initialEntity == null) return;
		if(!visited.contains(initialEntity)) {
			visited.add(initialEntity); 
			this.clone(initialEntity);
			//System.out.println(initialEntity);
		}
 
       //Recur for all the entities related to it .
       //Iterator<Entity> iterator = initialEntity.getRelated().listIterator(); 
       Iterator<Long> iterator = null;
       if( this.getMap().get(initialEntity.getId()) != null) {
    	   iterator = this.getMap().get(initialEntity.getId()).listIterator();
	       while (iterator != null && iterator.hasNext()) 
	       { 
	           Entity node = entityMap.get(iterator.next()); 
	           if (!visited.contains(node)) 
	               DFS(node, visited); 
	       } 
       }	
	}
	
	
}
