package com.safetynet.api.model;

import com.safetynet.api.utils.RegexConstants;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class Person {
	private String id;

	@NotNull
	private String firstName;

	@NotNull
	private String lastName;

	@NotNull
	@Pattern(regexp = RegexConstants.REGEX_ADDRESS)
	private String address;

	@NotNull
	private String zip;

	@NotNull
	private String city;

	@NotNull
	@Pattern(regexp = RegexConstants.REGEX_PHONE)
	private String phone;

	@NotNull
	@Pattern(regexp = RegexConstants.REGEX_EMAIL)
	// @Email(message = "email should be valid")
	private String email;

	public Person() {

	}

	// use for person service /controller test
	public Person(String firstName, String lastName, String address, String city, String zip, String phone,
			String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.zip = zip;
		this.city = city;
		this.phone = phone;
		this.email = email;
	}

	// use for person service /controller test
	public Person(String id, String firstName, String lastName, String address, String city, String zip, String phone,
			String email) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.zip = zip;
		this.city = city;
		this.phone = phone;
		this.email = email;
	}

	// use for person service /controller test
	public Person(String address, String city, String zip, String phone, String email) {
		this.address = address;
		this.zip = zip;
		this.city = city;
		this.phone = phone;
		this.email = email;
	}
	public Person(String address) {
		this.address = address;
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

	@Override
	public String toString() {

		return "Person{" + "id:" + id + ", first name:'" + firstName + '\'' + ", last name:" + lastName + ", address:"
				+ address + ", zip:" + zip + ", city:" + city + ", phone:" + phone + ", email:" + email + '}';
	}
}