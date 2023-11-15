package com.safetynet.api.service.alertssafetynetservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResidentsOfStationNumberService {
	private static final Logger log = LogManager.getLogger(ResidentsOfStationNumberService.class);

	@Autowired
	SearchingInfoOfResidentOfStationNumberImpl infoOfResidentOfStationNumber;

	@Autowired
	SortingAdultsAndChildsOfListOfResidentsWithCountDown countDownOfAdultsAndChilds;

	private List<Map<String, String>> listOfResidentsOfStationNumber = new ArrayList<Map<String, String>>();

	public List<Map<String, String>> getListOfResidentsOfStationNumber(String stationNumber) {
		log.debug("Retrieving  all residents of station number {}", stationNumber);

		try {

			listOfResidentsOfStationNumber = infoOfResidentOfStationNumber.searchInfoOfResident(stationNumber);
			for (Map<String, String> residents : listOfResidentsOfStationNumber) {
				residents.remove("age");
			}

			log.info(" List of residents retrieved successfully of station number {}", listOfResidentsOfStationNumber);

		} catch (Exception e) {
			log.error("Failed to retrieve all residents of station number {}", stationNumber);
		}

		return listOfResidentsOfStationNumber;
	}

	public Map<String, String> sortAdultsAndChildsOfListOfResidentsWithCountDown(String stationNumber) {
		Map<String, Integer> mapCountDownOfAdultsAndChilds = new HashMap<String, Integer>();
		Map<String, String> mapOfAdultsAndChildConvertedValueString = new HashMap<String, String>();
		
		try {
			mapCountDownOfAdultsAndChilds = countDownOfAdultsAndChilds.sortAdultsAndChilds(stationNumber);

			for (Map.Entry<String, Integer> entry : mapCountDownOfAdultsAndChilds.entrySet()) {
				mapOfAdultsAndChildConvertedValueString.put(entry.getKey(), entry.getValue().toString());
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		
		return mapOfAdultsAndChildConvertedValueString;
	}
}