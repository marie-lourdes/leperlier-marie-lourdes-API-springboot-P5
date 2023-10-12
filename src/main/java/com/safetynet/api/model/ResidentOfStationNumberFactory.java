package com.safetynet.api.model;


//@Entity
//@Table(name = "firestation")
public class ResidentOfStationNumberFactory extends Person{

	private String firstName;
	private String lastName;
	private String address;
	private String phone;
	
	//public ResidentOfStationNumberFactory (){}
	
public ResidentOfStationNumberFactory (String firstName, String lastName,String address, String phone) {
		super(firstName,lastName,address,phone);
		this.firstName = firstName;
		this.lastName =lastName  ;
		this.address= address;
		this.phone= phone;

	}
}
