package com.safetynet.api.service.alertssafetynetservice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FloodService {
	@Autowired
	ResidentsOfStationNumberService residentsOfStationNumberService;
	
	@Autowired
	SearchingFullInfoOfResidentsWithMedicalRecordImpl searchingFullInfoOfResidentsWithMedicalRecord;
	
	List< String> listOfAddress = new ArrayList <String>();
	
	public List<Object> getListOfHouseHoldByStationNumber(String stationNumber) {
		List<Map<String, String>> listOfResidentsOfStationNumber =residentsOfStationNumberService. getListOfResidentsOfStationNumber(stationNumber);
		List<Object> listOfHouseHoldOfStationNumber = new ArrayList<Object>();
	 
		System.out.println("listOfResidentsOfStationNumber of flood service" + listOfResidentsOfStationNumber);
		ListIterator<Map<String,String>> itrResidentsOfStationNumber = listOfResidentsOfStationNumber.listIterator();
		
		while(itrResidentsOfStationNumber.hasNext()) {
			Map<String, String>	itrResidentNextToNext = itrResidentsOfStationNumber.next();
		
				if(!listOfAddress.contains(itrResidentNextToNext.get("address"))) {	
					listOfAddress.add(itrResidentNextToNext.get("address"));
					//itrResidentNextToNext =	itrResidentsOfStationNumber.next();
				}
				
				
		}
		for(String address:listOfAddress ) {
			List<Map<String, String>> listOfResidentWithMedicalRecord = new ArrayList<Map<String, String>>();
			 listOfResidentWithMedicalRecord=searchingFullInfoOfResidentsWithMedicalRecord.searchInfoOfResident(address);
			/*List<Map<String, String>> listOfHouseHoldSameaddress = searchingFullInfoOfResidentsByAddress.searchInfoOfResident(address);	
			List<Object> listOfResidentWithMedicalRecord = new ArrayList<Object>();*/
			/*for (Map<String, String> resident : listOfHouseHoldSameaddress) {
				
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
				
			}*/
			 listOfHouseHoldOfStationNumber.add(listOfResidentWithMedicalRecord);
			
		}	
		System.out.println(" listOfHouseHoldOfStationNumber"+ listOfHouseHoldOfStationNumber);
		System.out.println("listOfAddress"+listOfAddress);
		
		return listOfHouseHoldOfStationNumber ;
	}
}
