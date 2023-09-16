package com.safetynet.api.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity 
public class PersonEntity {
	 @Id
	  @GeneratedValue(strategy=GenerationType.AUTO)
	private String id;
	private String firstName;
	private String lastName;
	private String address;
	private int zip;
	private String city;
	private String phone;
	private String email;
	private int age;
	
	public PersonEntity() {}
	
	public PersonEntity( String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public PersonEntity( String firstName, String lastName,String address, String phone) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address= address;
		this.phone= phone;
	}
	
	public PersonEntity( String lastName, int age, String address, String phone) {
		
		this.lastName = lastName;
		this.age= age;
		this.address= address;
		this.phone= phone;
	}

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getZip() {
		return zip;
	}

	public void setZip(int zip) {
		this.zip = zip;
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

		return "Person{" + "id=" + id + ", first name='" + firstName + '\'' + ", last name=" + lastName + ", address="
				+ address + ", zip=" + zip + ", city=" + city + ", phone=" + phone + ", email=" + email + '}';
	}

}
