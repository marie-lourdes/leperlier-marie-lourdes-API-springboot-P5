package com.safetynet.api.service.alertssafetynetservice;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChildAlertService {
	@Autowired
	 SearchingFullInfoOfResidentsByAddressImpl  infoOfChildAndMemberOfHouseHold;
	
	@Autowired
	SortingAdultsAndChildsOfListOfResidentsWithFirstNameAndLastNameImpl sortInfoOfChildsAndAdults;
	
	private List<Map<String, String>> listOfResidentChildAndMembersOfHouseHold;

	public List<Map<String, String>> getChildAndMembersOfHouseHold(String address) throws ParseException {
		listOfResidentChildAndMembersOfHouseHold= new ArrayList<Map<String, String>>();
		listOfResidentChildAndMembersOfHouseHold =infoOfChildAndMemberOfHouseHold.searchInfoOfResident(address);
		return 	listOfResidentChildAndMembersOfHouseHold;
	}

	public List<Map<String, String>> sortAdultsAndChildsOfListOfResidentsWithFullInfo(String address){
		return sortInfoOfChildsAndAdults.sortListAdultsAndChilds(address);
	}
}
