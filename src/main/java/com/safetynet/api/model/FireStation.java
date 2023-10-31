package com.safetynet.api.model;

import com.safetynet.api.utils.RegexConstants;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class FireStation {
	private String id;

	@NotNull
	private String stationNumber;

	@NotNull
	@Pattern(regexp = RegexConstants.REGEX_ADDRESS)
	private String address;

	public FireStation() {
	}

	// use for firestation service /controller test
	public FireStation(String id, String stationNumber, String address) {
		this.id = id;
		this.stationNumber = stationNumber;
		this.address = address;
	}

	// use for firestation service /controller test
	public FireStation(String stationNumber, String address) {
		this.stationNumber = stationNumber;
		this.address = address;
	}

	// use for firestation service /controller test
	public FireStation(String address) {
		this.address = address;
	}

	public String getId() {
		return id;
	}

	public String setId(String string) {
		return this.id = string;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStationNumber() {
		return stationNumber;
	}

	public void setStationNumber(String stationNumber) {
		this.stationNumber = stationNumber;
	}

	@Override
	public String toString() {
		return "FireStation{" + "id:" + id + ", stationnumber:'" + stationNumber + '\'' + ", address:" + address + '}';
	}

}