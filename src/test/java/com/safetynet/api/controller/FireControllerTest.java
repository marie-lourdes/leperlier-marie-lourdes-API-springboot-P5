package com.safetynet.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.Person;
import com.safetynet.api.service.dataservice.FireStationService;
@AutoConfigureJsonTesters
@WebMvcTest(controllers = FireStationController.class)
class FireControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private FireStationService fireStationService;

	@Autowired
	private JacksonTester<FireStation> jsonFireStation;
	
	@Test
	public void givenFireStationObjectWithStationNumber_WhenCreateFireStation_ThenReturnSavedFireStation() throws Exception {
		MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders.post("/firestation")
				.param("address", "1509 Culver St")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonFireStation.write(new FireStation("3","112 address modified")).getJson()))
				.andReturn().getResponse();

		assertEquals(HttpStatus.CREATED.value(), result.getStatus());
	}
	@Test
	public void givenFireStationObjectWithStationNumberNoValid_WhenCreateFireStation_ThenReturn400() throws Exception {
		MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders.post("/firestation")
				.param("address", "1509 Culver St")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonFireStation.write(new FireStation("3","address modified")).getJson()))
				.andReturn().getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), result.getStatus());
	}
/*	@Test
	void test() {
		fail("Not yet implemented");
	}*/

}
