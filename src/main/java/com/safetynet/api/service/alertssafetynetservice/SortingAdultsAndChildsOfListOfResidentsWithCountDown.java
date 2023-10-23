package com.safetynet.api.service.alertssafetynetservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SortingAdultsAndChildsOfListOfResidentsWithCountDown {
	@Autowired
	SearchingInfoOfResidentOfStationNumberImpl infoOfResidentOfStationNumber;
	
	@Autowired
	CalculatorAgeOfResidentImpl calculatorAgeOfResident;

	private Map<String, Integer> mapOfAdultsAndChild = new HashMap<String, Integer>();

	public Map<String, Integer> sortAdultsAndChilds(String request,List<Map<String, String>> ResidentsOfStationNumber) {
		Integer indexChild = 1;
		Integer indexAdult = 1;
		for (Map<String, String> resident : ResidentsOfStationNumber) {
			resident.put("age",
					calculatorAgeOfResident.calculateAgeOfResident(resident.get("firstName")+" " + resident.get("lastName")).toString());
			if (Integer.parseInt(resident.get("age")) <= 18) {

				mapOfAdultsAndChild.put("childs", indexChild++);
				resident.remove("age");
			} else {
				mapOfAdultsAndChild.put("adults", indexAdult++);
				resident.remove("age");
			}
		}
	
		System.out.println("resident Adults And Child sorted" + mapOfAdultsAndChild);
		return mapOfAdultsAndChild;
	}
}
