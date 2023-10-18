package com.safetynet.api.service.alertssafetynetservice;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FloodService {
	@Autowired
	ResidentsOfStationNumberService residentsOfStationNumberService;

	@Autowired
	SearchingFullInfoOfResidentsByAddressWithMedicalRecordImpl searchingFullInfoOfResidentsWithMedicalRecord;

	public List<Object> getListOfHouseHoldByStationNumber(String stationNumber) {
		
		List<Object> listOfHouseHoldOfStationNumber = new ArrayList<Object>();
		List<Map<String, String>> listOfResidentsOfStationNumber = residentsOfStationNumberService
				.getListOfResidentsOfStationNumber(stationNumber);

		ListIterator<Map<String, String>> itrResidentsOfStationNumber = listOfResidentsOfStationNumber.listIterator();
		List<String> listOfAddress = new ArrayList<String>();
		while (itrResidentsOfStationNumber.hasNext()) {
			Map<String, String> itrResidentNextToNext = itrResidentsOfStationNumber.next();

			if (!listOfAddress.contains(itrResidentNextToNext.get("address"))) {
				listOfAddress.add(itrResidentNextToNext.get("address"));
			}
		}

		for (String address : listOfAddress) {
			List<Map<String, String>> listOfResidentWithMedicalRecord = new ArrayList<Map<String, String>>();
			listOfResidentWithMedicalRecord = searchingFullInfoOfResidentsWithMedicalRecord
					.searchInfoOfResident(address);
			listOfHouseHoldOfStationNumber.add(listOfResidentWithMedicalRecord);

		}
		System.out.println(" listOfHouseHoldOfStationNumber" + listOfHouseHoldOfStationNumber);
		System.out.println("listOfAddress" + listOfAddress);

		return listOfHouseHoldOfStationNumber;
	}
}
