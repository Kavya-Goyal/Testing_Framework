package io.learn.AppTest.API;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Pet {
	
	private String id;
	private String name;
	private String status;

	public Pet() {}
	
	public Pet(String id,String name,String status) {
		this.id = id;
		this.name = name;
		this.status = status;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}
	
	public String toJson() {
		try {
			return new ObjectMapper().writeValueAsString(this);
		} catch (Exception e) {
			throw new RuntimeException("Failed to serialize Pet to JSON", e);
		}
	}
}
