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

	public List<Map<String, String>> getChildsAndMembersOfHouseHold(String address) throws NullPointerException {
		listOfResidentChildAndMembersOfHouseHold = new ArrayList<Map<String, String>>();
		listOfResidentChildAndMembersOfHouseHold = sortAdultsAndChildsOfListOfResidentsWithFullInfo(address);
		List<Integer> allMemberOfHouseHoldWithAge = new ArrayList<Integer>();
		long numberOfChild = 0;

		for (Map<String, String> resident : listOfResidentChildAndMembersOfHouseHold) {
			// get list age parsed of residents found by address to sort and remove info age
			// for adults
			allMemberOfHouseHoldWithAge.add(Integer.parseInt(resident.get("age")));
			if (Integer.parseInt(resident.get("age")) > 18) {
				resident.remove("age");
			}
		}
		numberOfChild = allMemberOfHouseHoldWithAge.stream().filter(elem -> elem <= 18).count();
		if (numberOfChild == 0) {
			listOfResidentChildAndMembersOfHouseHold = new ArrayList<Map<String, String>>();
		}

		if (listOfResidentChildAndMembersOfHouseHold.isEmpty()) {
			throw new NullPointerException("No child found at this address");
		}

		return listOfResidentChildAndMembersOfHouseHold;
	}

	public List<Map<String, String>> sortAdultsAndChildsOfListOfResidentsWithFullInfo(String address) {

		return sortInfoOfChildsAndAdults.sortAdultsAndChilds(address);

	}
}
