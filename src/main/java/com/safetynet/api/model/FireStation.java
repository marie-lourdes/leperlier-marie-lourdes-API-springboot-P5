package com.safetynet.api.model;

public class FireStation {

	private String id;
	private String stationNumber;
	private String address;

	/*
	 * private Integer numberOfAdult; private Integer numberOfChild;
	 */

	public FireStation() {
	}

	/*public FireStation(Long id, String address) {
		this.id = id;
		this.stationNumber=null;
		this.address = address;

	}

	public FireStation(Long id, String string2) {
		this.id = id;
		this.stationNumber = stationNumber;

	}*/



	public String getId() {
		return id;
	}

	public String setId(String string) {
		return this.id =string;
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

	public String getStationNumber() {
		return stationNumber;
	}

	public void setStationNumber(String stationNumber) {
		this.stationNumber = stationNumber;
	}
	

	@Override
	public String toString() {

		return "FireStation{" + "id=" + id + ", stationnumber='" + stationNumber + '\'' + ", address=" + address + '}';
	}

	
}