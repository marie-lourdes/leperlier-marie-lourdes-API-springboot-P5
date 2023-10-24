package com.safetynet.api.service.alertssafetynetservice;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.service.dataservice.MedicalRecordService;

@Service
public class SearchingInfoOfResidentsByAddressWithMedicalRecordImpl implements ISearchingInfoOfResident {
	private static final Logger log = LogManager.getLogger(SearchingInfoOfResidentsByAddressWithMedicalRecordImpl .class);
	@Autowired
	SearchingFullInfoOfResidentsByAddressImpl searchingFullInfoOfResidentsByAddress;

	@Autowired
	MedicalRecordService medicalRecordService;

	@Override
	public List<Map<String, String>> searchInfoOfResident(String address) {
		List<Map<String, String>> listOfResidentWithMedicalRecord = new ArrayList<Map<String, String>>();
		
		try {
		List<Map<String, String>> listOfResidentsByAddress = searchingFullInfoOfResidentsByAddress
				.searchInfoOfResident(address);
	
		for (Map<String, String> resident : listOfResidentsByAddress) {
			Map<String, String> mapOfMedicalRecord = new LinkedHashMap<String, String>();
			Map<String, String> mapOfMedicalRecordOfResidentUpdated = new LinkedHashMap<String, String>();
			String fullNamePerson = resident.get("firstName") + " " + resident.get("lastName");
			MedicalRecord medicalRecordFoundByFullName = medicalRecordService.getOneMedicalRecordById(fullNamePerson);
			resident.remove("firstName");

			resident.remove("zip");
			resident.remove("city");
			resident.remove("email");
			resident.remove("address");
			mapOfMedicalRecord.put("medications", medicalRecordFoundByFullName.getMedications().toString());
			mapOfMedicalRecord.put("allergies", medicalRecordFoundByFullName.getAllergies().toString());
		
			mapOfMedicalRecordOfResidentUpdated.put("resident", resident.toString());
			mapOfMedicalRecordOfResidentUpdated.put("medicalRecord", mapOfMedicalRecord.toString());
		
			listOfResidentWithMedicalRecord.add(mapOfMedicalRecordOfResidentUpdated);

		}
		}catch(Exception e) {
			e.printStackTrace();
			log.error( "An error has occured in searching info of residents by address with medical records");
		}
		return listOfResidentWithMedicalRecord;
	}

}
