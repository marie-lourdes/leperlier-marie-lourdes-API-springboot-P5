package com.safetynet.api.service.alertssafetynetservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.service.dataservice.FireStationService;
import com.safetynet.api.service.dataservice.MedicalRecordService;

@Service
public class FireService {
	@Autowired
	SearchingFullInfoOfResidentsByAddressImpl infoOfResidentsByAddress;

	@Autowired
	FireStationService fireStationService;

	@Autowired
	MedicalRecordService medicalRecordService;

	public List<Object> getListOfResidentsAndFireStationNearFire(String address) {
		List<Map<String, String>> listOfResidentNearFire = infoOfResidentsByAddress.searchInfoOfResident(address);
		List<Object> listOfResidentAndFireStationNearFire = new ArrayList<Object>();
		List<Object> listOfResidentWithMedicalRecord = new ArrayList<Object>();
		String fireStationFoundByAddressFire = fireStationService.getOneFireStationByAddress(address).get()
				.getStationNumber();

		for (Map<String, String> resident : listOfResidentNearFire) {
			Map<String, String> mapOfMedicalRecordOfResident = new HashMap<String, String>();
			
			String fullNamePerson = resident.get("firstName") + " " + resident.get("lastName");
			Optional<MedicalRecord> medicalRecordFoundByFullName = medicalRecordService
					.getOneMedicalRecordById(fullNamePerson);
			resident.remove("firstName");
			resident.remove("address");
			resident.remove("city");
			resident.remove("email");
			mapOfMedicalRecordOfResident.put("medications", medicalRecordFoundByFullName.get().getMedications().toString());
			mapOfMedicalRecordOfResident.put("allergies", medicalRecordFoundByFullName.get().getAllergies().toString());
			
			resident.putAll(mapOfMedicalRecordOfResident);
			
			listOfResidentWithMedicalRecord.add(resident);
		}

		listOfResidentAndFireStationNearFire.add(listOfResidentWithMedicalRecord);
		System.out.println("listOfResidentAndFireStationNearFire" + listOfResidentWithMedicalRecord);
		listOfResidentAndFireStationNearFire.add(fireStationFoundByAddressFire);

		return listOfResidentAndFireStationNearFire;

	}
}
