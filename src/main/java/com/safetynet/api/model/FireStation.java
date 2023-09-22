package com.safetynet.api.model;

import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "firestation")
public class FireStation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "station_number")
	private Long stationNumber;

	@Column(name = "address")
	private String address;

	/*
	 * private Integer numberOfAdult; private Integer numberOfChild;
	 */

	public FireStation() {
	}

	public FireStation(Long id, String address) {
		this.id = id;
		this.address = address;

	}

	public FireStation(Long id, Long stationNumber) {
		this.id = id;
		this.stationNumber = stationNumber;

	}

	public Long getId() {
		return id;
	}

	/*
	 * public Integer getNumberOfAdult() { return numberOfAdult; } public void
	 * setNumberOfAdult(Integer numberOfAdult) { this.numberOfAdult = numberOfAdult;
	 * } public Integer getNumberOfChild() { return numberOfChild; } public void
	 * setNumberOfChild(Integer numberOfChild) { this.numberOfChild = numberOfChild;
	 * }
	 */

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getStationNumber() {
		return stationNumber;
	}

	public void setStationNumber(Long stationNumber) {
		this.stationNumber = stationNumber;
	}
}