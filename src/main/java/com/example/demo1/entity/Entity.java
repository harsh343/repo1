package com.example.demo1.entity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import com.example.demo1.utils.GeneralUtils;

public class Entity  {
    private Long id;
    private String name;
    private String description;
    //private LinkedList<Entity> related;

    /*public LinkedList<Entity> getRelated() {
		return related;
	}

	public void setRelated(LinkedList<Entity> related) {
		this.related = related;
	}*/

	public Entity(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        //related?
    }

    @Override
    public String toString() {
        return "Entity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
