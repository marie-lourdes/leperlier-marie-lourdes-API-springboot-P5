package com.safetynet.api.service.alertssafetynetservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SortingAdultsAndChildsOfListOfResidentsWithFirstNameAndLastName {
	private static final Logger log = LogManager
			.getLogger(SortingAdultsAndChildsOfListOfResidentsWithFirstNameAndLastName.class);
	@Autowired
	SearchingFullInfoOfResidentsByAddressImpl fullInfoOfResidentWithSameAddress;

	private List<Map<String, String>> listOfResidentsFoundByAddress = new ArrayList<Map<String, String>>();
	private List<Map<String, String>> listOfAdultsAndChild = new ArrayList<Map<String, String>>();

	public List<Map<String, String>> sortAdultsAndChilds(String address) throws Exception {
		log.debug("Sorting  Adults And Childs at this address : {} ",address);
		listOfResidentsFoundByAddress = fullInfoOfResidentWithSameAddress.searchInfoOfResident(address);

		for (Map<String, String> resident : listOfResidentsFoundByAddress) {
			if (Integer.parseInt(resident.get("age")) <= 18) {
				resident.remove("address");
				resident.remove("city");
				resident.remove("phone");
				resident.remove("email");
				listOfAdultsAndChild.add(resident);
			} else {
				listOfAdultsAndChild.add(resident);
			}
		}

		log.debug("List of residents sorted  with full name of childs and all info of adults :" + listOfAdultsAndChild);
		return listOfResidentsFoundByAddress;
	}
}