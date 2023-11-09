package com.safetynet.api.service.alertssafetynetservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.safetynet.api.model.MedicalRecord;

/*@SpringBootTest
class FloodServiceTest {
	@Autowired
	private FloodService floodServiceUnderTest;
	
	@MockBean
	private SearchingInfoOfResidentOfStationNumberImpl infoOfResidentOfStationNumber;
	
	@MockBean
	private SearchingInfoOfResidentsByAddressWithMedicalRecordImpl searchingFullInfoOfResidentsWithMedicalRecord;
	

	private  Map<String, String> residentTest1;
	private  Map<String, String> residentTest2;
	private  MedicalRecord medicalRecordTest1;
	private  MedicalRecord medicalRecordTest2;
	private  List<Map<String, String>> listOfResidentsOfStationNumberExpectedTest;
	private  List<Map<String, String>> listOfResidentWithMedicalRecordExpectedTest ;
	private 	Map<String, String> mapMedicalRecordTest1; 
	private 	Map<String, String> mapMedicalRecordTest2; 

	@BeforeEach
	void setUp() {
		residentTest1 = new HashMap<String, String>();
		residentTest1.put("lastName", "Leperlier");
		residentTest1.put("phone", "841-874-6512");
		residentTest1.put("age", "34");
		residentTest2 = new HashMap<String, String>();
		residentTest2.put("lastName", "Leperlier");
		residentTest2.put("phone", "841-874-7878");
		residentTest2.put("age", "37");
		List<String> medications = new ArrayList<String>();
		medications.add("aznol:350mg");
		medications.add("hydrapermazol:100mg");
		List<String> allergies = new ArrayList<String>();
		allergies.add("nillacilan");
		medicalRecordTest1 = new MedicalRecord("Millie", "Leperlier", "09/04/1989", medications, allergies);
		medicalRecordTest2 = new MedicalRecord("John", "Leperlier", "03/08/1986", medications, allergies);
		mapMedicalRecordTest1 = new LinkedHashMap<String, String>();
		mapMedicalRecordTest1.put("medications", medicalRecordTest1.getMedications().toString());
		mapMedicalRecordTest1.put("allergies", medicalRecordTest1.getAllergies().toString());
		mapMedicalRecordTest2 = new LinkedHashMap<String, String>();
		mapMedicalRecordTest2.put("medications", medicalRecordTest2.getMedications().toString());
		mapMedicalRecordTest2.put("allergies", medicalRecordTest2.getAllergies().toString());

		listOfResidentsOfStationNumberExpectedTest = new ArrayList<Map<String, String>>();
		listOfResidentsOfStationNumberExpectedTest.add(residentTest1);
		listOfResidentsOfStationNumberExpectedTest.add(residentTest2);
	}
	
	@Test
	void testGetListOfHouseHoldByStationNumber() {
	
		when(infoOfResidentOfStationNumber.searchInfoOfResident("8")).thenReturn(listOfResidentsOfStationNumberExpectedTest);
		listOfResidentWithMedicalRecordExpectedTest =listOfResidentsOfStationNumberExpectedTest;
		listOfResidentWithMedicalRecordExpectedTest.add(mapMedicalRecordTest1);
		listOfResidentWithMedicalRecordExpectedTest.add(mapMedicalRecordTest2);
		when(searchingFullInfoOfResidentsWithMedicalRecord.searchInfoOfResident("112 address")).thenReturn(listOfResidentWithMedicalRecordExpectedTest);
		try {
		
			List<Object> resultListOfHouseHoldOfStationNumber=floodServiceUnderTest.getListOfHouseHoldByStationNumber("8");
			//System.out.println(resultListOfHouseHoldOfStationNumber.contains(listOfResidentsOfStationNumberExpectedTest)); 
			//verify(residentsOfStationNumberService).getListOfResidentsOfStationNumber(any(String.class));
			//verify(searchingFullInfoOfResidentsWithMedicalRecord).searchInfoOfResident(any(String.class));
			assertThat(resultListOfHouseHoldOfStationNumber).contains("Leperlier");
			/*.filteredOn("lastName", "Marrack")
			.filteredOn("lastName", "Zemicks")
			.filteredOn("lastName", "Cadigan")
			.filteredOn("lastName", "Boyd")
			.filteredOn("lastName", "Carman")
			.filteredOn("lastName", "Shepard")
			.filteredOn("lastName", "Cadigan")
			.filteredOn("lastName", "Fergusson")
			.filteredOn("lastName", "Cooper")
			.filteredOn("lastName", "Peters");
			
			assertEquals(resultListOfHouseHoldOfStationNumber.size(),16);
			//assertThat(resultListOfHouseHoldOfStationNumber).contains(mapOfFireStationFoundByAddressFireExpectedTest);	
		}catch (AssertionError e) {
				fail(e.getMessage());
		}
	}

}*/
