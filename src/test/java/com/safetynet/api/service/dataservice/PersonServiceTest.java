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

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.safetynet.api.model.Person;

@SpringBootTest
class PersonServiceTest {
	@Autowired
	private PersonService personServiceUnderTest;

	private Person personTest1;
	private Person personTest2;
	private List<Person> persons;
	private Person personTest1Updated = new Person("100 rue de Dax modified", "Dax", "97451", "841-874-6512",
			"joe@email.com");

	@BeforeEach
	void setUpPerTest() {
		personTest1 = new Person("John", "Leperlier", "1509 rue Dax", "Dax", "40100", "841-874-6512", "john@email.com");
		personTest2 = new Person("Millie", "Leperlier", "100 rue de Dax", "Dax", "40100", "841-874-6512",
				"millie@email.com");
		personServiceUnderTest.addPerson(personTest1);
		personServiceUnderTest.addPerson(personTest2);
		persons = personServiceUnderTest.getAllPersons();
	}

	@AfterEach
	void reInitPerTest() {
		persons.clear();
	}

	@Test
	void testAddPerson() throws Exception {
		Person PersonCreated = new Person("Minnie", "Cooper", "17 boulevard de New-York", "New-York", "10803",
				"841-874-6512", "millie@email.com");

		personServiceUnderTest.addPerson(PersonCreated);
		try {
			Person resultPersonCreatedRetrieved = personServiceUnderTest.getOnePersonById("Minnie Cooper");

			String expectedFirstName = PersonCreated.getFirstName();
			String expectedLastName = PersonCreated.getLastName();
			String expectedAddress = PersonCreated.getAddress();
			String expectedCity = PersonCreated.getCity();
			String expectedZip = PersonCreated.getZip();
			String expectedPhone = PersonCreated.getPhone();
			String expectedEmail = PersonCreated.getEmail();
			assertAll("assertion all data of person created found by id full name",
					() -> assertNotNull(resultPersonCreatedRetrieved),
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
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testAddPerson_WithPersonDuplicated() throws Exception {
		try {
			Person resultPersonCreated = personServiceUnderTest.addPerson(personTest1);
			persons = personServiceUnderTest.getAllPersons();

			assertNull(resultPersonCreated);
		} catch (IllegalArgumentException e) {
			assertThrows(IllegalArgumentException.class, () -> personServiceUnderTest.addPerson(personTest1));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testUpdateAddressPerson() throws Exception {
		try {

			// existing personTest1 before updating
			Person personRetrievedById = personServiceUnderTest.getOnePersonById("John Leperlier");

			// existing personTest1 after updating
			Person resultPersonUpdatedRetrieved = personServiceUnderTest.updateOnePersonById("John Leperlier",
					personTest1Updated);

			String expectedFirstName = personRetrievedById.getFirstName();
			String expectedLastName = personRetrievedById.getLastName();
			String expectedAddress = personTest1Updated.getAddress();
			String expectedCity = personRetrievedById.getCity();
			String expectedZip = personRetrievedById.getZip();
			String expectedPhone = personRetrievedById.getPhone();
			String expectedEmail = personRetrievedById.getEmail();
			assertAll("assertion all data of personTest1updated found by id",
					() -> assertNotNull(resultPersonUpdatedRetrieved),
					() -> assertSame(personRetrievedById, resultPersonUpdatedRetrieved),

					// checking if id with fullname and all datas except address of existing person
					// don't change when updating

					() -> assertEquals(personRetrievedById.getId(), resultPersonUpdatedRetrieved.getId(),
							"the id should  be the first name and last name of personTest1"),
					() -> assertEquals(expectedFirstName, resultPersonUpdatedRetrieved.getFirstName()),
					() -> assertEquals(expectedLastName, resultPersonUpdatedRetrieved.getLastName()),
					// checking if address of existing person change when updating new address
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
			Person resultPersonUpdated = personServiceUnderTest.updateOnePersonById("John Lenon", personTest1Updated);

			assertNull(resultPersonUpdated);
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class,
					() -> personServiceUnderTest.updateOnePersonById("John Lenon", personTest1Updated));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testDeleteOnePersonById() throws Exception {
		try {
			boolean resultPersonRemoved = personServiceUnderTest.deleteOnePersonById("Millie Leperlier");

			assertTrue(resultPersonRemoved);
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testDeleteOnePersonById_WithIncorrectId() throws Exception {
		try {
			boolean resultPersonRemoved = personServiceUnderTest.deleteOnePersonById("John Lenon");

			assertFalse(resultPersonRemoved);
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class, () -> personServiceUnderTest.deleteOnePersonById("John Lenon"));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testGetOnePersonById() throws Exception {
		try {
			Person resultPerson = personServiceUnderTest.getOnePersonById("John Leperlier");

			String expectedFirstName = personTest1.getFirstName();
			String expectedLastName = personTest1.getLastName();
			String expectedAddress = personTest1.getAddress();
			String expectedCity = personTest1.getCity();
			String expectedZip = personTest1.getZip();
			String expectedPhone = personTest1.getPhone();
			String expectedEmail = personTest1.getEmail();
			assertAll("assertion all data of personTest1 found by id", () -> assertNotNull(resultPerson),
					() -> assertEquals(expectedFirstName + " " + expectedLastName, resultPerson.getId()),
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
			List<Person> resultPersons = personServiceUnderTest.getPersonsByLastName("Leperlier");

			String expectedLastName = "Leperlier";
			int countPersonByLastName = 0;
			for (Person personFoundByLastName : resultPersons) {
				countPersonByLastName++;
				assertEquals(expectedLastName, personFoundByLastName.getLastName());
			}
			assertEquals(2, countPersonByLastName);
			assertFalse(resultPersons.isEmpty());
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testGePersonsByLastName_WithLastNameNotFound() throws Exception {
		try {
			List<Person> resultPersons = personServiceUnderTest.getPersonsByLastName("Lenon");

			assertTrue(resultPersons.isEmpty());
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class, () -> personServiceUnderTest.getPersonsByLastName("Lenon"));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testGetPersonsByAddress() throws Exception {
		try {
			List<Person> resultPersons = personServiceUnderTest.getPersonsByAddress("1509 rue Dax");

			String expectedAddress = "1509 rue Dax";
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
			List<Person> resultPersons = personServiceUnderTest.getPersonsByCity("Dax");

			String expectedCity = personTest1.getCity();
			int countPersonByCity = 0;
			for (Person personFoundByCity : resultPersons) {
				countPersonByCity++;
				assertEquals(expectedCity, personFoundByCity.getCity());
			}
			assertEquals(2, countPersonByCity);
			assertFalse(resultPersons.isEmpty());
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testGetPersonsByCity_WithCityNotFound() throws Exception {
		try {
			List<Person> resultPersons = personServiceUnderTest.getPersonsByCity("City no existing");

			assertTrue(resultPersons.isEmpty());
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class, () -> personServiceUnderTest.getPersonsByCity("City no existing"));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testGetAllPersons() throws Exception {
		try {
			List<Person> resultAllPersons = personServiceUnderTest.getAllPersons();

			assertAll("assertion of all persons retrieved", () -> assertNotNull(resultAllPersons),
					() -> assertTrue(resultAllPersons.contains(personTest1)),
					() -> assertTrue(resultAllPersons.contains(personTest2)));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testGetAllPersons_NotFound() throws Exception {
		persons.clear();
		try {
			personServiceUnderTest.getAllPersons();
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class, () -> personServiceUnderTest.getAllPersons());
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}
}
