package com.safetynet.api.service.alertssafetynetservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

public class SortingAdultsAndChildsOfListOfResidentsWithFirstNameAndLastNameImpl
		implements ISortingAdultsAndChildsOfListOfResidents {
	@Autowired
	SearchingFullInfoOfResidentsByAddressImpl fullinfoOfResidentWithSameAddress;

	private Map<String, String> mapOfAdultsAndChild = new HashMap<String, String>();
	private List<Map<String, String>> ResidentsOfStationNumberWithAge = new ArrayList<Map<String, String>>();

	@Override
	public Map<String, String> sortAdultsAndChilds(String request) {
		ResidentsOfStationNumberWithAge = fullinfoOfResidentWithSameAddress.searchInfoOfResident(request);
		for (Map<String, String> resident : ResidentsOfStationNumberWithAge) {
			System.out.println("resident of map" + resident.get("age"));

			if (Integer.parseInt(resident.get("age")) <= 18) {
				String fullName = resident.get("id");
				mapOfAdultsAndChild.put("childs", fullName);
				mapOfAdultsAndChild.put("childs", resident.get("age"));
			} else {
				mapOfAdultsAndChild.put("adults", resident.toString());
			}
		}

		System.out.println("resident of map" + mapOfAdultsAndChild);
		return mapOfAdultsAndChild;
	}
}
