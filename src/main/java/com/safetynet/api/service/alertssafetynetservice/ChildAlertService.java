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
		log.debug("Retrieving  childs and members Of houseHold at this address: {}", address);

		listOfResidentChildAndMembersOfHouseHold = new ArrayList<Map<String, String>>();
		try {
			listOfResidentChildAndMembersOfHouseHold = sortAdultsAndChildsOfListOfResidentsWithFullInfo(address);
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		List<Integer> allMemberOfHouseHoldWithAge = new ArrayList<Integer>();
		for (Map<String, String> resident : listOfResidentChildAndMembersOfHouseHold) {
			// get list age parsed of residents found by address to sort and remove info age
			// for adults
			try {
				allMemberOfHouseHoldWithAge.add(Integer.parseInt(resident.get("age")));
				if (Integer.parseInt(resident.get("age")) > 18) {
					resident.remove("age");
				}
			} catch (Exception e) {
				log.error("Error has occured in getting  childs and members Of houseHold at this address: {}", address);
			}

		}

		log.debug("Checking if there's child  at this address: {}", address);
		long numberOfChild = 0;
		numberOfChild = allMemberOfHouseHoldWithAge.stream().filter(elem -> elem <= 18).count();

		if (numberOfChild == 0) {
			listOfResidentChildAndMembersOfHouseHold = new ArrayList<Map<String, String>>();
		}

		if (listOfResidentChildAndMembersOfHouseHold.isEmpty()) {
			log.error("Failed to retrieve all childs and members of household at this address: {}", address);
			throw new NullPointerException("No child found at this address " + address);
		} else {
			log.debug(" {} childs found for  this address :{}", numberOfChild, address);
		}

		log.info(" List of childs and members of household retrieved successfully by address {}",
				listOfResidentChildAndMembersOfHouseHold);
		return listOfResidentChildAndMembersOfHouseHold;
	}

	public List<Map<String, String>> sortAdultsAndChildsOfListOfResidentsWithFullInfo(String address) throws Exception {
		List<Map<String, String>> listAdultsAndChildsSorted = null;
		try {
			listAdultsAndChildsSorted = sortInfoOfChildsAndAdults.sortAdultsAndChilds(address);
			if (listAdultsAndChildsSorted.isEmpty()) {
				throw new NullPointerException(
						"Error has occured sorting adults and childs  because not found at this address");
			} else {
				log.debug("All residents  sorted by adults and childs of address {}", address);
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return listAdultsAndChildsSorted;
	}
}