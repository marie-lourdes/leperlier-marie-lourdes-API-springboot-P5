package com.safetynet.api.controller;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.safetynet.api.config.JsonDataLoader;
import com.safetynet.api.model.Person;
import com.safetynet.api.service.dataservice.PersonService;

@ContextConfiguration(locations = "file:src/main/resources/META-INF/application-context.xml")
@Import(JsonDataLoader.class)
@WebMvcTest(controllers=PersonController.class)


class PersonControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private  PersonService personService;
	
	private  List<Person> persons = new ArrayList<>();
	
	private Person person;
	
	/*  @Test
	  public void givenCorrectUri_WhenRequestAllPersons_ThenRetrieveCorrectResponse() throws Exception {
	  mockMvc.perform(get("/person/")) .andExpect(status().isOk());
	 
	  }
	  
	 @Test
	  public void givenIncorrectUri_whenRequestAllPersons_ThenRetrieve404() throws Exception {
		  mockMvc.perform(get("/persons/")) .andExpect(status().isNotFound());
		 
		  }*/
	 
	/*  @Test 
	  public void givenCorrectId_WhenRequestOnePerson_ThenRetrieveCorrectResponse() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/person/{id}","John Boyd")) .andExpect(status().isOk()).andExpect(jsonPath("$.lastName").value("Boyd"));
		 
		  }*/
	  
	  @Test 
	  public void givenCorrectId_WhenRequestOnePerson_ThenRetrieveCorrectResponse()  throws Exception {
		
		  Person person = new Person();
		  when(personService.getOnePersonById("Tenley Boyd")).thenReturn( person);
		  personService= new PersonService();
		  personService.getOnePersonById("Tenley Boyd");
		
			  mockMvc.perform(MockMvcRequestBuilders.get("/person")
					  .param("id","Tenley Boyd")) 
			          .andExpect(MockMvcResultMatchers.status().isOk());
			
	  }
	
	  
	  @Test 
	  public void givenIncorrectId_whenRequestOnePerson_ThenRetrieve404() throws Exception {
	
		//when(personService.getOnePersonById("Tenley Boy")).thenReturn( person);
		 //test no valid return 200!!!


		
		  personService.getOnePersonById("Tenley Boy");
			//personService= new PersonService();
		  mockMvc.perform(MockMvcRequestBuilders.get("/person")
				  .param("id"," Tenley Boy")) 
		          .andExpect(MockMvcResultMatchers.status().isNotFound());
		//  assertThrows(NullPointerException.class, () ->   personService.getOnePersonById("Tenley Boy"));
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

