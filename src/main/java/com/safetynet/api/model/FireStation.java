package com.safetynet.api.model;

import java.util.List;

public class FireStation {
	private int id;
	private String address;
	private int stationNumber;
	private  Integer numberOfAdult;
	private Integer numberOfChild;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Integer getNumberOfAdult() {
		return numberOfAdult;
	}
	public void setNumberOfAdult(Integer numberOfAdult) {
		this.numberOfAdult = numberOfAdult;
	}
	public Integer getNumberOfChild() {
		return numberOfChild;
	}
	public void setNumberOfChild(Integer numberOfChild) {
		this.numberOfChild = numberOfChild;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getStationNumber() {
		return stationNumber;
	}
	public void setStationNumber(int stationNumber) {
		this.stationNumber = stationNumber;
	}
}
