package com.safetynet.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

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
	        mockMvc.perform(get("/demo/all"))
	            .andExpect(status().isOk());

}
}
