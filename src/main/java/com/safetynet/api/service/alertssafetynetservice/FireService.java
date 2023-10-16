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
		// Optional<MedicalRecord>medicalRecordFoundByFullName = Optional.empty();
		String fireStationFoundByAddressFire = fireStationService.getOneFireStationByAddress(address).get()
				.getStationNumber();

		for (Map<String, String> resident : listOfResidentNearFire) {
			Map<String, Object> mapOfMedicalRecordOfResident = new HashMap<String, Object>();
			String fullNamePerson = resident.get("firstName") + " " + resident.get("lastName");
			Optional<MedicalRecord> medicalRecordFoundByFullName = medicalRecordService
					.getOneMedicalRecordById(fullNamePerson);

			mapOfMedicalRecordOfResident.put("medications", medicalRecordFoundByFullName.get().getMedications());
			mapOfMedicalRecordOfResident.put("allergies", medicalRecordFoundByFullName.get().getAllergies());
			listOfResidentWithMedicalRecord.add(resident);
			listOfResidentWithMedicalRecord.add(mapOfMedicalRecordOfResident);
		}

		listOfResidentAndFireStationNearFire.add(listOfResidentWithMedicalRecord);
		System.out.println("listOfResidentAndFireStationNearFire" + listOfResidentWithMedicalRecord);
		listOfResidentAndFireStationNearFire.add(fireStationFoundByAddressFire);

		return listOfResidentAndFireStationNearFire;

	}
}
