package com.safetynet.api.service.alertssafetynetservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.safetynet.api.model.Person;
import com.safetynet.api.service.dataservice.PersonService;

@SpringBootTest
class SearchingInfoEmailOfResidentsByCityImplTest {
	@Autowired
	private SearchingInfoEmailOfResidentsByCityImpl infoOfResidentsByCityUnderTest;

	@MockBean
	private PersonService personService;

	private static Person personTest;
	private static List<Person> listOfPersonsTest;

	@BeforeAll
	static void setUp() {
		personTest = new Person("Millie", "Leperlier", "112 address", "Dax", "97451", "841-874-2512",
				"millie@email.com");
		personTest.setId("Millie Leperlier");
		listOfPersonsTest = new ArrayList<Person>();
		listOfPersonsTest.add(personTest);
	}

	@Test
	void testSearchInfoOfResident() throws Exception {
		when(personService.getPersonsByCity( "Dax")).thenReturn(listOfPersonsTest);
		try {
			List<Map<String, String>> resultListOfResidentsFoundByCity =  infoOfResidentsByCityUnderTest
					.searchInfoOfResident("Dax");
			
			verify(personService).getPersonsByCity(any(String.class));
			assertFalse(resultListOfResidentsFoundByCity.isEmpty());		
			//assertion email of resident of map generated by the method searchInfoOfResident with city 'dax'
			assertThat(resultListOfResidentsFoundByCity).filteredOn("email",personTest.getEmail()).isNotEmpty();
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testSearchInfoOfResident_WithResidentNotFoundByCity() throws Exception {
		when(personService.getPersonsByCity( "Dax")).thenThrow(NullPointerException.class);
		try {
			List<Map<String, String>> resultListOfResidentsFoundByCity =  infoOfResidentsByCityUnderTest
					.searchInfoOfResident("Dax");
			
			verify(personService).getPersonsByCity(any(String.class));
			assertTrue(resultListOfResidentsFoundByCity.isEmpty());
			
		}catch (NullPointerException e) {
			assertThrows(NullPointerException.class,
					() ->  infoOfResidentsByCityUnderTest
					.searchInfoOfResident("Dax"));
		}catch (AssertionError e) {
			fail(e.getMessage());
		}
	}
}
