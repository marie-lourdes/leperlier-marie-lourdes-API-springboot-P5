package com.safetynet.api.model;

import java.util.List;

public class Child {

	private String id;
	private String firstName;
	private String lastName;
	private int age;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public List<String> getParent() {
		return parent;
	}
	public void setParent(List<String> parent) {
		this.parent = parent;
	}
	private List<String> parent;
}
