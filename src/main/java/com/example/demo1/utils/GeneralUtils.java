package com.example.demo1.utils;

import java.util.List;

import com.example.demo1.entity.Entity;

public class GeneralUtils {
	/*private long maxId;

	public long getMaxId() {
		return maxId;
	}

	public void setMaxId(long maxId) {
		this.maxId = maxId;
	}
	
	public void updateMaxId(List<Entity> entityList) {
		long maxId = 0;
		for(Entity entity : entityList) {
			if(maxId < entity.getId()) {				//check for entity NULL?
				maxId = entity.getId();
			}
			this.setMaxId(maxId);
		}
	}*/
	public static long maxId;
	
	public static void updateMaxId(List<Entity> entityList) {
		for(Entity entity : entityList) {
			if(GeneralUtils.maxId < entity.getId()) {				//check for entity NULL?
				GeneralUtils.maxId = entity.getId();
			}
		}
	}
		
}
