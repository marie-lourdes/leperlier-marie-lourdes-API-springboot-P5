package com.safetynet.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "firestation")
public class FireStationWithAddressOnly extends FireStation{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "address")
	private String address;
	
	public FireStationWithAddressOnly (){}
/*	public FireStationWithAddressOnly(Long id, String stationNumber) {
		super();
		this.id =super.getId();
		this.address = super.getAddress();

	}*/
}
