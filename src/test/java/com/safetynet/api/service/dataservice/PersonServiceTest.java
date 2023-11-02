package com.safetynet.api.service.dataservice;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.safetynet.api.model.Person;

@SpringBootTest
class PersonServiceTest {
	@Autowired
	private static PersonService personServiceUnderTest;
	
	private static Person personTest1;
	private static List<Person> persons;
	private static Person personCreated;
	private static Person personUpdated;
	
	@BeforeAll
	static void setUp() throws IOException {
		personTest1 = new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "jaboyd@email.com");
		personCreated = new Person("Millie", "Boyd", "100 rue de Dax", "Dax", "40100", "841-874-6512",
				"millie@email.com");
		personUpdated = new Person("1509 Culver St modified", "Culver", "97451", "841-874-6512",
				"jaboyd@email.com");
		personServiceUnderTest.addPerson(personTest1);
		//personServiceUnderTest.addPerson(	personCreated );
	}

	// using for test
	@AfterAll
void reInitPersons() {
		persons.clear();
	}
	@Test
	void testAddPerson() throws Exception {
		
		personServiceUnderTest.addPerson(personCreated);

		Person resultPersonCreatedRetrieved = personServiceUnderTest.getOnePersonById("Millie Boyd");
		String expectedFirstName = personCreated.getFirstName();
		String expectedLastName = personCreated.getLastName();
		String expectedAddress = personCreated.getAddress();
		String expectedCity = personCreated.getCity();
		String expectedZip = personCreated.getZip();
		String expectedPhone = personCreated.getPhone();
		String expectedEmail = personCreated.getEmail();
		assertAll("assertion all data of personTest1 created found by id", () -> assertNotNull(resultPersonCreatedRetrieved),
				// checking setting id with fullname in method addPerson() of PersonService
				() -> assertEquals(expectedFirstName + " " + expectedLastName, resultPersonCreatedRetrieved.getId(),
						"the id should  be the first name and last name of personTest1"),
				() -> assertEquals(expectedFirstName, resultPersonCreatedRetrieved.getFirstName()),
				() -> assertEquals(expectedLastName, resultPersonCreatedRetrieved.getLastName()),
				() -> assertEquals(expectedAddress, resultPersonCreatedRetrieved.getAddress()),
				() -> assertEquals(expectedCity, resultPersonCreatedRetrieved.getCity()),
				() -> assertEquals(expectedZip, resultPersonCreatedRetrieved.getZip()),
				() -> assertEquals(expectedPhone, resultPersonCreatedRetrieved.getPhone()),
				() -> assertEquals(expectedEmail, resultPersonCreatedRetrieved.getEmail()));
	}

	@Test
	void testUpdatePerson() throws Exception {
		Person personTest1Updated = new Person("1509 Culver St modified", "Culver", "97451", "841-874-6512",
				"jaboyd@email.com");
		try {
			// existing personTest1 before updating
			Person resultPersonRetrieved = personServiceUnderTest.getOnePersonById("John Boyd");

			// existing personTest1 after updating
			Person resultPersonUpdatedRetrieved = personServiceUnderTest.updatePerson("John Boyd", personTest1Updated);

			String expectedFirstName = resultPersonRetrieved.getFirstName();
			String expectedLastName = resultPersonRetrieved.getLastName();
			String expectedAddress = personTest1Updated.getAddress();
			String expectedCity = resultPersonRetrieved.getCity();
			String expectedZip = resultPersonRetrieved.getZip();
			String expectedPhone = resultPersonRetrieved.getPhone();
			String expectedEmail = resultPersonRetrieved.getEmail();
			assertAll("assertion all data of personTest1 updated found by id",
					() -> assertNotNull(resultPersonUpdatedRetrieved),
					() -> assertSame(resultPersonRetrieved, resultPersonUpdatedRetrieved),

					// checking if id with fullname and all datas except address of existing personTest1
					// don't change when updating
					// and checking if address of existing personTest1 change when updating new address
					() -> assertEquals(resultPersonRetrieved.getId(), resultPersonUpdatedRetrieved.getId(),
							"the id should  be the first name and last name of personTest1"),
					() -> assertEquals(expectedFirstName, resultPersonUpdatedRetrieved.getFirstName()),
					() -> assertEquals(expectedLastName, resultPersonUpdatedRetrieved.getLastName()),
					() -> assertEquals(expectedAddress, resultPersonUpdatedRetrieved.getAddress()),
					() -> assertEquals(expectedCity, resultPersonUpdatedRetrieved.getCity()),
					() -> assertEquals(expectedZip, resultPersonUpdatedRetrieved.getZip()),
					() -> assertEquals(expectedPhone, resultPersonUpdatedRetrieved.getPhone()),
					() -> assertEquals(expectedEmail, resultPersonUpdatedRetrieved.getEmail()));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testUpdatePerson_WithIncorrectId() throws Exception {
		try {
			Person resultPersonUpdated = personServiceUnderTest.updatePerson("John Boy", personUpdated);

			assertNull(resultPersonUpdated);
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class,
					() -> personServiceUnderTest.updatePerson("John Boy", personUpdated));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testDeleteOnePersonById() throws Exception {
		personServiceUnderTest.addPerson(personCreated);

		try {
			boolean resultPersonRemoved = personServiceUnderTest.deleteOnePersonById("Millie Boyd");
			assertTrue(resultPersonRemoved);		
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testDeleteOnePersonById_WithIncorrectId() throws Exception {
		personServiceUnderTest.addPerson(personCreated);

		try {
			boolean resultPersonRemoved = personServiceUnderTest.deleteOnePersonById("Millie Boy");
			assertFalse(resultPersonRemoved);
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class, () -> personServiceUnderTest.deleteOnePersonById("Millie Boyd"));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testGetOnePersonById() throws Exception {
		try {
			Person resultPerson = personServiceUnderTest.getOnePersonById("John Boyd");

			String expectedFirstName = personTest1.getFirstName();
			String expectedLastName = personTest1.getLastName();
			String expectedAddress = personTest1.getAddress();
			String expectedCity = personTest1.getCity();
			String expectedZip = personTest1.getZip();
			String expectedPhone = personTest1.getPhone();
			String expectedEmail = personTest1.getEmail();
			assertAll("assertion all data of personTest1 found by id", () -> assertNotNull(resultPerson),
					() -> assertEquals(expectedFirstName + " " + expectedLastName, resultPerson.getId(),
							"the id should  be the first name and last name of personTest1"),
					() -> assertEquals(expectedFirstName, resultPerson.getFirstName()),
					() -> assertEquals(expectedLastName, resultPerson.getLastName()),
					() -> assertEquals(expectedAddress, resultPerson.getAddress()),
					() -> assertEquals(expectedCity, resultPerson.getCity()),
					() -> assertEquals(expectedZip, resultPerson.getZip()),
					() -> assertEquals(expectedPhone, resultPerson.getPhone()),
					() -> assertEquals(expectedEmail, resultPerson.getEmail()));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testGetOnePersonById_WithIdNotFound() throws Exception {
		try {
			Person resultPerson = personServiceUnderTest.getOnePersonById("John Lenon");

			assertNull(resultPerson);
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class, () -> personServiceUnderTest.getOnePersonById("John Lenon"));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}

	}

	@Test
	void testGetPersonsByLastName() throws Exception {
		try {
			List<Person> resultPersons = personServiceUnderTest.getPersonsByLastName("Boyd");

			String expectedLastName = personTest1.getLastName();
			for (Person personFoundByLastName : resultPersons) {
				assertEquals(expectedLastName, personFoundByLastName.getLastName());
			}
			assertFalse(resultPersons.isEmpty());
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testGePersonsByLastName_WithLastNameNotFound() throws Exception {
		try {
			List<Person> resultPersons = personServiceUnderTest.getPersonsByLastName("Boy");

			assertTrue(resultPersons.isEmpty());
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class, () -> personServiceUnderTest.getPersonsByLastName("Boy"));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testGetPersonsByAddress() throws Exception {
		try {
			List<Person> resultPersons = personServiceUnderTest.getPersonsByAddress("1509 Culver St");
			String expectedAddress = personTest1.getAddress();

			for (Person personFoundByAddress : resultPersons) {
				assertEquals(expectedAddress, personFoundByAddress.getAddress());
			}
			assertFalse(resultPersons.isEmpty());
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testGetPersonsByAddress_WithAddressNotFound() throws Exception {
		try {
			List<Person> resultPersons = personServiceUnderTest.getPersonsByAddress("112 address no existing");

			assertTrue(resultPersons.isEmpty());
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class,
					() -> personServiceUnderTest.getPersonsByAddress("112 address no existing"));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testGetPersonsByCity() throws Exception {
		try {
			List<Person> resultPersons = personServiceUnderTest.getPersonsByCity("Culver");

			String expectedCity = personTest1.getCity();
			for (Person personFoundByCity : resultPersons) {
				assertEquals(expectedCity, personFoundByCity.getCity());
			}
			assertFalse(resultPersons.isEmpty());
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testGetPersonsByCity_WithCityNotFound() throws Exception {
		try {
			List<Person> resultPersons = personServiceUnderTest.getPersonsByCity("city no existing");

			assertTrue(resultPersons.isEmpty());
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class, () -> personServiceUnderTest.getPersonsByCity("city no existing"));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testGetAllPersons() throws Exception {
		try {
			List<Person> resultAllPersons = personServiceUnderTest.getAllPersons();

			assertNotNull(resultAllPersons);
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	// assertnull or asserttrue isEmpty? test no stable
	@Test
	void testGetAllPersons_NotFound() throws Exception {
		personServiceUnderTest.reInitPersons();
		try {
			personServiceUnderTest.getAllPersons();
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class, () -> personServiceUnderTest.getAllPersons());
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}
}
