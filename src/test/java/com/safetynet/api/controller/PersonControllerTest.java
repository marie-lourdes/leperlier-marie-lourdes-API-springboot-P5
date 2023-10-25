package com.safetynet.api.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import com.safetynet.api.model.Person;
import com.safetynet.api.service.dataservice.PersonService;

@WebMvcTest(controllers = PersonController.class)
class PersonControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PersonService personService;

	
	  @Test
	  public void givenCorrectUriwhenRequestAllPersonsThenRetrieveCorrectResponse() throws Exception {
	  mockMvc.perform(get("/person/")) .andExpect(status().isOk());
	 
	  }
	  
	  @Test
	  public void givenIncorrectUriwhenRequestAllPersonsThenRetrieve404() throws Exception {
		  mockMvc.perform(get("/person/")) .andExpect(status().isNotFound());
		 
		  }
	  
	  @Test 
	  public void givenCorrectIdwhenRequestOnePersonThenRetrieveCorrectResponse() throws Exception {
		  mockMvc.perform(get("/person/John Boyd ")) .andExpect(status().isOk());
		 
		  }
	  
	  @Test 
	  public void givenIncorrectIdwhenRequestOnePersonThenRetrieve404() throws Exception {
		  mockMvc.perform(get("/person/John lenon")) .andExpect(status().isNotFound());
		 
		  }
	 
  @Test
  public void givenPersonCreatedWhenPostThenTheResponseBodyRetrievedMatchWithPersonCreated() throws Exception {
		  Person employee = new Person("John", "boyd","1509 Culver St","ville","9785","456-544-7895","jhvjf@frjhg.com");
		  // when -  action or the behaviour that we are going test
	        ResultActions response = mockMvc.perform(post("/person/", employee ));
	        
	        response.andExpect(status().isCreated())
            .andDo(print())
            .andExpect((ResultMatcher) is(employee.getFirstName()))
            .andExpect( (ResultMatcher) is(employee.getLastName()))
            .andExpect( (ResultMatcher) is(employee.getAddress()))
            .andExpect( (ResultMatcher) is(employee.getCity()))
            .andExpect((ResultMatcher) is(employee.getZip()))
            .andExpect( (ResultMatcher) is(employee.getPhone()))
            .andExpect( (ResultMatcher) is(employee.getEmail()));
  }	  
	/* final MvcResult result = mockMvc.perform(
	  MockMvcRequestBuilders.post("/person/")
	  .param("firstName", "John")
	  .param("lastName", "Boyd")
	  .param("address", "114 dsdfs")
	  .param("zip",
	  "656453") 
	  .param("city", "ville") 
	  .param("phone", "545-541-2653")
	  .param("email", "fjrhf@frbhhj.fr"))
	  .andExpect(status().isOk()) .andReturn();
	  
	 assertThat(result.getResponse().getContentAsString()) .contains("marie");
	  }*/
	 
}

