package com.example.demo1.entity;

import com.example.demo1.utils.GeneralUtils;
import org.json.simple.parser.ParseException;

import java.util.*;

public class Graph {
	private List<Entity> entityList;
	private List<Link> linkList;
	private long initialParentId;
	private Map<Long,List<Long>> map = new HashMap<Long,List<Long>>();
	private Map<Long,Entity> entityMap = new HashMap<Long,Entity>();

	public long maxId = 0;

	public void updateMaxId(List<Entity> entityList) {
		for(Entity entity : entityList) {
			if(entity!=null && maxId < entity.getId()) {
				maxId = entity.getId();
			}
		}
	}
	
	public Map<Long, List<Long>> getMap() {
		return map;
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
			if(link.getTo() == inputEntityid)
				this.initialParentId = link.getFrom();
			long key = link.getFrom();
			if (map.get(key) == null) {
			    map.put(key, new ArrayList<Long>());
			}
			map.get(key).add(link.getTo());
		}

		updateMaxId(entityList);
	}
	
	public void updateGraphAfterCloning(long parentId, long newId, Entity clonedObj) {
		this.entityList.add(clonedObj);
		this.linkList.add(new Link(parentId, newId));
	}
	
	public void clone(Entity entity)  {
		long parentId;
		if(this.initialParentId > 0) {
			parentId = initialParentId;
			initialParentId = 0;
		}
		else {
			parentId = maxId;
		}
		long newId = ++maxId;
    	Entity clonedObj = new Entity(newId, entity.getName(), entity.getDescription());
    	this.updateGraphAfterCloning(parentId, newId, clonedObj);
    	
    }
	
	public void cloneRelatedEntities(Entity initialEntity) {
		Set<Entity> visited = new HashSet<>(); 
		visited.add(initialEntity); 
		this.clone(initialEntity);
		
	    DFS(initialEntity, visited);

	    try {
			GeneralUtils.writetoJSON(this.entityList, this.linkList);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		
	}

	public void DFS(Entity initialEntity, Set<Entity> visited) {
		if(initialEntity == null) return;
		if(!visited.contains(initialEntity)) {
			visited.add(initialEntity); 
			this.clone(initialEntity);
		}

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
