package com.safetynet.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.safetynet.api.model.Person;
import com.safetynet.api.service.dataservice.PersonService;

/*@ContextConfiguration(locations = "file:src/main/resources/META-INF/application-context.xml")
@Import(JsonDataLoader.class)*/
@AutoConfigureJsonTesters
@WebMvcTest(controllers=PersonController.class)


class PersonControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private  PersonService personService;
	
	 @Autowired
	 private JacksonTester<Person> jsonPerson;
	
	/*private  List<Person> persons = new ArrayList<>();
	
	private Person person;*/
	
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
		  // given
	        given(personService.getOnePersonById("John Boyd")).willReturn(new Person ("John Boyd","John","Boyd","1509 Culver St","Culver","97451","841-874-6512","jaboyd@email.com"));
	        
		/*  Person person = new Person();
		  when(personService.getOnePersonById("John Boyd")).thenReturn( person);*/
	
		 /* personService.getOnePersonById("Tenley Boyd");*/
		
	        MockHttpServletResponse result=  mockMvc.perform(MockMvcRequestBuilders.get("/person")
					  .param("id","John Boyd")).andReturn().getResponse();
	        assertEquals( HttpStatus.OK.value(),result.getStatus());
	        assertEquals(result.getContentAsString(),
	        		 jsonPerson.write(new Person ("John Boyd","John","Boyd","1509 Culver St","Culver","97451","841-874-6512","jaboyd@email.com")).getJson());
			
	  }
	
	  
	  @Test 
	  public void givenIncorrectId_whenRequestOnePerson_ThenRetrieve404() throws Exception {
		 
	        given(personService.getOnePersonById("Tenley Boy")).willReturn(null);
		

	        MockHttpServletResponse result=  mockMvc.perform(MockMvcRequestBuilders.get("/person")
				  .param("id"," Tenley Boy")).andReturn().getResponse();
	        assertEquals( HttpStatus.NOT_FOUND.value(),result.getStatus());
	        
		// assertThrows(NullPointerException.class, () ->   personService.getOnePersonById("Tenley Boy"));
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

