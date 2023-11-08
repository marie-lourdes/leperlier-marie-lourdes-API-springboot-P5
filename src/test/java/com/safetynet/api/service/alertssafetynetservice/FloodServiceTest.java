package com.safetynet.api.service.alertssafetynetservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.MedicalRecord;

@SpringBootTest
class FloodServiceTest {
	@Autowired
	private FloodService floodServiceUnderTest;
	
	@Spy
	private ResidentsOfStationNumberService residentsOfStationNumberService=new ResidentsOfStationNumberService();

	@MockBean
	private SearchingInfoOfResidentsByAddressWithMedicalRecordImpl searchingFullInfoOfResidentsWithMedicalRecord;
	

	private static Map<String, String> residentTest1;
	private static Map<String, String> residentTest2;
	private static MedicalRecord medicalRecordTest1;
	private static MedicalRecord medicalRecordTest2;
	private static List<Map<String, String>> listOfResidentsOfStationNumberExpectedTest;
	private static List<Map<String, String>> listOfResidentWithMedicalRecordExpectedTest ;
	private static FireStation fireStationTest1;
	private static List<Map<String, String>> listOfFireStationTest;
	private static Map<String, String> mapOfFireStationFoundByAddressFireTest1;
	private static Map<String, String> mapOfFireStationFoundByAddressFireTest2;
	private static	Map<String, String> mapMedicalRecordTest1; 
	private static	Map<String, String> mapMedicalRecordTest2; 

	@BeforeAll
	static void setUp() {
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
		mapMedicalRecordTest1.put("medications", medicalRecordTest2.getMedications().toString());
		mapMedicalRecordTest1.put("allergies", medicalRecordTest2.getAllergies().toString());

		listOfResidentsOfStationNumberExpectedTest = new ArrayList<Map<String, String>>();
		listOfResidentsOfStationNumberExpectedTest.add(residentTest1);
		listOfResidentsOfStationNumberExpectedTest.add(residentTest2);
	}
	
	@Test
	void testGetListOfHouseHoldByStationNumber() {
	
		doReturn(listOfResidentsOfStationNumberExpectedTest).when(residentsOfStationNumberService.getListOfResidentsOfStationNumber("8"));
		listOfResidentWithMedicalRecordExpectedTest =listOfResidentsOfStationNumberExpectedTest;
		listOfResidentWithMedicalRecordExpectedTest.add(mapMedicalRecordTest1);
		listOfResidentWithMedicalRecordExpectedTest.add(mapMedicalRecordTest2);
		when(searchingFullInfoOfResidentsWithMedicalRecord.searchInfoOfResident("112 address")).thenReturn(listOfResidentWithMedicalRecordExpectedTest);
		try {
			List<Object> resultListOfHouseHoldOfStationNumber=floodServiceUnderTest.getListOfHouseHoldByStationNumber("8");
			
			verify(residentsOfStationNumberService).getListOfResidentsOfStationNumber(any(String.class));
			verify(searchingFullInfoOfResidentsWithMedicalRecord).searchInfoOfResident(any(String.class));
			assertThat(resultListOfHouseHoldOfStationNumber).contains(listOfResidentWithMedicalRecordExpectedTest);
			//assertThat(resultListOfHouseHoldOfStationNumber).contains(mapOfFireStationFoundByAddressFireExpectedTest);	
		}catch (AssertionError e) {
				fail(e.getMessage());
		}
	}

}
