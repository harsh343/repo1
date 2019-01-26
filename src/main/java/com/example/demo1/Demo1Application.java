package com.example.demo1;

import com.example.demo1.entity.Entity;
import com.example.demo1.entity.Graph;
import com.example.demo1.entity.Link;
import com.example.demo1.utils.GeneralUtils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@SpringBootApplication
public class Demo1Application {
	

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Demo1Application.class, args);

		if(args == null || args.length <2) {
			throw new IllegalArgumentException("Invalid inputs provided. Please provide valid file name and node id as 2 parameters !");
		}


	}

	@Bean
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
			
			GeneralUtils.updateMaxId(entityList);	//to generate unique ID each time
			
            System.out.println("INPUTS");
            System.out.println("===============");

			System.out.println(entityList);

			JSONArray links = (JSONArray) jsonObject.get("links");
			List<Link> linkList = new ArrayList<>();
			links.stream().map((link) -> {
				JSONObject jsonLinkObject = (JSONObject) link;
				Link eachLink = new Link((Long)jsonLinkObject.get("from"), (Long)jsonLinkObject.get("to"));
				linkList.add(eachLink);
				return eachLink;
			}).collect(Collectors.toList());


			System.out.println(linkList);

			process(entityList, linkList, inputEntityId);

		};
	}
	
	private void process(List<Entity> entityList, List<Link> linkList, Long inputEntityid) {
		Graph graph = new Graph(entityList, linkList);
		graph.buildGraph(inputEntityid);
		
        System.out.println("OUTPUTS");
        System.out.println("=================");
        //STEP 1
        List<Entity> initialEntities = entityList.stream().filter(entity -> entity.getId() == inputEntityid).collect(Collectors.toList());
        if(initialEntities.size() > 0) {
            Entity initialEntity = initialEntities.get(0);
            graph.cloneRelatedEntities(initialEntity);
        }
    }

}

