package com.safetynet.api.service.alertssafetynetservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.api.service.dataservice.FireStationService;

@Service
public class FireService {

	@Autowired
	FireStationService fireStationService;

	@Autowired
	SearchingFullInfoOfResidentsByAddressWithMedicalRecordImpl searchingFullInfoOfResidentsWithMedicalRecord;

	public List<Object> getListOfResidentsAndFireStationNearFire(String address) {
		List<Map<String, String>> listOfResidentWithMedicalRecord = new ArrayList<Map<String, String>>();
		listOfResidentWithMedicalRecord = searchingFullInfoOfResidentsWithMedicalRecord.searchInfoOfResident(address);
		List<Object> listOfResidentAndFireStationNearFire = new ArrayList<Object>();

		Map<String, String> mapOfFireStationFoundByAddressFire = new HashMap<String, String>();
		String fireStationFoundByAddressFire = fireStationService.getOneFireStationByAddress(address)
				.getStationNumber();
		mapOfFireStationFoundByAddressFire.put("stationNumber", fireStationFoundByAddressFire);

		listOfResidentAndFireStationNearFire.add(listOfResidentWithMedicalRecord);
		System.out.println("listOfResidentWithMedicalRecord" + listOfResidentWithMedicalRecord);
		listOfResidentAndFireStationNearFire.add(mapOfFireStationFoundByAddressFire);

		return listOfResidentAndFireStationNearFire;

	}
}
