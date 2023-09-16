package com.safetynet.api.model;

import java.util.List;

public class Child extends PersonEntity {
	private  String firstName;
	private  String lastName;
	private int age;
	private List<Family> parent;
		
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public List<Family> getParent() {
		return parent;
	}
	public void setParent(List<Family> parent) {
		this.parent = parent;
	}

}
