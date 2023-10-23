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
public class PhoneAlertService {
	private static final Logger log = LogManager.getLogger( PhoneAlertService .class);
	
	@Autowired
	SearchingInfoOfResidentOfStationNumberImpl infoOfResidentOfStationNumber;
	
	private List<Map<String, String>> listOfPhonesOfResidentOfStationNumber;

	public List<Map<String, String>> getListOfPhonesOfResidentsOfStationNumber(String stationNumber)
			throws NullPointerException {
		log.debug("Retrieving all phones of residents of firestation");
		try {
			listOfPhonesOfResidentOfStationNumber = new ArrayList<Map<String, String>>();
			listOfPhonesOfResidentOfStationNumber = infoOfResidentOfStationNumber.searchInfoOfResident(stationNumber);
	
		} catch (NullPointerException e) {
			log.error("Failed to retrieve phones of this firestation : {}",stationNumber);
			throw new NullPointerException("Not phone of resident found of this firestation : "+ stationNumber);
		}

		for (Map<String, String> residents : listOfPhonesOfResidentOfStationNumber) {
			residents.remove("firstName");
			residents.remove("lastName");
			residents.remove("address");
			residents.remove("age");
			System.out.println("residents" + residents);
		}
		log.info("List of phones of residents of firestation retrieved successfully : {}", listOfPhonesOfResidentOfStationNumber);
		return listOfPhonesOfResidentOfStationNumber;
	}
}
