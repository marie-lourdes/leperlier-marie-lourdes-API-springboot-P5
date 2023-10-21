package com.safetynet.api.model;

//@Entity
//@Table(name = "firestation")
public class ResidentOfStationNumberFactory {

	private String firstName;
	private String lastName;
	private String address;
	private String phone;

	// public ResidentOfStationNumberFactory (){}

	public ResidentOfStationNumberFactory(String firstName, String lastName, String address, String phone) {

		new Person(firstName, lastName, address, phone);
	}
}
