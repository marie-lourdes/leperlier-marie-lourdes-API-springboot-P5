package com.safetynet.api.model;


//@Entity
//@Table(name = "firestation")
public class FireStationWithAddressOnly extends FireStation{
	//@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//@Column(name = "address")
	private String address;
	
	public FireStationWithAddressOnly (){}
/*	public FireStationWithAddressOnly(Long id, String stationNumber) {
		super();
		this.id =super.getId();
		this.address = super.getAddress();

	}*/
}
