package com.safetynet.api.service.alertssafetynetservice;

import java.util.ArrayList;
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

	private List<Map<String, String>> listOfResidentOfStationNumber;

	public List<Map<String, String>> getListOfResidentsOfStationNumber(String stationNumber)
			throws NullPointerException {
		log.debug("Retrieving  all residents of station number {}", stationNumber);

		try {
			listOfResidentOfStationNumber = new ArrayList<Map<String, String>>();
			listOfResidentOfStationNumber = infoOfResidentOfStationNumber.searchInfoOfResident(stationNumber);

			if (listOfResidentOfStationNumber.isEmpty()) {
				throw new NullPointerException("Residents not found at this station number: " + stationNumber);
			}
			log.info(" List of residents retrieved successfully of station number {}", listOfResidentOfStationNumber);
		} catch (Exception e) {
			log.error("Failed to retrieve all residents of station number {}", stationNumber);
		} 

		for (Map<String, String> residents : listOfResidentOfStationNumber) {
			// residents.remove("age");
			System.out.println("residents" + residents);
		}

		return listOfResidentOfStationNumber;
	}

	public Map<String, Integer> sortAdultsAndChildsOfListOfResidentsWithCountDown(String stationNumber) {
		Map<String, Integer> mapCountDownOfAdultsAndChilds = null;
		try {
			mapCountDownOfAdultsAndChilds = countDownOfAdultsAndChilds
					.sortAdultsAndChilds(listOfResidentOfStationNumber, stationNumber);
			if (mapCountDownOfAdultsAndChilds.isEmpty()) {
				throw new NullPointerException(
						"Error has occured sorting with countdown of adult and childs  because not found at this station number");
			} else {
				log.debug("all residents  sorted with countdown of adult and child of station number {}",
						stationNumber);			
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return mapCountDownOfAdultsAndChilds;
	}
}
