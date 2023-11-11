package com.safetynet.api.service.alertssafetynetservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class SortingAdultsAndChildsOfListOfResidentsWithCountDownTest {
	@Autowired
	private SortingAdultsAndChildsOfListOfResidentsWithCountDown sortingAdultsAndChildsOfListOfResidentsWithCountDownUnderTest;
	
	@MockBean
	SearchingInfoOfResidentOfStationNumberImpl infoOfResidentOfStationNumber;
	
	private static Map<String, String> residentAdultTest1;
	private static Map<String, String> residentChildTest2;
	private static List<Map<String, String>> listOfAdultsAndChildsTest1;
	private static List<Map<String, String>> listOfAdultsAndChildsTest2;

	@BeforeAll
	static void setUp() {
		residentAdultTest1 = new HashMap<String, String>();
		residentAdultTest1.put("firstName", "Millie");
		residentAdultTest1.put("lastName", "Leperlier");
		residentAdultTest1.put("age", "34");
		residentChildTest2 = new HashMap<String, String>();
		residentChildTest2.put("firstName", "Maelys");
		residentChildTest2.put("lastName", "Leperlier");
		residentChildTest2.put("age", "8");
		listOfAdultsAndChildsTest1 = new ArrayList<Map<String, String>>();
		listOfAdultsAndChildsTest1.add(residentAdultTest1);
		listOfAdultsAndChildsTest1.add(residentChildTest2);
		listOfAdultsAndChildsTest2 = new ArrayList<Map<String, String>>();
		listOfAdultsAndChildsTest2.add(residentChildTest2);
	}

	@Test
	void testSortAdultsAndChilds() throws Exception {
		when(infoOfResidentOfStationNumber.searchInfoOfResident("5")).thenReturn(listOfAdultsAndChildsTest1);
		try {
			Map<String, Integer> resultCountDownOfAdultsAndChilds = sortingAdultsAndChildsOfListOfResidentsWithCountDownUnderTest
					.sortAdultsAndChilds( "5",listOfAdultsAndChildsTest1);
				assertThat(resultCountDownOfAdultsAndChilds ).contains(entry("adults", 1), entry("childs", 1));	
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testSortAdultsAndChilds_WithAdultsNotFound_ShouldReturnOnlyCountDownOfChilds() throws Exception {
		when(infoOfResidentOfStationNumber.searchInfoOfResident("6")).thenReturn(listOfAdultsAndChildsTest2);
		try {
			Map<String, Integer> resultCountDownOfAdultsAndChilds = sortingAdultsAndChildsOfListOfResidentsWithCountDownUnderTest
					.sortAdultsAndChilds("6",listOfAdultsAndChildsTest2);
			assertThat(resultCountDownOfAdultsAndChilds).containsOnly(entry("childs", 1));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	void testSortAdultsAndChilds_WithResidentsNotProvided_ShouldReturnMapEmpty() throws Exception {
		try {
			Map<String, Integer> resultCountDownOfAdultsAndChilds = sortingAdultsAndChildsOfListOfResidentsWithCountDownUnderTest
					.sortAdultsAndChilds("8",new ArrayList<Map<String, String>>());
			assertThat(resultCountDownOfAdultsAndChilds).isEmpty();
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}
}
