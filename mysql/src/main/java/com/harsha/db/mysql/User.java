 package com.harsha.db.mysql;

public class User {
	private int id;
	private String name;
	
	
	public User(String name) {
		super();
		this.name = name;
	}


	
	
	public User(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}




	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + "]";
	}




	public void setName(String name) {
		this.name = name;
	}

	
}
