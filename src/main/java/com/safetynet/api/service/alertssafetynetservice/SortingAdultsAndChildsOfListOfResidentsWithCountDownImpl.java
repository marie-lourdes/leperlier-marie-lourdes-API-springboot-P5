package com.safetynet.api.service.alertssafetynetservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SortingAdultsAndChildsOfListOfResidentsWithCountDownImpl  implements ISortingAdultsAndChildsOfListOfResidents {
	@Autowired
	SearchingInfoOfResidentOfStationNumberImpl infoOfResidentOfStationNumber;
	
	private Map<String, String> mapOfChild = new HashMap<String, String>();
	private Map<String, Person> mapOfAdult = new HashMap<String, Person>();
	private List<Map<String, String>> ResidentsOfStationNumberWithAge = new ArrayList<Map<String, String>>();
	
	@Override
	public Map<String, Integer> sortAdultsAndChilds(String request){
	
		ResidentsOfStationNumberWithAge = infoOfResidentOfStationNumber.searchInfoOfResident(request);

		Integer indexChild = 1;
		Integer indexAdult = 1;
		for (Map<String, String> resident : ResidentsOfStationNumberWithAge) {
			System.out.println("resident of map" + resident.get("age"));

			if (Integer.parseInt(resident.get("age")) <= 18) {

				mapOfChild.put("childs", indexChild++);
			} else {
				mapOfAdultsAndChild.put("adults", indexAdult++);
			}
		}

		System.out.println("resident of map" + mapOfAdultsAndChild);
		return mapOfAdultsAndChild;
	}
}
