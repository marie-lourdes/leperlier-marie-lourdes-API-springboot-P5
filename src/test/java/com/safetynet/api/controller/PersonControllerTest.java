package com.safetynet.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.safetynet.api.model.Person;
import com.safetynet.api.service.dataservice.PersonService;

@AutoConfigureJsonTesters
@WebMvcTest(controllers = PersonController.class)
class PersonControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PersonService personService;

	@Autowired
	private JacksonTester<Person> jsonPerson;
	
	@Test
	public void givenPersonObject_WhenCreatePerson_ThenReturnSavedPerson() throws Exception {
		MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders.post("/person")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonPerson.write(new Person("firstname created", "lastname created", "112 address created",
						"city created", "97451", "841-874-2512", "personcreated@email.com")).getJson()))
				.andReturn().getResponse();

		assertEquals(HttpStatus.CREATED.value(), result.getStatus());
	}

	@Test
	public void givenPersonObjectWithAddressNoValid_WhenCreatePerson_ThenReturn400() throws Exception {
		MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders.post("/person")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonPerson.write(new Person("firstname created", "lastname created", "address created",
						"city created", "97451", "841-874-2512", "personcreated@email.com")).getJson()))
				.andReturn().getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), result.getStatus());
	}

	@Test
	public void givenExistingPersonObject_WhenUpdateAddressPerson_ThenReturnUpdatedPerson() throws Exception {
		Person existingPerson = new Person("John Boyd", "John", "Boyd", "1509 Culver St", "Culver", "97451",
				"841-874-6512", "jaboyd@email.com");
		Person existingPersonUpdated = new Person("John Boyd", "John", "Boyd", "14 address modified", "Culver", "97451",
				"841-874-6512", "jaboyd@email.com");
		given(personService.getOnePersonById("John Boyd")).willReturn(existingPerson);

		MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders.put("/person").param("id", "John Boyd")
				.contentType(MediaType.APPLICATION_JSON).content(jsonPerson.write(existingPersonUpdated).getJson()))
				.andReturn().getResponse();

		assertEquals(HttpStatus.OK.value(), result.getStatus());

	}


	@Test
	public void givenExistingPersonObject_WhenUpdateAddressNoValidPerson_ThenReturn400() throws Exception {
		Person existingPerson = new Person("John Boyd", "John", "Boyd", "1509 Culver St", "Culver", "97451",
				"841-874-6512", "jaboyd@email.com");
		Person existingPersonUpdatedAddressNoValid = new Person("John Boyd", "John", "Boyd", "quatorze  address modified", "Culver", "97451",
				"841-874-6512", "jaboyd@email.com");
		given(personService.getOnePersonById("John Boyd")).willReturn(existingPerson);

		MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders.put("/person").param("id", "John Boyd")
				.contentType(MediaType.APPLICATION_JSON).content(jsonPerson.write( existingPersonUpdatedAddressNoValid ).getJson()))
				.andReturn().getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), result.getStatus());

	}
	
	@Test
	public void givenExistingPersonObject_WhenRemoveExistingPerson_ThenRemovePerson() throws Exception {
	
		given(personService.deleteOnePersonById("John Boyd")).willReturn(true);

		MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders.delete("/person").param("id", "John Boyd"))
				.andReturn().getResponse();

		assertEquals(HttpStatus.NO_CONTENT.value(), result.getStatus());
	
	}
	
	@Test
	public void givenNoExistingPersonObject_WhenRemoveNoExistingPerson_ThenReturn404() throws Exception {
	
		given(personService.deleteOnePersonById("John Lenon")).willReturn(false);

		MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders.delete("/person").param("id", "John Lenon"))
				.andReturn().getResponse();

		assertEquals(HttpStatus.NOT_FOUND.value(), result.getStatus());
	
	} 	 
}

