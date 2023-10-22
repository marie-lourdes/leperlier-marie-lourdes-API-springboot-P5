package com.safetynet.api.service.alertssafetynetservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.api.service.dataservice.PersonService;

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
			log.info(" All residents retrieved of station number {}", stationNumber);		
		} catch (NullPointerException e) {
			log.error("Failed to retrieve all residents of station number {}", stationNumber);
			throw new NullPointerException("Residents not found at this station number: " + stationNumber);
		}
		for (Map<String, String> residents : listOfResidentOfStationNumber) {
			residents.remove("age");
			System.out.println("residents" + residents);
		}
		log.info(" List of residents retrieved of station number {}",listOfResidentOfStationNumber);
		return listOfResidentOfStationNumber;
	}

	public Map<String, Integer> sortAdultsAndChildsOfListOfResidentsWithCountDown(String stationNumber)
			throws NullPointerException {
		log.debug("Sorting  all residents by category adult and child of station number {}", stationNumber);
		Map<String, Integer> mapCountDownOfAdultsAndChilds = countDownOfAdultsAndChilds
				.sortAdultsAndChilds(stationNumber, listOfResidentOfStationNumber);
		if (mapCountDownOfAdultsAndChilds.isEmpty()) {
			throw new NullPointerException(
					"Error has occured sorting Adults and childs  because not found at this station number");
		}else {
			log.debug("all residents  sorted by category adult and child of station number {}", stationNumber);
		}
		
		return mapCountDownOfAdultsAndChilds;
	}
}
