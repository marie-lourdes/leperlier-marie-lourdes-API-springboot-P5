package com.safetynet.api.service.alertssafetynetservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class SortingAdultsAndChildsOfListOfResidentsWithFirstNameAndLastNameTest {
	@Autowired
	 SortingAdultsAndChildsOfListOfResidentsWithFirstNameAndLastName  sortingAdultsAndChildsOfListOfResidentsWithFullNameUnderTest;
	
	@MockBean
	SearchingFullInfoOfResidentsByAddressImpl fullInfoOfResidentWithSameAddress;


	private static Map<String, String> residentAdultTestExpected1;
	private static Map<String, String> residentChildTestExpected2;
	private static List<Map<String, String>> listOfAdultsAndChildsTest1;
	private static List<Map<String, String>> listOfAdultsAndChildsTest2;

	@BeforeAll
	static void setUp() {
		residentAdultTestExpected1 = new HashMap<String, String>();
		residentAdultTestExpected1.put("firstName", "Millie");
		residentAdultTestExpected1.put("lastName", "Leperlier");
		residentAdultTestExpected1.put("address", "112 address");
		residentAdultTestExpected1.put("city", "City");
		residentAdultTestExpected1.put("zip", "45569");
		residentAdultTestExpected1.put("phone", "841-874-6512");
		residentAdultTestExpected1.put("email", "millie@email.com");
		residentAdultTestExpected1.put("age", "34");
		
		residentChildTestExpected2 = new HashMap<String, String>();
		residentChildTestExpected2.put("firstName", "Maelys");
		residentChildTestExpected2.put("lastName", "Leperlier");
		residentChildTestExpected2.put("address", "112 address");
		residentChildTestExpected2.put("city", "City");
		residentChildTestExpected2.put("zip", "45569");
		residentChildTestExpected2.put("phone", "841-874-6512");
		residentChildTestExpected2.put("email", "millie@email.com");
		residentChildTestExpected2.put("age", "8");
		listOfAdultsAndChildsTest1 = new ArrayList<Map<String, String>>();
		listOfAdultsAndChildsTest1.add(residentAdultTestExpected1);
		listOfAdultsAndChildsTest1.add(residentChildTestExpected2);
		listOfAdultsAndChildsTest2 = new ArrayList<Map<String, String>>();
		listOfAdultsAndChildsTest2.add(residentChildTestExpected2);
	}

	@Test
	@DisplayName("test sorting adults and childs  should return adults with full data without age and return childs only with first name, last name and age")
	void testSortAdultsAndChilds() throws Exception {
		when(fullInfoOfResidentWithSameAddress.searchInfoOfResident("112 address")).thenReturn(listOfAdultsAndChildsTest1);
		
		try {
			List<Map<String, String>> infoOfAdultsAndChilds = sortingAdultsAndChildsOfListOfResidentsWithFullNameUnderTest
					.sortAdultsAndChilds("112 address");
			
			verify(fullInfoOfResidentWithSameAddress).searchInfoOfResident(any(String.class));
			//assertion data of adult sorted
			assertThat(infoOfAdultsAndChilds).contains(residentAdultTestExpected1)
			.filteredOn("firstName","Millie").filteredOn("age","")
			.filteredOn("lastName","Leperlier")
			.filteredOn("address", "112 address")
			.filteredOn("city", "City")
			.filteredOn("zip", "45569")
			.filteredOn("phone", "841-874-6512")
			.filteredOn("email", "millie@email.com");
		
			//assertion data of child sorted
			assertThat(infoOfAdultsAndChilds).contains(residentChildTestExpected2)
			.filteredOn("firstName","Maelys")
			.filteredOn("lastName","Leperlier")
			.filteredOn("age","8");
			
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	void testSortAdultsAndChilds_WithAdultsNotFound_ShouldReturnOnlyChilds() throws Exception {
		when(fullInfoOfResidentWithSameAddress.searchInfoOfResident("112 address")).thenReturn(listOfAdultsAndChildsTest2);
		
		try {
			List<Map<String, String>>  infoOfAdultsAndChilds  = sortingAdultsAndChildsOfListOfResidentsWithFullNameUnderTest
					.sortAdultsAndChilds("112 address");
			
			verify(fullInfoOfResidentWithSameAddress).searchInfoOfResident(any(String.class));
			assertThat( infoOfAdultsAndChilds ).filteredOn("firstName", "Millie").isEmpty();
			assertThat( infoOfAdultsAndChilds ).filteredOn("firstName", "Maelys").isNotEmpty();
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testSortAdultsAndChilds_WithResidentsNotProvided_ShouldReturnListEmpty() throws Exception {
		when(fullInfoOfResidentWithSameAddress.searchInfoOfResident("112 address")).thenReturn(new ArrayList<Map<String, String>>());
		
		try {
			List<Map<String, String>>  infoOfAdultsAndChilds= sortingAdultsAndChildsOfListOfResidentsWithFullNameUnderTest
					.sortAdultsAndChilds("112 address");
			
			verify(fullInfoOfResidentWithSameAddress).searchInfoOfResident(any(String.class));
			assertThat(infoOfAdultsAndChilds).isEmpty();
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}
}
