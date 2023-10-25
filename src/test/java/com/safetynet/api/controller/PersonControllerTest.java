package com.safetynet.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.safetynet.api.model.Person;
import com.safetynet.api.service.dataservice.PersonService;

@WebMvcTest(controllers=  PersonController.class)
class PersonControllerTest {

	@Autowired
	private MockMvc mockMvc;
	

	@MockBean
	private PersonService personService;
	


	  @Test
	  public void givenCorrectUri_WhenRequestAllPersons_ThenRetrieveCorrectResponse() throws Exception {
	  mockMvc.perform(get("/person/")) .andExpect(status().isOk());
	 
	  }
	  
	 @Test
	  public void givenIncorrectUri_whenRequestAllPersons_ThenRetrieve404() throws Exception {
		  mockMvc.perform(get("/persons/")) .andExpect(status().isNotFound());
		 
		  }
	/*  
	  @Test 
	  public void givenCorrectId_WhenRequestOnePerson_ThenRetrieveCorrectResponse() throws Exception {
		  mockMvc.perform(get("/person/John Boyd")) .andExpect(status().isOk());
		 
		  }
	  */
	  @Test 
	  public void givenIncorrectId_whenRequestOnePerson_ThenRetrieve404() throws Exception {
		  String id = "John lenon";
		  mockMvc.perform(get("/person/John lenon")) .andExpect(status().isNotFound());
		 
		  }
	 
 /* @Test
  public void givenPersonObject_whenCreatePerson_thenReturnSavedPerson()  throws Exception {
		  Person person = new Person();
		
		  person.setFirstName("John");
		  person.setLastName("Boyd");	
		  person.setAddress("1509 Culver St");
		  person.setAddress("9785");
		  person.setCity("culver");
		  person.setPhone("456-544-7895");
		  person.setEmail("jhvjf@frjhg.com");
		//  personService.addPerson(person)	;  
		  /*
		  // when -  action or the behaviour that we are going test
	        ResultActions response = mockMvc.perform(post("/person/",  person ));
	        response.andExpect(status().isCreated())
            .andDo(print())
            .andExpect((ResultMatcher) is( person.getFirstName()))
            .andExpect( (ResultMatcher) is( person.getLastName()))
            .andExpect( (ResultMatcher) is( person.getAddress()))
            .andExpect( (ResultMatcher) is( person.getCity()))
            .andExpect((ResultMatcher) is( person.getZip()))
            .andExpect( (ResultMatcher) is( person.getPhone()))
            .andExpect( (ResultMatcher) is( person.getEmail()));
  }*/	  
  
	/* final MvcResult result = mockMvc.perform(
	  MockMvcRequestBuilders.post("/person/",person)).andExpect(status().isCreated()).andReturn();
	  
	 assertThat(result.getResponse().getContentAsString()) .contains("John");
	  }*/
	 
}

