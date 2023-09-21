package com.safetynet.api;

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

import com.safetynet.api.controller.PersonController;
import com.safetynet.api.service.dataservice.PersonService;
@WebMvcTest(controllers = PersonController.class)
class PersonControllerTest {

	 @Autowired
	    private MockMvc mockMvc;

	    @MockBean
	    private PersonService personService;

	    @Test
	    public void testGetPerson() throws Exception {
	        mockMvc.perform(get("/person"))
	            .andExpect(status().isOk());

	    }
	    
	    @Test
		public void testPostPerson() throws Exception {
		
			// WHEN
			final MvcResult result = mockMvc.perform(
					MockMvcRequestBuilders.post("/person")
							.param("firstName", "marie")
							.param("lastName", "leperlier")
							.param("address", "address2")
							.param("zip", "656453")
							.param("city", "ville2")
							.param("phone", "5456-54153")
							.param("email", "fjrhf@frbhhj.fr"))
					 		.andExpect(status().isOk())
					 		.andReturn();
			
			assertThat(result.getResponse().getContentAsString())
			.contains("marie");
	    }
	    
}
