package com.safetynet.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.safetynet.api.service.alertssafetynetservice.ChildAlertService;
import com.safetynet.api.service.alertssafetynetservice.CommunityEmailService;
import com.safetynet.api.service.alertssafetynetservice.FloodService;
import com.safetynet.api.service.alertssafetynetservice.PersonInfoService;
import com.safetynet.api.service.alertssafetynetservice.PhoneAlertService;
import com.safetynet.api.service.alertssafetynetservice.ResidentsOfStationNumberService;
import com.safetynet.api.service.dataservice.FireStationService;

@WebMvcTest(controllers = AlertsController.class)
class AlertsControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ResidentsOfStationNumberService  residentsOfStationNumberService ;
	
	@MockBean
	private ChildAlertService  childAlertService ;
	
	@MockBean
	private PhoneAlertService  phoneAlertService;
	
	@MockBean
	private FireStationService  fireStationService;
	
	@MockBean
	private  FloodService    FloodService  ;
	
	@MockBean
	private PersonInfoService  personInfoService;
	
	@MockBean
	private CommunityEmailService  CommunityEmailService  ;
	
	@Test
	public void givenResidentsNearFireStation_WhenGetFireStationByStationNumber_ThenReturnStationNumberWithInfoAndCountDownOfAdultsAndChilds() throws Exception {
		try {
			MockHttpServletResponse result = mockMvc
					.perform(MockMvcRequestBuilders.get("/firestation").param("stationNumber", "3")).andReturn()
					.getResponse();
			
			assertEquals(HttpStatus.OK.value(), result.getStatus());
		}catch (AssertionError e) {
			fail(e.getMessage());
		}
	}
}
