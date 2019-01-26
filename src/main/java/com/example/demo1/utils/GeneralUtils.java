package com.example.demo1.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.example.demo1.entity.Entity;
import com.example.demo1.entity.Link;

public class GeneralUtils {
	
	public static long maxId;
	
	public static void updateMaxId(List<Entity> entityList) {
		for(Entity entity : entityList) {
			if(entity!=null && GeneralUtils.maxId < entity.getId()) {				//check for entity NULL?
				GeneralUtils.maxId = entity.getId();
			}
		}
	}
	
	public static void writetoJSON(List<Entity> entityList, List<Link> linkList) throws ParseException {
		JSONParser parser = new JSONParser();
		JSONObject entityListObject = (JSONObject) parser.parse(entityList.toString());
		JSONObject linkListObject = (JSONObject) parser.parse(linkList.toString());
		JSONArray outputArray = new JSONArray();
		outputArray.add(entityListObject);
		outputArray.add(linkListObject);
		//Write JSON file
        try (FileWriter file = new FileWriter("output.json")) {
 
            file.write(outputArray.toJSONString());
            file.flush();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
		
}
