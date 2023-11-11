package com.safetynet.api.service.alertssafetynetservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommunityEmailService {
	private static final Logger log = LogManager.getLogger(CommunityEmailService.class);

	@Autowired
	SearchingInfoEmailOfResidentsByCityImpl infoEmailOfResidentsByCity;

	private List<Map<String, String>> listEmailsOfResidentsOfCity = new ArrayList<Map<String, String>>();

	public List<Map<String, String>> getEmailOfResidentsOfCity(String city) {
		log.debug("Retrieving all emails of residents of the city : {}", city);

		try {
			listEmailsOfResidentsOfCity = infoEmailOfResidentsByCity.searchInfoOfResident(city);
		} catch (Exception e) {
			log.error("Failed to retrieve emails of residents of the city : {}", city);	
		}

		log.info(" All emails of residents retrieved of the city  {} : {}", city, listEmailsOfResidentsOfCity);
		return listEmailsOfResidentsOfCity;
	}
}
