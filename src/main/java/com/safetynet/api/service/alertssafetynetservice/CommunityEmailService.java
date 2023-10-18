package com.safetynet.api.service.alertssafetynetservice;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommunityEmailService {
@Autowired
SearchingInfoEmailOfResidentsByCitympl infoEmailOfResidentsByCity;

public List<Map<String, String>> getEmailOfResidentsOfCity(String city) {
	return infoEmailOfResidentsByCity.searchInfoOfResident(city);
}
}
