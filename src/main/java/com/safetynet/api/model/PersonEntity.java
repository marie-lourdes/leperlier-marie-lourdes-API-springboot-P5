package com.safetynet.api.model;

public class PersonEntity {
	// @NonNull to check or throw NullPointerException
	private String id;
	private String firstName;
	private String lastName;
	private String address;
	private int zip;
	private String city;
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

	private String phone;
	private String email;

	public PersonEntity(String id, String firstName, String lastName, String address, int zip, String city,
			String phone, String email) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.zip = zip;
		this.city = city;
		this.phone = phone;
		this.email = email;

	}

	// jsonnitter?
	@Override
	public String toString() {

		return "Person{" + "id=" + id + ", first name='" + firstName + '\'' + ", last name=" + lastName + ", address="
				+ address + ", zip=" + zip + ", city=" + city + ", phone=" + phone + ", email=" + email + '}';
	}

}
