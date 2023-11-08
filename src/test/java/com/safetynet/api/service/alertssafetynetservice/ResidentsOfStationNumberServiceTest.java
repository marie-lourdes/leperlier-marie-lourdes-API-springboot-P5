package com.safetynet.api.service.alertssafetynetservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
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
class ResidentsOfStationNumberServiceTest {
	@Autowired
	private ResidentsOfStationNumberService residentsOfStationNumberServiceUnderTest;
	
	@MockBean
	private SearchingInfoOfResidentOfStationNumberImpl infoOfResidentOfStationNumber;

	@MockBean
	private SortingAdultsAndChildsOfListOfResidentsWithCountDown countDownOfAdultsAndChilds;
	

	private static Map<String, String>residentFoundByStationNumberTest1;
	private static Map<String, String>residentFoundByStationNumberTest2;
	private static List<Map<String, String>>listOfResidentsFoundByStationNumberTest;
	private static Map<String, Integer> countDownTest;
	
	@BeforeAll
	static void setUp() {
		residentFoundByStationNumberTest1 = new HashMap<String, String>();
		residentFoundByStationNumberTest1.put("firstName", "Millie");
		residentFoundByStationNumberTest1.put("lastName", "Leperlier");
		residentFoundByStationNumberTest1.put("address", "112 address");
		residentFoundByStationNumberTest1.put("phone", "841-874-6512");
		residentFoundByStationNumberTest1.put("age", "34");
		residentFoundByStationNumberTest2 = new HashMap<String, String>();
		residentFoundByStationNumberTest2.put("firstName", "Maelys");
		residentFoundByStationNumberTest2.put("lastName", "Leperlier");
		residentFoundByStationNumberTest2.put("address", "112 address");
		residentFoundByStationNumberTest2.put("phone", "841-874-6512");
		residentFoundByStationNumberTest2.put("age", "8");
		listOfResidentsFoundByStationNumberTest = new ArrayList<Map<String, String>>();
		listOfResidentsFoundByStationNumberTest.add(residentFoundByStationNumberTest1);
		listOfResidentsFoundByStationNumberTest.add(residentFoundByStationNumberTest2);
		countDownTest = new HashMap<String,Integer>();
		countDownTest.put("adults", 1);
		countDownTest.put("childs", 1);
	}
	
	@Test
	void testGetListOfResidentsOfStationNumber() throws Exception {
		when(infoOfResidentOfStationNumber.searchInfoOfResident("5")).thenReturn(listOfResidentsFoundByStationNumberTest);
		
		try {
		List<Map<String, String>> resultListOfResidentOfStationNumber=residentsOfStationNumberServiceUnderTest.getListOfResidentsOfStationNumber("5");
		
		verify(infoOfResidentOfStationNumber).searchInfoOfResident(any(String.class));
		assertThat(resultListOfResidentOfStationNumber).contains(residentFoundByStationNumberTest1);
		assertThat(resultListOfResidentOfStationNumber).contains(residentFoundByStationNumberTest2);
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	void testGetListOfResidentsOfStationNumber_WithResidentsNotFoundByStationNumber() throws Exception {
		when(infoOfResidentOfStationNumber.searchInfoOfResident("6")).thenReturn( new ArrayList<Map<String, String>>());
		
		try {
		List<Map<String, String>> resultListOfResidentOfStationNumber=residentsOfStationNumberServiceUnderTest.getListOfResidentsOfStationNumber("6");
		
		verify(infoOfResidentOfStationNumber).searchInfoOfResident(any(String.class));
		assertThat(resultListOfResidentOfStationNumber).isEmpty();
		
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class,
					() ->residentsOfStationNumberServiceUnderTest.getListOfResidentsOfStationNumber("6"));
		}catch (AssertionError e) {
			fail(e.getMessage());
		}
	}
	
}
