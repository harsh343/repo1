package com.example.demo1;

import com.example.demo1.entity.Entity;
import com.example.demo1.entity.Graph;
import com.example.demo1.entity.Link;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@SpringBootApplication
public class RunApp {	

	public static void main(String[] args) throws Exception {
		SpringApplication.run(RunApp.class, args);

		if(args == null || args.length <2) {
			throw new IllegalArgumentException("Invalid inputs provided. Please provide valid file name and node id as 2 parameters !");
		}


	}

	@Bean
	@Profile("!test")
	public CommandLineRunner runner() {

		return args -> {

		    if(args == null || args.length <2) {
			   throw new IllegalArgumentException("Invalid inputs provided. Please provide valid file name and node id as 2 parameters !");
		    }

			JSONParser jsonParser = new JSONParser();
			Reader reader = new FileReader(args[0]);
			long inputEntityId = Long.parseLong(args[1]);

			Object jsonObj = jsonParser.parse(reader);

			JSONObject jsonObject = (JSONObject) jsonObj;
			JSONArray entities = (JSONArray) jsonObject.get("entities");

			List<Entity> entityList = new ArrayList<>();
			entities.stream().map((entity) -> {
				JSONObject jsonEntityObject = (JSONObject) entity;
				Entity eachEntity = new Entity((Long)jsonEntityObject.get("entity_id"),(String)jsonEntityObject.get("name"), (String)jsonEntityObject.get("description"));
				entityList.add(eachEntity);
				return eachEntity;
			}).collect(Collectors.toList());

			JSONArray links = (JSONArray) jsonObject.get("links");
			List<Link> linkList = new ArrayList<>();
			links.stream().map((link) -> {
				JSONObject jsonLinkObject = (JSONObject) link;
				Link eachLink = new Link((Long)jsonLinkObject.get("from"), (Long)jsonLinkObject.get("to"));
				linkList.add(eachLink);
				return eachLink;
			}).collect(Collectors.toList());

			process(entityList, linkList, inputEntityId);

		};
	}
	
	private void process(List<Entity> entityList, List<Link> linkList, Long inputEntityid) throws Exception {
			Graph graph = new Graph(entityList, linkList);
			graph.buildGraph(inputEntityid);

	        List<Entity> initialEntities = entityList.stream().filter(entity -> entity.getId() == inputEntityid).collect(Collectors.toList());
	        if(initialEntities != null && initialEntities.size() > 0) {
	            Entity initialEntity = initialEntities.get(0);
	            graph.cloneRelatedEntities(initialEntity);
	        }
	        else {
	        	throw new Exception("Given value not found.");
	        }
	}

}

