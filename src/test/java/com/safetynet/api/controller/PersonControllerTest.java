package com.safetynet.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
		Person personCreated = new Person("Millie", "Leperlier", "112 address", "city", "97451", "841-874-2512",
				"millie@email.com");

		try {
			MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders.post("/person")
					.contentType(MediaType.APPLICATION_JSON).content(jsonPerson.write(personCreated).getJson()))
					.andReturn().getResponse();

			verify(personService).addPerson(any(Person.class));
			assertEquals(HttpStatus.CREATED.value(), result.getStatus());
		} catch (AssertionError e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void givenPersonObjectWithAddressNoValid_WhenCreatePerson_ThenReturn400() throws Exception {
		Person personCreatedWithAddressNovalid = new Person("Millie", "Leperlier", "address", "city", "97451",
				"841-874-2512", "millie@email.com");

		try {
			MockHttpServletResponse result = mockMvc
					.perform(MockMvcRequestBuilders.post("/person").contentType(MediaType.APPLICATION_JSON)
							.content(jsonPerson.write(personCreatedWithAddressNovalid).getJson()))
					.andReturn().getResponse();
			// verify if constraint validation in model and in controller with @valid stop
			// the instruction post before call PersonService
			verify(personService, Mockito.times(0)).addPerson(any(Person.class));
			assertEquals(HttpStatus.BAD_REQUEST.value(), result.getStatus());
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void givenExistingPersonObject_WhenUpdateAddressOfPerson_ThenReturnUpdatedPerson() throws Exception {
		Person existingPersonUpdated = new Person("John", "Boyd", "14 address modified", "Culver", "97451",
				"841-874-6512", "jaboyd@email.com");
		try {
			MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders.put("/person")
					.param("id", "John Boyd").contentType(MediaType.APPLICATION_JSON)
					.content(jsonPerson.write(existingPersonUpdated).getJson())).andReturn().getResponse();

			verify(personService).updatePersonById(any(String.class), any(Person.class));
			assertEquals(HttpStatus.OK.value(), result.getStatus());
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void givenExistingPersonObjectWithAddressNoValid_WhenUpdateOfPerson_ThenReturn400() throws Exception {
		Person existingPersonUpdatedAddressNoValid = new Person( "John", "Boyd",
				"quatorze  address modified", "Culver", "97451", "841-874-6512", "jaboyd@email.com");

		try {
			MockHttpServletResponse result = mockMvc
					.perform(MockMvcRequestBuilders.put("/person").param("id", "John Boyd")
							.contentType(MediaType.APPLICATION_JSON)
							.content(jsonPerson.write(existingPersonUpdatedAddressNoValid).getJson()))
					.andReturn().getResponse();

			// verify if constraint validation in model and in controller with @valid stop
			// the instruction post before call PersonService
			verify(personService, Mockito.times(0)).updatePersonById(any(String.class), any(Person.class));
			assertEquals(HttpStatus.BAD_REQUEST.value(), result.getStatus());
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void givenExistingPersonObject_WhenUpdateNoExistingPerson_ThenReturn404() throws Exception {
		Person NoExistingPersonUpdated = new Person("John", "Boyd", "14 address modified", "Culver",
				"97451", "841-874-6512", "jaboyd@email.com");

		try {
			personService = new PersonService();
			given(personService.updatePersonById("John Lenon", NoExistingPersonUpdated))
					.willThrow(NullPointerException.class);

			MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders.put("/person")
					.param("id", "John Lenon").contentType(MediaType.APPLICATION_JSON)
					.content(jsonPerson.write(NoExistingPersonUpdated).getJson())).andReturn().getResponse();

			verify(personService).updatePersonById(any(String.class), any(Person.class));
			assertEquals(HttpStatus.NOT_FOUND.value(), result.getStatus());
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class,
					() -> personService.updatePersonById("John Lenon", NoExistingPersonUpdated));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void givenExistingPersonObject_WhenRemoveExistingPerson_ThenRemovePerson() throws Exception {
		try {
			given(personService.deleteOnePersonById("John Boyd")).willReturn(true);

			MockHttpServletResponse result = mockMvc
					.perform(MockMvcRequestBuilders.delete("/person").param("id", "John Boyd")).andReturn()
					.getResponse();

			verify(personService).deleteOnePersonById(any(String.class));
			assertEquals(HttpStatus.NO_CONTENT.value(), result.getStatus());
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void givenNoExistingPersonObject_WhenRemoveNoExistingPerson_ThenReturn404() throws Exception {
		try {
			MockHttpServletResponse result = mockMvc
					.perform(MockMvcRequestBuilders.delete("/person").param("id", "John Lenon")).andReturn()
					.getResponse();

			verify(personService).deleteOnePersonById(any(String.class));
			assertEquals(HttpStatus.NOT_FOUND.value(), result.getStatus());
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class,
					() -> personService.deleteOnePersonById("John Lenon")); 
		}catch (AssertionError e) {
			fail(e.getMessage());
		}
	}
}
