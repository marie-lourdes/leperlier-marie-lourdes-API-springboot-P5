package com.safetynet.api.service.alertssafetynetservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SortingAdultsAndChildsOfListOfResidentsWithFirstNameAndLastName {
	@Autowired
	SearchingFullInfoOfResidentsByAddressImpl fullInfoOfResidentWithSameAddress;

	private List<Map<String, String>> listOfResidentsFoundByAddress = new ArrayList<Map<String, String>>();
	private List<Map<String, String>> listOfAdultsAndChild = new ArrayList<Map<String, String>>();

	public List<Map<String, String>> sortAdultsAndChilds(String request) {

		listOfResidentsFoundByAddress = fullInfoOfResidentWithSameAddress.searchInfoOfResident(request);

		for (Map<String, String> resident : listOfResidentsFoundByAddress) {
			if (Integer.parseInt(resident.get("age")) <= 18) {
				// System.out.println("child firstName" + resident.get("firstName"));
				// mapOfAdultsAndChild.put("childs",
				// resident.get("firstName")+","+resident.get("lastName")+","
				// +resident.get("age"));
				resident.remove("address");
				resident.remove("city");
				resident.remove("zip");
				resident.remove("phone");
				resident.remove("email");
				listOfAdultsAndChild.add(resident);

			} else {
				resident.remove("age");
				listOfAdultsAndChild.add(resident);
			}
		}
		System.out.println("listOfAdultsAndChildSorted" + listOfAdultsAndChild);
		return listOfResidentsFoundByAddress;
	}
}