package com.example.demo1.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.example.demo1.entity.Entity;
import com.example.demo1.entity.Link;

public class GeneralUtils {

    public static void writetoJSON(List<Entity> entityList, List<Link> linkList) throws ParseException {
        JSONArray outputEntityArray = new JSONArray();
        JSONArray outputLinkArray = new JSONArray();

        for (int i = 0; i < entityList.size(); i++) {
            JSONObject eachEntity = new JSONObject();
            if (entityList.get(i).getDescription() != null) {
                eachEntity.put("description", entityList.get(i).getDescription());
            }
            eachEntity.put("name", entityList.get(i).getName());
            eachEntity.put("entity_id", entityList.get(i).getId());


            outputEntityArray.add(eachEntity);
        }
        for (int i = 0; i < linkList.size(); i++) {
            JSONObject eachLink = new JSONObject();
            eachLink.put("from", linkList.get(i).getFrom());
            eachLink.put("to", linkList.get(i).getTo());
            outputLinkArray.add(eachLink);
        }

        JSONObject obj = new JSONObject();
        obj.put("entities", outputEntityArray);
        obj.put("links", outputLinkArray);

        try (FileWriter file = new FileWriter("output.json")) {
            file.write(obj.toJSONString());
            file.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
