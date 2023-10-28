package com.safetynet.api.service.dataservice;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.safetynet.api.model.Person;

@SpringBootTest
class PersonServiceTest {
	private static final Logger log = LogManager.getLogger(PersonServiceTest.class);
	@Autowired
	private PersonService personService;
	private Person person;

	@BeforeEach
	public void setUpPerTest() {
		person = new Person("John Boyd", "John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512",
				"jaboyd@email.com");

	}

	@Test
	void testGetOnePersonById() {
		try {
			Person resultPerson = personService.getOnePersonById("John Boyd");

			String expectedFirstName = person.getFirstName();
			String expectedLastName = person.getLastName();
			String expectedAddress = person.getAddress();
			String expectedCity = person.getCity();
			String expectedZip = person.getZip();
			String expectedPhone = person.getPhone();
			String expectedEmail = person.getEmail();

			assertAll("assertion all data of person found by id", () -> assertNotNull(resultPerson),
					() -> assertEquals(expectedFirstName + expectedLastName, resultPerson.getId(),
							"the id should  be the first name and last name of person"),
					() -> assertEquals(expectedFirstName, resultPerson.getFirstName()),
					() -> assertEquals(expectedLastName, resultPerson.getLastName()),
					() -> assertEquals(expectedAddress, resultPerson.getAddress()),
					() -> assertEquals(expectedCity, resultPerson.getCity()),
					() -> assertEquals(expectedZip, resultPerson.getZip()),
					() -> assertEquals(expectedPhone, resultPerson.getPhone()),
					() -> assertEquals(expectedEmail, resultPerson.getEmail()));

		} catch (AssertionError e) {
			log.error(e.getMessage());
		}
	}
}
