package com.example.demo1.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.example.demo1.utils.GeneralUtils;

public class Graph {
	private List<Entity> entityList;
	private List<Link> linkList;
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
	
	public void buildGraph() {
		for(Entity entity : this.entityList) {
			long key = entity.getId();
			if(!entityMap.containsKey(key))
				entityMap.put(key, entity);
		}
		
		for(Link link : this.linkList) {
			long key = link.getFrom();
			if (map.get(key) == null) {
			    map.put(key, new ArrayList<Long>());
			}
			map.get(key).add(link.getTo());
		}
	}
	
	public void updateGraphAfterCloning(long newId, Entity clonedObj) {
		/*entityMap.put(newId, clonedObj);
		map.put(newId, value)
		this.entityList.add(clonedObj);
		this.linkList.add(e);*/
	}
	
	public void clone(Entity entity)  {
    	
        //return new Entity(++GeneralUtils.maxId, this.name, this.description);
		long newId = ++GeneralUtils.maxId;
    	Entity clonedObj = new Entity(newId, entity.getName(), entity.getDescription());
    	//clonedObj.setRelated(this.related);
    	this.updateGraphAfterCloning(newId, clonedObj);
    	//return clonedObj;
    }
	
	public void cloneRelatedEntities(Entity initialEntity) {
		 Set<Entity> visited = new HashSet<>(); 
	     // Call the recursive helper function to print DFS traversal 
	     DFS(initialEntity, visited);
		
	}

	public void DFS(Entity initialEntity, Set<Entity> visited) {
		// Mark the current entity as visited and print it 
		if(initialEntity == null) return;
		visited.add(initialEntity); 
		//initialEntity.clone(); ToDo
		this.clone(initialEntity);
		System.out.println(initialEntity+" CLONED "); 
 
       // Recur for all the entities related to it .
       //Iterator<Entity> iterator = initialEntity.getRelated().listIterator(); 
       Iterator<Long> iterator = null;
       if( this.getMap().get(initialEntity.getId()) != null) {
    	   iterator = this.getMap().get(initialEntity.getId()).listIterator();
	       while (iterator!= null && iterator.hasNext()) 
	       { 
	           Entity node = entityMap.get(iterator.next()); 
	           if (!visited.contains(node)) 
	               DFS(node, visited); 
	       } 
       }
		
	}
	
	
	/*public List<Link> getLinkList() {
		return linkList;
	}
	public void setLinkList(List<Link> linkList) {
		this.linkList = linkList;
	}
	public List<Entity> getEntityList() {
		return entityList;
	}
	public void setEntityList(List<Entity> entityList) {
		this.entityList = entityList;
	}*/
	
	
	
	
	
}
