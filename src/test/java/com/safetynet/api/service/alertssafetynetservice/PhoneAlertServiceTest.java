package com.safetynet.api.service.alertssafetynetservice;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class PhoneAlertServiceTest {
	@Autowired
	private PhoneAlertService phoneAlertServiceUnderTest;

	@MockBean
	private SearchingInfoPhoneOfResidentsByStationNumberImpl infoPhoneOfResidentsByStationNumber;

	private static Map<String, String> phoneOfResidentFoundByStationNumberTest1;
	private static Map<String, String> phoneOfResidentFoundByStationNumberTest2;
	private static List<Map<String, String>> listOfphonesOfResidentFoundByStationNumberTest1;

	@BeforeAll
	static void setUp() {
		phoneOfResidentFoundByStationNumberTest1 = new HashMap<String, String>();
		phoneOfResidentFoundByStationNumberTest1.put("phone", "841-874-7878");
		phoneOfResidentFoundByStationNumberTest2 = new HashMap<String, String>();
		phoneOfResidentFoundByStationNumberTest1.put("phone", "841-874-7878");
		phoneOfResidentFoundByStationNumberTest1.put("phone", "841-874-6512");
		listOfphonesOfResidentFoundByStationNumberTest1 = new ArrayList<Map<String, String>>();
		listOfphonesOfResidentFoundByStationNumberTest1.add(phoneOfResidentFoundByStationNumberTest1);
		listOfphonesOfResidentFoundByStationNumberTest1.add(phoneOfResidentFoundByStationNumberTest2);
	}

	@Test
	void testGetListOfPhonesOfResidentsOfStationNumber() throws Exception {
		when(infoPhoneOfResidentsByStationNumber.searchInfoOfResident("5")).thenReturn(listOfphonesOfResidentFoundByStationNumberTest1);
		
		try {
			List<Map<String, String>> resultListOfResidentChildAndMembersOfHouseHold=phoneAlertServiceUnderTest.getListOfPhonesOfResidentsOfStationNumber("5");
			
			verify(infoPhoneOfResidentsByStationNumber).searchInfoOfResident(any(String.class));
			assertEquals(listOfphonesOfResidentFoundByStationNumberTest1,resultListOfResidentChildAndMembersOfHouseHold);
			} catch (AssertionError e) {
				fail(e.getMessage());
			}
	}

	@Test
	void testGetListOfPhonesOfResidentsOfStationNumber_WithPhonesNotFoundByStationNumber() throws Exception {
		when(infoPhoneOfResidentsByStationNumber.searchInfoOfResident("6")).thenReturn( new ArrayList<Map<String, String>>());
		try {
			List<Map<String, String>> resultListOfResidentChildAndMembersOfHouseHold=phoneAlertServiceUnderTest.getListOfPhonesOfResidentsOfStationNumber("6");
			
			verify(infoPhoneOfResidentsByStationNumber).searchInfoOfResident(any(String.class));
			assertTrue(resultListOfResidentChildAndMembersOfHouseHold.isEmpty());
			} catch (NullPointerException e) {
				assertThrows(NullPointerException.class,
						() ->phoneAlertServiceUnderTest.getListOfPhonesOfResidentsOfStationNumber("6"));
			} catch (AssertionError e) {
				fail(e.getMessage());
			}
	}

}
