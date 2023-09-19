package com.safetynet.api.model;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity 
@Table(name="person")
public class Person {
	@Id
	// unicité des donnée , standard d identifiant gnerée aleatoirement, utilisée pour les base de données
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;
	
	 @Column(name="first_name")
	private String firstName;
	 @Column(name="last_name")
	private String lastName;
	private String address;
	private int zip;
	private String city;
	private String phone;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
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
