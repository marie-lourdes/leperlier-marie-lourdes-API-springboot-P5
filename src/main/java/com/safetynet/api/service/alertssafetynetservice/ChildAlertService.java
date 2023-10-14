package com.safetynet.api.service.alertssafetynetservice;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

public class ChildAlertService {
	@Autowired
	 SearchingInfoOfChildAndMemberOfHouseHoldImpl  InfoOfChildAndParent;
	
	@Autowired
	SortingAdultsAndChildsOfListOfResidentsWithCountDownImpl adultsAndChildsOfListOfResidents;
	
	private List<Map<String, String>> listOfResidentChildAndMembersOfHouseHold;

	public List<Map<String, String>> getChildAndMembersOfHouseHold(String address) throws ParseException {
		listOfResidentChildAndMembersOfHouseHold= new ArrayList<Map<String, String>>();
		listOfResidentChildAndMembersOfHouseHold = InfoOfChildAndParent.searchInfoOfResident(address);

		System.out.println("list des residents" +listOfResidentChildAndMembersOfHouseHold);
		return 	listOfResidentChildAndMembersOfHouseHold;
	}

	public Map<String, Integer> sortAdultsAndChildsOfListOfResidentsWithCountDown(String stationNumber){
		return adultsAndChildsOfListOfResidents.sortAdultsAndChilds(stationNumber);
	}
}
