package com.safetynet.api.service.alertssafetynetservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.service.dataservice.FireStationService;

@SpringBootTest
class FireServiceTest {
	@Autowired
	private FireService fireServiceUnderTest;

	@MockBean
	private FireStationService fireStationService;

	@MockBean
	SearchingInfoOfResidentsByAddressWithMedicalRecordImpl fullInfoOfResidentsWithMedicalRecord;

	private static Map<String, String> residentTest;
	private static MedicalRecord medicalRecordTest;
	private static List<Map<String, String>> listOfPersonWithMedicalRecordExpectedTest;
	private static FireStation fireStationTest1;
	private static List<FireStation> listOfFireStationTest;
	private static Map<String, String> mapOfFireStationFoundByAddressFireExpectedTest;

	@BeforeAll
	static void setUp() {
		residentTest = new HashMap<String, String>();
		residentTest.put("lastName", "Leperlier");
		residentTest.put("phone", "841-874-6512");
		residentTest.put("age", "34");

		List<String> medications = new ArrayList<String>();
		medications.add("aznol:350mg");
		medications.add("hydrapermazol:100mg");
		List<String> allergies = new ArrayList<String>();
		allergies.add("nillacilan");
		medicalRecordTest = new MedicalRecord("Millie", "Leperlier", "09/04/1989", medications, allergies);
		Map<String, String> mapMedicalRecordTest = new LinkedHashMap<String, String>();
		mapMedicalRecordTest.put("medications", medicalRecordTest.getMedications().toString());
		mapMedicalRecordTest.put("allergies", medicalRecordTest.getAllergies().toString());

		listOfPersonWithMedicalRecordExpectedTest = new ArrayList<Map<String, String>>();
		listOfPersonWithMedicalRecordExpectedTest.add(residentTest);
		listOfPersonWithMedicalRecordExpectedTest.add(mapMedicalRecordTest);

		listOfFireStationTest = new ArrayList<FireStation>();
		fireStationTest1 = new FireStation("7", "17 impasse de la caserne");
		listOfFireStationTest.add(fireStationTest1);
		fireStationTest1 = new FireStation("7", "17 impasse de la caserne");
		listOfFireStationTest.add(fireStationTest1);
		mapOfFireStationFoundByAddressFireExpectedTest = new HashMap<String, String>();
		mapOfFireStationFoundByAddressFireExpectedTest.put("stationNumber", "7");

	}

	@Test
	void testGetListOfResidentsAndFireStationNearFire() throws Exception{
		when(fullInfoOfResidentsWithMedicalRecord.searchInfoOfResident("112 address")).thenReturn(	listOfPersonWithMedicalRecordExpectedTest);
		when(fireStationService.getFireStationsByAddress("112 address")).thenReturn(listOfFireStationTest);
		
		try {
			List<Map<String, String>> resultListOfResidentAndFireStationNearFire=fireServiceUnderTest.getListOfResidentsAndFireStationNearFire("112 address");
			
			verify(fullInfoOfResidentsWithMedicalRecord).searchInfoOfResident(any(String.class));
			verify(fireStationService).getFireStationsByAddress(any(String.class));
			assertThat(resultListOfResidentAndFireStationNearFire).contains(listOfPersonWithMedicalRecordExpectedTest);
			assertThat(resultListOfResidentAndFireStationNearFire).contains(mapOfFireStationFoundByAddressFireExpectedTest);	
		}catch (AssertionError e) {
			fail(e.getMessage());
		}	
			
	}

	@Test
	void testGetListOfResidentsAndFireStationNearFire_WithResidentsNotFoundByAddress() throws Exception {
		when(fullInfoOfResidentsWithMedicalRecord.searchInfoOfResident("112 address")).thenThrow(NullPointerException.class);
		when(fireStationService.getFireStationsByAddress("112 address")).thenReturn(listOfFireStationTest);
		
		try {
			List<Object> resultListOfResidentAndFireStationNearFire=fireServiceUnderTest.getListOfResidentsAndFireStationNearFire("112 address");
			
			verify(fullInfoOfResidentsWithMedicalRecord).searchInfoOfResident(any(String.class));
			verify(fireStationService).getFireStationsByAddress(any(String.class));
			assertThat(resultListOfResidentAndFireStationNearFire).isEmpty();
		} catch (NullPointerException e) {
					assertThrows(NullPointerException.class,
							() ->fireServiceUnderTest.getListOfResidentsAndFireStationNearFire("112 address"));
		}catch (AssertionError e) {
				fail(e.getMessage());
		}
	}

	@Test
	void testGetListOfResidentsAndFireStationNearFire_WithFireStationNotFoundByAddress() throws Exception {
		when(fullInfoOfResidentsWithMedicalRecord.searchInfoOfResident("112 address")).thenReturn(listOfPersonWithMedicalRecordExpectedTest);
		when(fireStationService.getFireStationsByAddress("112 address")).thenThrow(NullPointerException.class);
		
		try {
			List<Object> resultListOfResidentAndFireStationNearFire=fireServiceUnderTest.getListOfResidentsAndFireStationNearFire("112 address");
			
			verify(fullInfoOfResidentsWithMedicalRecord).searchInfoOfResident(any(String.class));
			verify(fireStationService).getFireStationsByAddress(any(String.class));
			assertThat(resultListOfResidentAndFireStationNearFire).isEmpty();
		}catch (NullPointerException e) {
				assertThrows(NullPointerException.class,
						() ->fireServiceUnderTest.getListOfResidentsAndFireStationNearFire("112 address"));
		}catch (AssertionError e) {
				fail(e.getMessage());
		}
	}
}
