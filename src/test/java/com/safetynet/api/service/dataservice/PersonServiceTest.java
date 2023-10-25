package com.safetynet.api.service.dataservice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.safetynet.api.model.Person;


@SpringBootTest
class PersonServiceTest {
	@Autowired
	private PersonService personService;
	@Test
	void testGetOnePersonById() {

      /*  String expected = "John";
        Person personExpectedById = new Person("John Boyd","John","Boyd","1509 Culver St","Culver","97451","841-874-6512","jaboyd@email.com" );

        Person result =  personService.getOnePersonById("John Boyd");
        
		assertEquals(personExpectedById,  result);*/
	   Person resultPerson =  personService.getOnePersonById("John Boyd");
	   
		String expectedFirstName = "John ";
		String expectedLastName = "Boyd";
        String resultId =resultPerson.getFirstName() +" "+ resultPerson.getLastName();
			assertEquals(expectedFirstName+expectedLastName,  resultId);
	}

}
