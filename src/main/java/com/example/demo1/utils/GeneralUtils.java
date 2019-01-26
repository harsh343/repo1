package com.example.demo1.utils;

import java.util.List;

import com.example.demo1.entity.Entity;

public class GeneralUtils {
	
	public static long maxId;
	
	public static void updateMaxId(List<Entity> entityList) {
		for(Entity entity : entityList) {
			if(GeneralUtils.maxId < entity.getId()) {				//check for entity NULL?
				GeneralUtils.maxId = entity.getId();
			}
		}
	}
		
}
