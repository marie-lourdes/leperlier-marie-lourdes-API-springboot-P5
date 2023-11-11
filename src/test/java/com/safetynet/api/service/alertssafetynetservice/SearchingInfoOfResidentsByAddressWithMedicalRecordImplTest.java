package com.safetynet.api.service.alertssafetynetservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.service.dataservice.MedicalRecordService;

@SpringBootTest
class SearchingInfoOfResidentsByAddressWithMedicalRecordImplTest {
	@Autowired
	private SearchingInfoOfResidentsByAddressWithMedicalRecordImpl infoOfResidentsByAddressWithMedicalRecordUnderTest;

	@MockBean
	private MedicalRecordService medicalRecordService;

	@MockBean
	private SearchingFullInfoOfResidentsByAddressImpl searchingFullInfoOfResidentsByAddress;

	private static Map<String, String> residentTest;
	private static List<Map<String, String>> listOfResidentsTest;
	private static MedicalRecord medicalRecordTest;

	@BeforeAll
	static void setUp() {
		residentTest = new HashMap<String, String>();
		residentTest.put("firstName", "Millie");
		residentTest.put("lastName", "Leperlier");
		residentTest.put("address", "112 address");
		residentTest.put("city", "City");
		residentTest.put("phone", "841-874-6512");
		residentTest.put("email", "millie@email.com");
		residentTest.put("age", "34");
		listOfResidentsTest = new ArrayList<Map<String, String>>();
		listOfResidentsTest.add(residentTest);

		List<String> medications = new ArrayList<String>();
		medications.add("aznol:350mg");
		medications.add("hydrapermazol:100mg");
		List<String> allergies = new ArrayList<String>();
		allergies.add("nillacilan");
		medicalRecordTest = new MedicalRecord("Millie", "Leperlier", "09/04/1989", medications, allergies);
	}

	@Test
	void testSearchInfoOfResident() throws Exception {
		when(searchingFullInfoOfResidentsByAddress.searchInfoOfResident( "112 address")).thenReturn(listOfResidentsTest);
		when(medicalRecordService.getOneMedicalRecordById( "Millie Leperlier")).thenReturn(medicalRecordTest);
		
		try {
			List<Map<String, String>> resultListOfResidentWithMedicalRecord =  infoOfResidentsByAddressWithMedicalRecordUnderTest
					.searchInfoOfResident("112 address");
			
			Map<String, String>	mapResidentTestExpected = new LinkedHashMap<String, String>();
			mapResidentTestExpected.put("lastName", "Leperlier");
			mapResidentTestExpected.put("phone", "841-874-6512");
			mapResidentTestExpected.put("age", "34");
			Map<String, String>	mapMedicalRecordTestExpected = new LinkedHashMap<String, String>();
			mapMedicalRecordTestExpected.put("medications", medicalRecordTest.getMedications().toString());
			mapMedicalRecordTestExpected.put("allergies",medicalRecordTest.getAllergies().toString());
			
			verify(searchingFullInfoOfResidentsByAddress).searchInfoOfResident(any(String.class));
			verify(medicalRecordService).getOneMedicalRecordById(any(String.class));	
			assertFalse(resultListOfResidentWithMedicalRecord.isEmpty());
			//assertion all data of resident with medical record in map generated by the method searchInfoOfResident with address '112 address'
			assertThat( resultListOfResidentWithMedicalRecord).filteredOn("resident",mapResidentTestExpected);
			assertThat( resultListOfResidentWithMedicalRecord).filteredOn("medicalRecord",mapMedicalRecordTestExpected);		
		}catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	@DisplayName("Given no existing info resident found by address   when generate a map with info of resident with medical record  the method should return error and null")
	void testSearchInfoOfResident_WithResidentNotFoundByAddress() throws Exception {
		when(searchingFullInfoOfResidentsByAddress.searchInfoOfResident( "45 no existing address")).thenThrow(NullPointerException.class);
		when(medicalRecordService.getOneMedicalRecordById( "Millie Leperlier")).thenThrow(NullPointerException.class);
		try {
			List<Map<String, String>> resultListOfResidentWithMedicalRecord =  infoOfResidentsByAddressWithMedicalRecordUnderTest
					.searchInfoOfResident("45 no existing address");
			
			verify(searchingFullInfoOfResidentsByAddress).searchInfoOfResident(any(String.class));
			verify(medicalRecordService,Mockito.times(0)).getOneMedicalRecordById(any(String.class));
			assertTrue(resultListOfResidentWithMedicalRecord.isEmpty());
		}catch (NullPointerException e) {
			assertThrows(NullPointerException.class,
					() ->   infoOfResidentsByAddressWithMedicalRecordUnderTest
					.searchInfoOfResident("45 no existing address"));
		}catch (AssertionError e) {
			fail(e.getMessage());
		}
	}
}
