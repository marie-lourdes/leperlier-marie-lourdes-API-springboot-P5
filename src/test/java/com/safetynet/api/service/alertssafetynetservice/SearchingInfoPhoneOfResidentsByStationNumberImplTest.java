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
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.Person;
import com.safetynet.api.service.dataservice.FireStationService;
import com.safetynet.api.service.dataservice.PersonService;
@SpringBootTest
class SearchingInfoPhoneOfResidentsByStationNumberImplTest {
	@Autowired
	SearchingInfoPhoneOfResidentsByStationNumberImpl infoPhoneOfResidentsByStationNumberUnderTest;

	@MockBean
	FireStationService fireStationService;

	@MockBean
	PersonService personService;
	
	
	private static Person personTest1;
	private static Person personTest2;
	private static List<Person> listOfPersonsTest;
	private static FireStation firestationTest1;
	private static FireStation firestationTest2;
	private static List<FireStation> listOfFireStationsTest;
	
	@BeforeAll
	static void setUp() {
		personTest1 = new Person("Millie", "Leperlier", "45 quartier du port de Vannes","Vannes", "56000","841-874-2512",
				"millie@email.com");
		personTest1.setId("Millie Leperlier");
		personTest2 = new Person("Minnie", "Cooper", "12 rue des combattants", "Vannes", "40100", "841-874-7784",
				"millie@email.com");
		personTest2.setId("John Leperlier");
		listOfPersonsTest = new ArrayList<Person>();
		listOfPersonsTest.add(personTest1);
		listOfPersonsTest.add(personTest2);
		
		firestationTest1= new FireStation("5", "45 quartier du port de Vannes");
		firestationTest1.setId("45-5-65");
		firestationTest2= new FireStation("5", "112 address");
		firestationTest1.setId("112-5-41");
		listOfFireStationsTest = new ArrayList<FireStation>();
		listOfFireStationsTest.add(firestationTest1);
		listOfFireStationsTest.add(firestationTest2);
	}
	
	@Test
	void testSearchInfoOfResident() throws Exception {
		when(personService.getAllPersons()).thenReturn(listOfPersonsTest);
		when(fireStationService.getFireStationsByStationNumber( "5")).thenReturn(listOfFireStationsTest);
		
		try {
			List<Map<String, String>> listOfPhonesOfResidentsOfStationNumber= infoPhoneOfResidentsByStationNumberUnderTest
					.searchInfoOfResident("5");
			
			verify(personService).getAllPersons();
			verify(fireStationService).getFireStationsByStationNumber(any(String.class));
			assertFalse(listOfPhonesOfResidentsOfStationNumber.isEmpty());
			//assertion all phones of residents in of map generated by the method searchInfoOfResident with station number '5'
			assertThat(listOfPhonesOfResidentsOfStationNumber).filteredOn("phone",personTest1.getPhone());
			assertThat(listOfPhonesOfResidentsOfStationNumber).filteredOn("phone",personTest2.getPhone());
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	void testSearchInfoOfResident_WithPhonesNotFoundByStationNumber() throws Exception {
		when(personService.getAllPersons()).thenThrow(NullPointerException.class);
		when(fireStationService.getFireStationsByStationNumber( "6")).thenThrow(NullPointerException.class);
		
		try {
			List<Map<String, String>> listOfPhonesOfResidentsOfStationNumber= infoPhoneOfResidentsByStationNumberUnderTest
					.searchInfoOfResident("6");
			
			verify(personService).getAllPersons();
			verify(fireStationService).getFireStationsByStationNumber(any(String.class));
			assertTrue(listOfPhonesOfResidentsOfStationNumber.isEmpty());
		}catch (NullPointerException e) {
					assertThrows(NullPointerException.class,
							() -> infoPhoneOfResidentsByStationNumberUnderTest
							.searchInfoOfResident("6"));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}	
	}
}
