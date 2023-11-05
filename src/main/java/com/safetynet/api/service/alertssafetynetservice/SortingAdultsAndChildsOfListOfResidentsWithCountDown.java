package com.safetynet.api.service.alertssafetynetservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SortingAdultsAndChildsOfListOfResidentsWithCountDown {
	private static final Logger log = LogManager.getLogger(SortingAdultsAndChildsOfListOfResidentsWithCountDown.class);

	private Map<String, Integer> mapOfAdultsAndChild = new HashMap<String, Integer>();

	public Map<String, Integer> sortAdultsAndChilds(String request,
			List<Map<String, String>> ResidentsOfStationNumber) {
		try {
			Integer indexChild = 1;
			Integer indexAdult = 1;

			for (Map<String, String> resident : ResidentsOfStationNumber) {
				if (Integer.parseInt(resident.get("age")) <= 18) {
					mapOfAdultsAndChild.put("childs", indexChild++);
					resident.remove("age");
				} else {
					mapOfAdultsAndChild.put("adults", indexAdult++);
					resident.remove("age");
				}
			}
		} catch (Exception e) {
			log.error("An error has occured in sorting residents with countdown of adults and childs");
		}
		log.debug("List of residents  sorted with coundDown of Adults And Childs: " + mapOfAdultsAndChild);
		return mapOfAdultsAndChild;
	}
}
