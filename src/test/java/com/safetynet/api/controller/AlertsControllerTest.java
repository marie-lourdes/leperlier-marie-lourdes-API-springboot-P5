package com.safetynet.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.BDDMockito.given;

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
import com.safetynet.api.service.alertssafetynetservice.FireService;
import com.safetynet.api.service.alertssafetynetservice.FloodService;
import com.safetynet.api.service.alertssafetynetservice.PersonInfoService;
import com.safetynet.api.service.alertssafetynetservice.PhoneAlertService;
import com.safetynet.api.service.alertssafetynetservice.ResidentsOfStationNumberService;

@WebMvcTest(controllers = AlertsController.class)
class AlertsControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ResidentsOfStationNumberService residentsOfStationNumberService;

	@MockBean
	private ChildAlertService childAlertService;

	@MockBean
	private PhoneAlertService phoneAlertService;

	@MockBean
	private FireService fireService;

	@MockBean
	private FloodService floodService;

	@MockBean
	private PersonInfoService personInfoService;

	@MockBean
	private CommunityEmailService communityEmailService;

	// ---------Test resident of station number and countDown of adults and childs URL--------
	@Test
	public void givenResidentsNearFireStation_WhenGetFireStationByStationNumber_ThenReturnStationNumberWithInfoAndCountDownOfAdultsAndChilds()
			throws Exception {
		try {
			MockHttpServletResponse result = mockMvc
					.perform(MockMvcRequestBuilders.get("/firestation").param("stationNumber", "3")).andReturn()
					.getResponse();

			assertEquals(HttpStatus.OK.value(), result.getStatus());
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void givenNoExistingResidentsNearNoExistingFireStation_WhenGetNoExistingFireStationByStationNumber_ThenReturn404()
			throws Exception {
		try {
			given(residentsOfStationNumberService.getListOfResidentsOfStationNumber("0"))
					.willThrow(NullPointerException.class);

			MockHttpServletResponse result = mockMvc
					.perform(MockMvcRequestBuilders.get("/firestation").param("stationNumber", "0")).andReturn()
					.getResponse();

			assertEquals(HttpStatus.NOT_FOUND.value(), result.getStatus());
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class,
					() -> residentsOfStationNumberService.getListOfResidentsOfStationNumber("0"));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	// ---------Test childAlert URL--------
	@Test
	public void givenChildsAndMembersOfHouseHoldAdults_WhenGetChildsByAddress_ThenReturnChildsAndMembersOfHouseHoldAdultsInfo()
			throws Exception {
		try {
			MockHttpServletResponse result = mockMvc
					.perform(MockMvcRequestBuilders.get("/childAlert").param("address", "1509 Culver St")).andReturn()
					.getResponse();

			assertEquals(HttpStatus.OK.value(), result.getStatus());
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void givenNoExistingChildsAndOnlyAdults_WhenGetChildsByAddress_ThenReturn404() throws Exception {
		try {
			given(childAlertService.getChildsAndMembersOfHouseHold("951 LoneTree Rd"))
					.willThrow(NullPointerException.class);

			MockHttpServletResponse result = mockMvc
					.perform(MockMvcRequestBuilders.get("/childAlert").param("address", "951 LoneTree Rd")).andReturn()
					.getResponse();

			assertEquals(HttpStatus.NOT_FOUND.value(), result.getStatus());
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class,
					() -> childAlertService.getChildsAndMembersOfHouseHold("951 LoneTree Rd"));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void givenChildsAndMembersOfHouseHoldAdults_WhenGetChildsByNoExistingAddress_ThenReturn404()
			throws Exception {
		try {
			given(childAlertService.getChildsAndMembersOfHouseHold("45 No existing address"))
					.willThrow(NullPointerException.class);

			MockHttpServletResponse result = mockMvc
					.perform(MockMvcRequestBuilders.get("/childAlert").param("address", "45 No existing address"))
					.andReturn().getResponse();

			assertEquals(HttpStatus.NOT_FOUND.value(), result.getStatus());
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class,
					() -> childAlertService.getChildsAndMembersOfHouseHold("45 No existing address"));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	// ---------Test phoneAlert URL--------
	@Test
	public void givenPhonesOfResidentsOfStationNumber_WhenGetPhonesResidentByStationNumber_ThenReturnPhonesOfResidents()
			throws Exception {
		try {
			MockHttpServletResponse result = mockMvc
					.perform(MockMvcRequestBuilders.get("/phoneAlert").param("stationNumber", "3")).andReturn()
					.getResponse();

			assertEquals(HttpStatus.OK.value(), result.getStatus());
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void givenPhonesOfResidentsOfStationNumber_WhenGetPhonesResidentByStationNumber_ThenReturn404()
			throws Exception {
		try {
			given(phoneAlertService
					.getListOfPhonesOfResidentsOfStationNumber("5"))
					.willThrow(NullPointerException.class);

			MockHttpServletResponse result = mockMvc
					.perform(MockMvcRequestBuilders.get("/phoneAlert").param("stationNumber", "5"))
					.andReturn().getResponse();

			assertEquals(HttpStatus.NOT_FOUND.value(), result.getStatus());
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class,
					() -> phoneAlertService
					.getListOfPhonesOfResidentsOfStationNumber("5"));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	// ---------Test residents and firestations near fire URL--------
	@Test
	public void givenResidentsAndFireStationNearFireAddress_WhenGetResidentsAndFireStationByFireAddress_ThenReturnResidentsAndStationNumber()
			throws Exception {
		try {
			MockHttpServletResponse result = mockMvc
					.perform(MockMvcRequestBuilders.get("/fire").param("address", "1509 Culver St")).andReturn()
					.getResponse();

			assertEquals(HttpStatus.OK.value(), result.getStatus());
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void givenNoExistingResidentsAndFireStationNearFireAddress_WhenGetResidentsAndFireStationByFireAddress_ThenReturn404()
			throws Exception {
		try {		
			given(fireService
					.getListOfResidentsAndFireStationNearFire("45 Address no registered in data residents and firestations"))
					.willThrow(NullPointerException.class);
			
			MockHttpServletResponse result = mockMvc
					.perform(MockMvcRequestBuilders.get("/fire").param("address", "45 Address no registered in data residents and firestations")).andReturn()
					.getResponse();

			assertEquals(HttpStatus.NOT_FOUND.value(), result.getStatus());
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class,
					() ->fireService
					.getListOfResidentsAndFireStationNearFire("45 Address no registered in data residents and firestations"));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	// ---------Test households of station  number when flood URL--------
	@Test
	public void givenFloodAndHouseHoldsOfStationsNumber_WhenGetHouseHoldsBySeveralsStationsNumber_ThenReturnHouseHoldsOfListOfStationNumbers()
			throws Exception {
		try {
			MockHttpServletResponse result = mockMvc
					.perform(MockMvcRequestBuilders.get("/flood/stations").param("stations", "2").param("stations", "3")).andReturn()
					.getResponse();

			assertEquals(HttpStatus.OK.value(), result.getStatus());
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void givenFloodAndHouseHoldsOfStationsNumber_WhenGetHouseHoldsBySeveralsStationsNumberWithOneNoExistingStationNumber_ThenReturn404()
			throws Exception {
		try {
			given(floodService.getListOfHouseHoldByStationNumber("0"))
					.willThrow(NullPointerException.class);
			
			MockHttpServletResponse result = mockMvc
					.perform(MockMvcRequestBuilders.get("/flood/stations").param("stations", "2").param("stations", "0")).andReturn()
					.getResponse();

			assertEquals(HttpStatus.NOT_FOUND.value(), result.getStatus());
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class,
					() ->floodService.getListOfHouseHoldByStationNumber("0"));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	// ---------Test personInfo URL--------
	@Test
	public void givenInfoOfPerson_WhenGetInfoOfPersonByFirstNameAndLastName_ThenReturnInfoOfPersonAndPersonsWithSameLastName()
			throws Exception {
		try {
			MockHttpServletResponse result = mockMvc
					.perform(MockMvcRequestBuilders.get("/personInfo").param("firstName", "John").param("lastName", "Boyd")).andReturn()
					.getResponse();

			assertEquals(HttpStatus.OK.value(), result.getStatus());
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void givenNoExistingInfoOfPerson_WhenGetInfoOfNoExistingPersonByFirstNameAndLastName_ThenReturn404()
			throws Exception {
		try {
			given(personInfoService
					.getInfoAndMedicalRecordOfPersonByFullName("John", "Lenon"))
			.willThrow(NullPointerException.class);
			
			MockHttpServletResponse result = mockMvc
					.perform(MockMvcRequestBuilders.get("/personInfo").param("firstName", "John").param("lastName", "Lenon")).andReturn()
					.getResponse();

			assertEquals(HttpStatus.NOT_FOUND.value(), result.getStatus());
		}  catch (NullPointerException e) {
			assertThrows(NullPointerException.class,
					() ->personInfoService
					.getInfoAndMedicalRecordOfPersonByFullName("John", "Lenon"));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}
}
