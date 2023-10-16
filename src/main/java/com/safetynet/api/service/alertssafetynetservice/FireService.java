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
	FireStationService fireStationService ;
	
	@Autowired
	MedicalRecordService medicalRecordService;
	
	private List<Map<String, String>> listOfResidentNearFire=new ArrayList<Map<String, String>>();;
	private Map<String, String> mapOfFireStationNearFire = new HashMap<String, String>();
	private Map<String,Object> mapOfMedicalRecordOfResident=new HashMap<String,Object>();

	private Optional<MedicalRecord>medicalRecordFoundByFullName = Optional.empty();
	private List<Object> listOfResidentAndFireStationNearFire = new ArrayList<Object>();
	
	public List<Object> getListOfResidentsAndFireStationNearFire(String address) {
		
		listOfResidentNearFire=infoOfResidentsByAddress.searchInfoOfResident(address);
		
		String fireStationFoundByAddressFire= fireStationService.getOneFireStationByAddress(address).get().getStationNumber();
		
		for(Map<String, String>resident:listOfResidentNearFire) {
			String fullNamePerson= resident.get("firstName")+" "+resident.get("lastName");
			medicalRecordFoundByFullName =medicalRecordService.getOneMedicalRecordById(fullNamePerson);
			
			mapOfMedicalRecordOfResident.put("medications",medicalRecordFoundByFullName.get().getMedications() );
			mapOfMedicalRecordOfResident.put("allergies",medicalRecordFoundByFullName.get().getAllergies() );
			listOfResidentAndFireStationNearFire.add(resident);
			listOfResidentAndFireStationNearFire.add(mapOfMedicalRecordOfResident);
		
			
			
		}
		System.out.println("listOfResidentAndFireStationNearFire"+	listOfResidentAndFireStationNearFire);
		listOfResidentAndFireStationNearFire.add(fireStationFoundByAddressFire);
		//mapOfFireStationNearFire.put("firestation", fireStationFoundByAddressFire );
		//listOfResidentAndFireStationNearFire.add(mapOfFireStationNearFire);
		
		
		return 	listOfResidentAndFireStationNearFire;
		
		
	}
}
