package com.safetynet.api.service.alertssafetynetservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChildAlertService {
	private static final Logger log = LogManager.getLogger(ChildAlertService.class);
	@Autowired
	SearchingFullInfoOfResidentsByAddressImpl infoOfChildAndMemberOfHouseHold;

	@Autowired
	SortingAdultsAndChildsOfListOfResidentsWithFirstNameAndLastName sortInfoOfChildsAndAdults;

	private List<Map<String, String>> listOfResidentChildAndMembersOfHouseHold;

	public List<Map<String, String>> getChildsAndMembersOfHouseHold(String address) throws NullPointerException {
		log.debug("Retrieving  Childs And Members Of HouseHold at this address: {}",  address);
		try {
		listOfResidentChildAndMembersOfHouseHold = new ArrayList<Map<String, String>>();
		listOfResidentChildAndMembersOfHouseHold = sortAdultsAndChildsOfListOfResidentsWithFullInfo(address);
		log.debug("All residents  sorted with category  adult and child at this address: {}", address);
		
		List<Integer> allMemberOfHouseHoldWithAge = new ArrayList<Integer>();
		for (Map<String, String> resident : listOfResidentChildAndMembersOfHouseHold) {
			// get list age parsed of residents found by address to sort and remove info age
			// for adults
			allMemberOfHouseHoldWithAge.add(Integer.parseInt(resident.get("age")));
			if (Integer.parseInt(resident.get("age")) > 18) {
				resident.remove("age");
			}
		}
		
		log.debug("Checking if there's child  at this address: {}", address);
		long numberOfChild = 0;
		numberOfChild = allMemberOfHouseHoldWithAge.stream().filter(elem -> elem <= 18).count();
		
		if (numberOfChild == 0) {
			listOfResidentChildAndMembersOfHouseHold = new ArrayList<Map<String, String>>();
		}

		if (listOfResidentChildAndMembersOfHouseHold.isEmpty()) {
			log.error("Failed to retrieve all childs and members of household at this address: {}",address);
			throw new NullPointerException("No child found at this address "+address);
		}else {
			log.debug(numberOfChild +" childs found for  this address :{}",  address);
		}
		}catch(Exception e) {
			e.printStackTrace();
			log.error( "An error has occured in getting childs members of houseHold");
		}
		log.info(" List of childs and members of household retrieved successfully by address {}", listOfResidentChildAndMembersOfHouseHold);
		return listOfResidentChildAndMembersOfHouseHold;
	}

	public List<Map<String, String>> sortAdultsAndChildsOfListOfResidentsWithFullInfo(String address) {
		log.debug("Sorting  all residents with category  adult and child at this address: {}", address);
		return sortInfoOfChildsAndAdults.sortAdultsAndChilds(address);

	}
}
