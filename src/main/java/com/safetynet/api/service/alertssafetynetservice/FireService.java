package com.safetynet.api.service.alertssafetynetservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.api.model.FireStation;
import com.safetynet.api.service.dataservice.FireStationService;

@Service
public class FireService {
	@Autowired
	SearchingFullInfoOfResidentsByAddressImpl infoOfResidentsByAddress;
	
	@Autowired
	FireStationService fireStationService ;
	
	private List<Map<String, String>> listOfResidentNearFire;
	private Map<String, String> mapOfFireStationNearFire;
	private List<Map<String, String>> listOfResidentAndFireStationNearFire;
	public List<Map<String, String>> getListOfResidentsAndFireStationNearFire(String address) {
		listOfResidentNearFire=new ArrayList<Map<String, String>>();
		listOfResidentNearFire=infoOfResidentsByAddress.searchInfoOfResident(address);
		String fireStationFoundByAddressFire= fireStationService.getOneFireStationByAddress(address).get().getStationNumber();
		
		mapOfFireStationNearFire.put("firestation", fireStationFoundByAddressFire );
		listOfResidentAndFireStationNearFire.add(mapOfFireStationNearFire);
		
		
		return null;
		
		
	}
}
