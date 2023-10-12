package com.safetynet.api.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public class Person {
	private final String REGEX_P = "^(.+)@(\\S+)$";
	private String id ;
	private String firstName;
	private String lastName;
	private String address;
	private String zip;
	private String city;
	private String phone;
	
	@Pattern( regexp = REGEX_P)
	@Email
	private String email;
	
	private int age;
	
	public Person() {}
	
	public Person( String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public Person( String firstName, String lastName,String address, String phone) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address= address;
		this.phone= phone;
	}
	
	/*public Person( String lastName, int age, String address, String phone) {
		
		this.lastName = lastName;
		this.age= age;
		this.address= address;
		this.phone= phone;
	}*/

	public String getId() {
		return id;
	}

	public String setId (String id) {
		return this.id=id;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getZip() {
		return zip;
	}

	public void setZip(String string) {
		this.zip = string;
	}
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	// jsonnitter?
	@Override
	public String toString() {

		return "Person{" + "id=" + id+ ", first name='" + firstName + '\'' + ", last name=" + lastName + ", address="
				+ address + ", zip=" + zip + ", city=" + city + ", phone=" + phone + ", email=" + email + '}';
	}

}