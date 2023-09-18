package com.safetynet.api.model;

import java.util.Map;

public class ResidentOfStationNumber extends Person {
	private Map<String,Integer> listOfPhoneOfResident;
	private int stationNumber;
	
	public Map<String, Integer> getListOfPhoneOfResident() {
		return listOfPhoneOfResident;
	}
	public void setListOfPhoneOfResident(Map<String, Integer> listOfPhoneOfResident) {
		this.listOfPhoneOfResident = listOfPhoneOfResident;
	}
	public int getStationNumber() {
		return stationNumber;
	}
	public void setStationNumber(int stationNumber) {
		this.stationNumber = stationNumber;
	}


}
