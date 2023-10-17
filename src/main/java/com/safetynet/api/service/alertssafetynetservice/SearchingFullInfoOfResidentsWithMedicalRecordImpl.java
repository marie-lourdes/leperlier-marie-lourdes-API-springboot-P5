package com.safetynet.api.service.alertssafetynetservice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.service.dataservice.MedicalRecordService;

@Service
public class SearchingFullInfoOfResidentsWithMedicalRecordImpl implements ISearchingInfoOfResident  {
	
	@Autowired
	SearchingFullInfoOfResidentsByAddressImpl searchingFullInfoOfResidentsByAddress ;
	
	@Autowired
	MedicalRecordService medicalRecordService;
	
	@Override
	public List<Map<String, String>> searchInfoOfResident(String address) {
		List<Map<String, String>> listOfHouseHoldSameaddress = searchingFullInfoOfResidentsByAddress.searchInfoOfResident(address);	
		List<Map<String, String>> listOfResidentWithMedicalRecord = new ArrayList<Map<String, String>>();
		for (Map<String, String> resident : listOfHouseHoldSameaddress) {
			
			Map<String, String> mapOfMedicalRecord = new HashMap<String, String>();
			Map<String, String> mapOfMedicalRecordOfResidentUpdated = new HashMap<String, String>();
			String fullNamePerson = resident.get("firstName") + " " + resident.get("lastName");
			Optional<MedicalRecord> medicalRecordFoundByFullName = medicalRecordService
					.getOneMedicalRecordById(fullNamePerson);
			resident.remove("firstName");

			resident.remove("city");
			resident.remove("email");
			mapOfMedicalRecord.put("medications", medicalRecordFoundByFullName.get().getMedications().toString());
			mapOfMedicalRecord.put("allergies", medicalRecordFoundByFullName.get().getAllergies().toString());
			
			mapOfMedicalRecordOfResidentUpdated.put("medicalRecord", mapOfMedicalRecord.toString());
			mapOfMedicalRecordOfResidentUpdated.put("resident", resident.toString());
			TreeMap<String, String> treeMapOfMedicalRecordOfResidentUpdated =
					 new TreeMap<String, String>(Collections.reverseOrder());
			
			treeMapOfMedicalRecordOfResidentUpdated.putAll(mapOfMedicalRecordOfResidentUpdated);
			listOfResidentWithMedicalRecord.add(treeMapOfMedicalRecordOfResidentUpdated);
			
		}
		return listOfResidentWithMedicalRecord;
	}
	
}