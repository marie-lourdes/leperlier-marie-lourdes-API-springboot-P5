package com.safetynet.api.service.alertssafetynetservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhoneAlertService {
	@Autowired
	SearchingInfoOfResidentOfStationNumberImpl infoOfResidentOfStationNumber;
	private List<Map<String, String>> listOfPhonesOfResidentOfStationNumber;

	public List<Map<String, String>> getListOfPhonesOfResidentsOfStationNumber(String stationNumber)
			throws NullPointerException {
		try {

			listOfPhonesOfResidentOfStationNumber = new ArrayList<Map<String, String>>();
			listOfPhonesOfResidentOfStationNumber = infoOfResidentOfStationNumber.searchInfoOfResident(stationNumber);
		} catch (NullPointerException e) {
			throw new NullPointerException("Not phone of resident found of this firestation");
		}

		for (Map<String, String> residents : listOfPhonesOfResidentOfStationNumber) {
			residents.remove("firstName");
			residents.remove("lastName");
			residents.remove("address");
			residents.remove("age");
			System.out.println("residents" + residents);
		}

		System.out.println("list of phones of resident" + listOfPhonesOfResidentOfStationNumber);
		return listOfPhonesOfResidentOfStationNumber;
	}
}
