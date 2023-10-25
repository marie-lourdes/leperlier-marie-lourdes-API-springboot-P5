package com.safetynet.api.service.dataservice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class PersonServiceTest {
	@Autowired
	private PersonService personService;
	@Test
	void testGetOnePerson() {

        String expected = "John";

        String result =  personService.getOnePersonById("John boyd").getFirstName();
        
		assertEquals(expected,  result);
	}

}
