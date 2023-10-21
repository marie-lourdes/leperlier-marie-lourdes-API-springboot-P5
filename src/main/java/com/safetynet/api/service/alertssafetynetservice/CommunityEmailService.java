package com.safetynet.api.service.alertssafetynetservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommunityEmailService {
	@Autowired
	SearchingInfoEmailOfResidentsByCitympl infoEmailOfResidentsByCity;

	private List<Map<String, String>> listEmailsOfResidentsOfCity = new ArrayList<Map<String, String>>();

	public List<Map<String, String>> getEmailOfResidentsOfCity(String city)  throws NullPointerException {
       try {
    	   listEmailsOfResidentsOfCity = infoEmailOfResidentsByCity.searchInfoOfResident(city);
   		System.out.println("listEmailsOfResidentsOfCity" + listEmailsOfResidentsOfCity);
   		
       }catch(NullPointerException e) {
    	   throw new NullPointerException("list of email not found found in this city "+city);
       }
		
		return listEmailsOfResidentsOfCity;
	}
}
