package com.safetynet.api.service.alertssafetynetservice;

import java.util.ArrayList;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.service.dataservice.MedicalRecordService;

@Component
public class SearchingInfoPersonByAddressWithMedicalRecordImpl implements ISearchingInfoOfResident {
	private static final Logger log = LogManager.getLogger(SearchingInfoPersonByAddressWithMedicalRecordImpl.class);

	@Autowired
	private SearchingFullInfoOfResidentsByAddressImpl searchingFullInfoOfResidentsByAddress;

	@Autowired
	private MedicalRecordService medicalRecordService;

	@Override
	public List<Map<String, String>> searchInfoOfResident(String address) {
		log.debug("Searching info of residents  by address with medical record ");

		List<Map<String, String>> listOfPersonWithMedicalRecord = new ArrayList<Map<String, String>>();

		try {
			List<Map<String, String>> listOfPersonByAddress = searchingFullInfoOfResidentsByAddress
					.searchInfoOfResident(address);
			for (Map<String, String> person : listOfPersonByAddress) {
				Map<String, String> mapOfMedicalRecord = new LinkedHashMap<String, String>();
				Map<String, String> mapOfMedicalRecordOfPersonUpdated = new LinkedHashMap<String, String>();
				String fullNamePerson = person.get("firstName") + " " + person.get("lastName");
				MedicalRecord medicalRecordFoundByFullName = medicalRecordService
						.getOneMedicalRecordById(fullNamePerson);
				person.remove("firstName");
				person.remove("city");
				person.remove("phone");

				mapOfMedicalRecord.put("medications", medicalRecordFoundByFullName.getMedications().toString());
				mapOfMedicalRecord.put("allergies", medicalRecordFoundByFullName.getAllergies().toString());

				mapOfMedicalRecordOfPersonUpdated.put("person", person.toString());
				mapOfMedicalRecordOfPersonUpdated.put("medicalRecord", mapOfMedicalRecord.toString());

				listOfPersonWithMedicalRecord.add(mapOfMedicalRecordOfPersonUpdated);
			}
		} catch (Exception e) {
			log.error("An error has occured in searching info of person found by address with medical records");
		}

		log.debug("Info of residents  by address with medical record succesfully retrieved : {}",
				listOfPersonWithMedicalRecord);
		return listOfPersonWithMedicalRecord;
	}
}
