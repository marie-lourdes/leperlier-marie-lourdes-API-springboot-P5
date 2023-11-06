package com.safetynet.api.service.alertssafetynetservice;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.safetynet.api.model.Person;
import com.safetynet.api.service.dataservice.PersonService;

@SpringBootTest
class SearchingFullInfoOfResidentsByAddressImplTest {
	@Autowired
	SearchingFullInfoOfResidentsByAddressImpl searchingFullInfoOfResidentsByAddress;

	@MockBean
	PersonService personService;
	
	@MockBean
	CalculatorAgeOfResidentImpl calculatorAge;

	private static Person personTest;
	private static List<Person> listOfPersonsTest;

	@BeforeAll
	static void setUp() {
		personTest = new Person("Millie", "Leperlier", "112 address", "city", "97451", "841-874-2512",
				"millie@email.com");
		personTest.setId("Millie Leperlier");
		listOfPersonsTest = new ArrayList<Person>();
		listOfPersonsTest.add(personTest);
	}

	@Test
	void testSearchInfoOfResident() throws Exception {
		when(personService.getPersonsByAddress( "112 address")).thenReturn(listOfPersonsTest);
		when(calculatorAge.calculateAgeOfResident("Millie Leperlier")).thenReturn(BigInteger.valueOf(34));

		try {
			List<Map<String, String>> listOfResidentsWithSameAddress = searchingFullInfoOfResidentsByAddress
					.searchInfoOfResident("112 address");
			verify(personService).getPersonsByAddress(any(String.class));
			verify(calculatorAge).calculateAgeOfResident(any(String.class));
			assertFalse(listOfResidentsWithSameAddress.isEmpty());
			
			for (Map<String, String> resident : listOfResidentsWithSameAddress) {
				assertAll(
						"assertion all data of resident in map generated by the method searchInfoOfResident with address '112 address'",
						() -> assertEquals(personTest.getFirstName(), resident.get("firstName")),
						() -> assertEquals(personTest.getLastName(), resident.get("lastName")),
						() -> assertEquals(personTest.getAddress(), resident.get("address")),
						() -> assertEquals(personTest.getCity(), resident.get("city")),
						() -> assertEquals(personTest.getZip(), resident.get("zip")),
						() -> assertEquals(personTest.getPhone(), resident.get("phone")),
						() -> assertEquals(personTest.getEmail(), resident.get("email")),
						() -> assertEquals("34", resident.get("age")));
			}
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	@DisplayName("Given no existing resident found by address   when generate a map with info of resident  the method should return error and null")
	void testSearchInfoOfResident_WithResidentNotFoundByAddress() throws Exception {
		when(personService.getPersonsByAddress("112 address")).thenThrow(NullPointerException.class);
		
		try {
			List<Map<String, String>> listOfResidentsWithSameAddress = searchingFullInfoOfResidentsByAddress.searchInfoOfResident("112 address");
			verify(personService).getPersonsByAddress(any(String.class));
			verify(calculatorAge, Mockito.times(0)).calculateAgeOfResident(any(String.class));
			assertTrue(  listOfResidentsWithSameAddress.isEmpty());
		}catch (NullPointerException e) {
					assertThrows(NullPointerException.class,
							() -> searchingFullInfoOfResidentsByAddress
							.searchInfoOfResident("112 address"));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}	
	}

	@Test
	@DisplayName("Given resident found by address  with birthdate incorrect when generate a map with info of resident  the method should return 'error!' in key'age' instead age '0' ")
	void testSearchInfoOfResident_WithAgeOfResidentNotCalculated() throws Exception {
		when(personService.getPersonsByAddress( "112 address")).thenReturn(listOfPersonsTest);
		when(calculatorAge.calculateAgeOfResident("Millie Leperlier")).thenReturn(BigInteger.valueOf(0));
		
		try {
			List<Map<String, String>> listOfResidentsWithSameAddress = searchingFullInfoOfResidentsByAddress
					.searchInfoOfResident("112 address");
		verify(personService).getPersonsByAddress(any(String.class));
			verify(calculatorAge).calculateAgeOfResident(any(String.class));
			assertNotNull(listOfResidentsWithSameAddress);
			for (Map<String, String> resident : listOfResidentsWithSameAddress) {
				assertEquals(" error! ", resident.get("age"));
			}
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class,
					() -> searchingFullInfoOfResidentsByAddress.searchInfoOfResident("112 address"));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}
}
