package com.safetynet.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;

@Entity 
@Table(name="person")
public class Person {
	private final String REGEX_P = "^(.+)@(\\S+)$";
	@Id
	// unicité des donnée , standard d identifiant gnerée aleatoirement, utilisée pour les base de données :generationtype uuid
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="address")
	private String address;
	
	@Column(name="zip")
	private Long zip;
	
	@Column(name="city")
	private String city;
	
	@Column(name="phone")
	private String phone;
	
	@Pattern( regexp = REGEX_P)
	@Column(name="email", unique=true)
	private String email;
	
	//private int age;
	
	/*public Person() {}
	
	public Person( String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public Person( String firstName, String lastName,String address, String phone) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address= address;
		this.phone= phone;
	}*/
	
	/*public Person( String lastName, int age, String address, String phone) {
		
		this.lastName = lastName;
		this.age= age;
		this.address= address;
		this.phone= phone;
	}*/

	public Long getId() {
		return id;
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

	public Long getZip() {
		return zip;
	}

	public void setZip(Long zip) {
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