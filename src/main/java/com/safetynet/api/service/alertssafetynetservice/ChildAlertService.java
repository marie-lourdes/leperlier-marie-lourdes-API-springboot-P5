package com.safetynet.api.service.alertssafetynetservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChildAlertService {
	@Autowired
	SearchingFullInfoOfResidentsByAddressImpl infoOfChildAndMemberOfHouseHold;

	@Autowired
	SortingAdultsAndChildsOfListOfResidentsWithFirstNameAndLastName sortInfoOfChildsAndAdults;

	private List<Map<String, String>> listOfResidentChildAndMembersOfHouseHold;

	public List<Map<String, String>> getChildsAndMembersOfHouseHold(String address) {
		listOfResidentChildAndMembersOfHouseHold = new ArrayList<Map<String, String>>();
		listOfResidentChildAndMembersOfHouseHold = sortAdultsAndChildsOfListOfResidentsWithFullInfo(address);
		List<Integer> allMemberOfHouseHoldWithAge= new ArrayList<Integer> ();
		long numberOfChild=0;
		
		for(Map<String, String> resident:listOfResidentChildAndMembersOfHouseHold ) {
			System.out.println("resident sorted in childsService"+ resident.get("age"));
			allMemberOfHouseHoldWithAge.add(Integer.parseInt(resident.get("age")));	
			
			if (Integer.parseInt(resident.get("age")) >18) {
				resident.remove("age");
			}		
		}
	
		System.out.println("list age is child"+ allMemberOfHouseHoldWithAge);
		numberOfChild = allMemberOfHouseHoldWithAge.stream().filter(elem-> elem <=18 ).count();
		System.out.println("numberOf Child"+ numberOfChild );
		if(numberOfChild==0) {
			return null;
		}
		return listOfResidentChildAndMembersOfHouseHold;
	}

	public List<Map<String, String>> sortAdultsAndChildsOfListOfResidentsWithFullInfo(String address) {
		return sortInfoOfChildsAndAdults.sortAdultsAndChilds(address);
	}
}
