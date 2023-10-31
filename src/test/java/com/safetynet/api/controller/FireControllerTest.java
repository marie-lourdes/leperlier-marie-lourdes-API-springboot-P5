package com.safetynet.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
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

	private static FireStation fireStationTest;
	private static List<FireStation> listFireStationsTest;

	@BeforeAll
	public static void setUp() {
		fireStationTest = new FireStation("9", "9", "112 address ");
		listFireStationsTest = new ArrayList<FireStation>();
		listFireStationsTest.add(fireStationTest);
	}

	@Test
	public void givenFireStationObjectWithNewAddress_WhenReplaceAllExistingFireStationFoundByStationNumber_ThenReturnSavedFireStation()
			throws Exception {
		try {
			FireStation fireStationCreatedWithNewAddress = new FireStation("9", "112 address modified");
			
			MockHttpServletResponse result = mockMvc
					.perform(MockMvcRequestBuilders.post("/firestation/9").contentType(MediaType.APPLICATION_JSON)
							.content(jsonFireStation.write(fireStationCreatedWithNewAddress).getJson()))
					.andReturn().getResponse();

			verify(fireStationService).addAddressOfExistingFireStation(any(FireStation.class), any(String.class));
			assertEquals(HttpStatus.CREATED.value(), result.getStatus());
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void givenFireStationObjectWithNewAddressNoValid_WhenReplaceAllExistingFireStationFoundByStationNumber_ThenReturn400()
			throws Exception {
		try {
			FireStation fireStationCreatedWithNewAddressNoValid = new FireStation("9", "address modified");
			
			MockHttpServletResponse result = mockMvc
					.perform(MockMvcRequestBuilders.post("/firestation/9").contentType(MediaType.APPLICATION_JSON)
							.content(jsonFireStation.write(fireStationCreatedWithNewAddressNoValid).getJson()))
					.andReturn().getResponse();

			// verify if constraint validation in model and in controller with @valid stop
			// the instruction post before call FireStationService
			verify(fireStationService, Mockito.times(0)).addAddressOfExistingFireStation(any(FireStation.class),
					any(String.class));
			assertEquals(HttpStatus.BAD_REQUEST.value(), result.getStatus());

		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void givenFireStationObjectWithNewAddress_WhenReplaceNoExistingFireStationAndNoExistingStationNumber_ThenReturn404()
			throws Exception {
		try {
			FireStation NoExistingStationNumberOfFireStationCreated = new FireStation("9", "112 address modified");
			fireStationService = new FireStationService();
			given(fireStationService.addAddressOfExistingFireStation(NoExistingStationNumberOfFireStationCreated, "9"))
					.willThrow(NullPointerException.class);

			MockHttpServletResponse result = mockMvc
					.perform(MockMvcRequestBuilders.post("/firestation/9").contentType(MediaType.APPLICATION_JSON)
							.content(jsonFireStation.write(new FireStation("9", "112 address modified")).getJson()))
					.andReturn().getResponse();

			verify(fireStationService).addAddressOfExistingFireStation(any(FireStation.class), any(String.class));
			assertEquals(HttpStatus.NOT_FOUND.value(), result.getStatus());
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class, () -> fireStationService
					.addStationNumberOfExistingFireStation(new FireStation("9", "112 address modified"), "9"));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void givenFireStationObjectWithNewStationNumber_WhenReplaceAllExistingFireStationFoundByAddress_ThenReturnSavedFireStation()
			throws Exception {
		FireStation existingFireStationUpdated = new FireStation("3", "3","1509 Culver St");
		
		try {
			MockHttpServletResponse result = mockMvc
					.perform(MockMvcRequestBuilders.post("/firestation").param("address", "1509 Culver St")
							.contentType(MediaType.APPLICATION_JSON)
							.content(jsonFireStation.write(existingFireStationUpdated).getJson()))
					.andReturn().getResponse();

			verify(fireStationService).addStationNumberOfExistingFireStation(any(FireStation.class), any(String.class));
			assertEquals(HttpStatus.CREATED.value(), result.getStatus());
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void givenFireStationObjectWithNewStationNumberNoValid_WhenReplaceAllExistingFireStationFoundByAddress_ThenReturn400()
			throws Exception {
		try {
			FireStation fireStationCreatedWithNewStationNumberNoValid = new FireStation("1509 Culver St");
			
			MockHttpServletResponse result = mockMvc
					.perform(MockMvcRequestBuilders.post("/firestation").param("address", "1509 Culver St")
							.contentType(MediaType.APPLICATION_JSON)
							.content(jsonFireStation.write( fireStationCreatedWithNewStationNumberNoValid ).getJson()))
					.andReturn().getResponse();

			// verify if constraint validation in model and in controller with @valid stop
			// the instruction post before call FireStationService
			verify(fireStationService, Mockito.times(0)).addStationNumberOfExistingFireStation(any(FireStation.class),
					any(String.class));
			assertEquals(HttpStatus.BAD_REQUEST.value(), result.getStatus());
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void givenFireStationObjectWithNewStationNumber_WhenReplaceNoExistingFireStationAndNoExistingAddress_ThenReturn404()
			throws Exception {
		try {
			FireStation NoExistingAddressOfFireStationCreated = new FireStation("8", "1509 Culver St");
			fireStationService = new FireStationService();
			given(fireStationService.addStationNumberOfExistingFireStation(NoExistingAddressOfFireStationCreated,
					"1509 Culver S")).willThrow(NullPointerException.class);

			MockHttpServletResponse result = mockMvc
					.perform(MockMvcRequestBuilders.post("/firestation").param("address", "1509 Culver S")
							.contentType(MediaType.APPLICATION_JSON)
							.content(jsonFireStation.write(NoExistingAddressOfFireStationCreated).getJson()))
					.andReturn().getResponse();

			verify(fireStationService).addStationNumberOfExistingFireStation(any(FireStation.class), any(String.class));
			assertEquals(HttpStatus.NOT_FOUND.value(), result.getStatus());
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class, () -> fireStationService
					.addStationNumberOfExistingFireStation(new FireStation("8", "1509 Culver St"), "1509 Culver S"));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void givenExistingFireStationObject_WhenUpdateStationNumberOfFireStation_ThenReturnUpdatedFireStation() throws Exception {
		try {
			FireStation existingFireStationUpdated = new FireStation ("10","112 Steppes Pl");
			
			MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders.put("/firestation").param("id", "3")
					.contentType(MediaType.APPLICATION_JSON).content(jsonFireStation.write(existingFireStationUpdated ).getJson()))
					.andReturn().getResponse();
			
			verify(fireStationService).updateFireStation(any(String.class),any(FireStation.class));
			assertEquals(HttpStatus.OK.value(), result.getStatus());
		} catch (AssertionError e) {
			fail(e.getMessage());
		}		
	}
}
