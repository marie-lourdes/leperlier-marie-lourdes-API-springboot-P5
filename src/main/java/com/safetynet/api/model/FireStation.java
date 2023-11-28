package com.safetynet.api.model;

import com.safetynet.api.utils.RegexConstants;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
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

	@Override
	public String toString() {
		return "FireStation{" + "id:" + id + ", stationnumber:'" + stationNumber + '\'' + ", address:" + address + '}';
	}
}