package com.safetynet.api.model;



//@Entity
//@Table(name = "firestation")
public class FireStationWithStationNumberOnly extends FireStation {
	//@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//@Column(name = "station_number")
	private String stationNumber;
	
	public FireStationWithStationNumberOnly(){}
/*	public FireStationWithStationNumberOnly(Long id, String stationNumber) {
		super();
		this.id =id;
		this.stationNumber = stationNumber;

	}*/
}
