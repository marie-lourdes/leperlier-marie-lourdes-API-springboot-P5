package com.safetynet.api.model;

import org.springframework.cache.annotation.Cacheable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

//@Entity 
//@Table(name="person")
public class Person {
	private final String REGEX_P = "^(.+)@(\\S+)$";
	//@Id
	//@GeneratedValue(strategy= GenerationType.IDENTITY)
	private String id ;
	
	//@Column(name="first_name")
	private String firstName;
	
	//@Column(name="last_name")
	private String lastName;
	
	//@Column(name="address")
	private String address;
	
	//@Column(name="zip")
	private String zip;
	
	//@Column(name="city")
	private String city;
	
	//@Column(name="phone")
	private String phone;
	
	@Pattern( regexp = REGEX_P)
	@Email
	//@Column(name="email")
	private String email;
	
	//private int age;
	
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
@Cacheable
	public String getId() {
		return id;
	}

	public String setId (String id) {
		return this.id=id;
	}
	@Cacheable
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	@Cacheable
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	@Cacheable
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	@Cacheable
	public String getZip() {
		return zip;
	}

	public void setZip(String string) {
		this.zip = string;
	}
	@Cacheable
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	@Cacheable
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Cacheable
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	/*public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}*/
	
	// jsonnitter?
	@Override
	public String toString() {

		return "Person{" + "id=" + id+ ", first name='" + firstName + '\'' + ", last name=" + lastName + ", address="
				+ address + ", zip=" + zip + ", city=" + city + ", phone=" + phone + ", email=" + email + '}';
	}

}