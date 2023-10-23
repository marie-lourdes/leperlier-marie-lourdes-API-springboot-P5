package com.safetynet.api.service.alertssafetynetservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhoneAlertService {
	private static final Logger log = LogManager.getLogger( PhoneAlertService .class);
	
	@Autowired
	SearchingInfoPhoneOfResidentsByStationNumberImpl infoPhoneOfResidentsByStationNumber;

	private List<Map<String, String>> listOfPhonesOfResidentOfStationNumber = new ArrayList<Map<String, String>>();;
	public List<Map<String, String>> getListOfPhonesOfResidentsOfStationNumber(String stationNumber)
			throws NullPointerException {
		log.debug("Retrieving all phones of residents of firestation");
		try {
			listOfPhonesOfResidentOfStationNumber =  infoPhoneOfResidentsByStationNumber.searchInfoOfResident(stationNumber);
		} catch (NullPointerException e) {
			log.error("Failed to retrieve phones of this firestation : {}",stationNumber);
			throw new NullPointerException("Not phone of resident found of this firestation : "+ stationNumber);
		}
		
		log.info("List of phones of residents of firestation retrieved successfully : {}", listOfPhonesOfResidentOfStationNumber);
		return  listOfPhonesOfResidentOfStationNumber;
	}
}
