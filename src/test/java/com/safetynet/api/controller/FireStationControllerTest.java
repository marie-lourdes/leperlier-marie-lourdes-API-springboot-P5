package com.safetynet.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

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
import com.safetynet.api.service.dataservice.FireStationService;

@AutoConfigureJsonTesters
@WebMvcTest(controllers = FireStationController.class)
class FireStationControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private FireStationService fireStationService;

	@Autowired
	private JacksonTester<FireStation> jsonFireStation;

	@Test
	public void givenFireStationObjectWithNewStationNumber_WhenAddFireStationByExistingAddress_ThenReturnSavedFireStation()
			throws Exception {
		FireStation fireStationCreatedWithNewStationNumber = new FireStation("3", "1509 Culver St");

		try {
			MockHttpServletResponse result = mockMvc
					.perform(MockMvcRequestBuilders.post("/firestation").param("address", "1509 Culver St")
							.contentType(MediaType.APPLICATION_JSON)
							.content(jsonFireStation.write(fireStationCreatedWithNewStationNumber).getJson()))
					.andReturn().getResponse();

			verify(fireStationService).addStationNumberOfFireStationWithExistingAddress(any(String.class),
					any(FireStation.class));
			assertEquals(HttpStatus.CREATED.value(), result.getStatus());
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void givenFireStationObjectWithNewStationNumberNoValid_WhenAddFireStationByExistingAddress_ThenReturn400()
			throws Exception {
		FireStation fireStationCreatedWithNewStationNumberNoValid = new FireStation("1509 Culver St");

		try {
			MockHttpServletResponse result = mockMvc
					.perform(
							MockMvcRequestBuilders.post("/firestation").param("address", "1509 Culver St")
									.contentType(MediaType.APPLICATION_JSON).content(jsonFireStation
											.write(fireStationCreatedWithNewStationNumberNoValid).getJson()))
					.andReturn().getResponse();

			// verify if constraint validation in model and in controller with @valid stop
			// the instruction post before call FireStationService
			verify(fireStationService, Mockito.times(0))
					.addStationNumberOfFireStationWithExistingAddress(any(String.class), any(FireStation.class));
			assertEquals(HttpStatus.BAD_REQUEST.value(), result.getStatus());
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void givenFireStationObjectWithNewStationNumber_WhenAddFireStationByNoExistingAddress_ThenReturn404()
			throws Exception {
		FireStation NoExistingAddressOfFireStationCreated = new FireStation("8", "1509 Culver St");

		try {
			fireStationService = new FireStationService();
			given(fireStationService.addStationNumberOfFireStationWithExistingAddress("1509 Culver S",
					NoExistingAddressOfFireStationCreated)).willReturn(null);

			MockHttpServletResponse result = mockMvc
					.perform(MockMvcRequestBuilders.post("/firestation").param("address", "1509 Culver S")
							.contentType(MediaType.APPLICATION_JSON)
							.content(jsonFireStation.write(NoExistingAddressOfFireStationCreated).getJson()))
					.andReturn().getResponse();

			verify(fireStationService).addStationNumberOfFireStationWithExistingAddress(any(String.class),
					any(FireStation.class));
			assertEquals(HttpStatus.NOT_FOUND.value(), result.getStatus());
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class,
					() -> fireStationService.addStationNumberOfFireStationWithExistingAddress("1509 Culver S",
							NoExistingAddressOfFireStationCreated));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void givenFireStationObjectWithNewAddress_WhenAddFireStationByExistingStationNumber_ThenReturnSavedFireStation()
			throws Exception {
		FireStation fireStationCreatedWithNewAddress = new FireStation("9", "112 address modified");

		try {
			MockHttpServletResponse result = mockMvc
					.perform(MockMvcRequestBuilders.post("/firestation/9").contentType(MediaType.APPLICATION_JSON)
							.content(jsonFireStation.write(fireStationCreatedWithNewAddress).getJson()))
					.andReturn().getResponse();

			verify(fireStationService).addAddressOfFireStationWithExistingStationNumber(any(String.class),
					any(FireStation.class));
			assertEquals(HttpStatus.CREATED.value(), result.getStatus());
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void givenFireStationObjectWithNewAddressNoValid_WhenAddFireStationByExistingStationNumber_ThenReturn400()
			throws Exception {
		FireStation fireStationCreatedWithNewAddressNoValid = new FireStation("9", "address modified");

		try {
			MockHttpServletResponse result = mockMvc
					.perform(MockMvcRequestBuilders.post("/firestation/9").contentType(MediaType.APPLICATION_JSON)
							.content(jsonFireStation.write(fireStationCreatedWithNewAddressNoValid).getJson()))
					.andReturn().getResponse();

			// verify if constraint validation in model and in controller with @valid stop
			// the instruction post before call FireStationService
			verify(fireStationService, Mockito.times(0))
					.addAddressOfFireStationWithExistingStationNumber(any(String.class), any(FireStation.class));
			assertEquals(HttpStatus.BAD_REQUEST.value(), result.getStatus());
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void givenFireStationObjectWithNewAddress_WhenAddFireStationByNoExistingStationNumber_ThenReturn404()
			throws Exception {
		FireStation NoExistingStationNumberOfFireStationCreated = new FireStation("9", "112 address modified");

		try {
			fireStationService = new FireStationService();
			given(fireStationService.addAddressOfFireStationWithExistingStationNumber("9",
					NoExistingStationNumberOfFireStationCreated)).willThrow(NullPointerException.class);

			MockHttpServletResponse result = mockMvc
					.perform(MockMvcRequestBuilders.post("/firestation/9").contentType(MediaType.APPLICATION_JSON)
							.content(jsonFireStation.write(NoExistingStationNumberOfFireStationCreated).getJson()))
					.andReturn().getResponse();

			verify(fireStationService).addAddressOfFireStationWithExistingStationNumber(any(String.class),
					any(FireStation.class));
			assertEquals(HttpStatus.NOT_FOUND.value(), result.getStatus());
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class,
					() -> fireStationService.addAddressOfFireStationWithExistingStationNumber("9",
							NoExistingStationNumberOfFireStationCreated));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void givenExistingFireStationObject_WhenUpdateStationNumberOfFireStationByAddress_ThenReturnUpdatedFireStation()
			throws Exception {
		FireStation existingFireStationUpdated = new FireStation("5", "748 Townings Dr");

		try {
			MockHttpServletResponse result = mockMvc
					.perform(MockMvcRequestBuilders.put("/firestation").param("address", "748 Townings Dr")
							.contentType(MediaType.APPLICATION_JSON)
							.content(jsonFireStation.write(existingFireStationUpdated).getJson()))
					.andReturn().getResponse();

			verify(fireStationService).updateFireStationByAddress(any(String.class), any(FireStation.class));
			assertEquals(HttpStatus.OK.value(), result.getStatus());
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void givenExistingFireStationObjectWithStationNumberNoValid_WhenUpdateStationNumberOfFireStationByAddress_ThenReturn400()
			throws Exception {
		FireStation existingFireStationUpdatedStationNumberNoValid = new FireStation("748 Townings Dr");

		try {
			MockHttpServletResponse result = mockMvc
					.perform(
							MockMvcRequestBuilders.put("/firestation").param("address", "748 Townings Dr")
									.contentType(MediaType.APPLICATION_JSON).content(jsonFireStation
											.write(existingFireStationUpdatedStationNumberNoValid).getJson()))
					.andReturn().getResponse();

			verify(fireStationService, Mockito.times(0)).updateFireStationByAddress(any(String.class),
					any(FireStation.class));
			assertEquals(HttpStatus.BAD_REQUEST.value(), result.getStatus());
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void givenExistingFireStationObject_WhenUpdateStationNumberOfFireStationByNoExistingAddress_ThenReturn404()
			throws Exception {
		FireStation NoExistingfireStationUpdated = new FireStation("5", "748 Townings Dr");

		try {
			fireStationService = new FireStationService();
			given(fireStationService.updateFireStationByAddress("748 Townings D", NoExistingfireStationUpdated))
					.willThrow(NullPointerException.class);

			MockHttpServletResponse result = mockMvc
					.perform(MockMvcRequestBuilders.put("/firestation").param("address", "748 Townings D")
							.contentType(MediaType.APPLICATION_JSON)
							.content(jsonFireStation.write(NoExistingfireStationUpdated).getJson()))
					.andReturn().getResponse();

			verify(fireStationService).updateFireStationByAddress(any(String.class), any(FireStation.class));
			assertEquals(HttpStatus.NOT_FOUND.value(), result.getStatus());
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class, () -> fireStationService
					.updateFireStationByAddress("748 Townings D", NoExistingfireStationUpdated));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void givenExistingFireStationObject_WhenRemoveExistingFireStationByStationNumber_ThenRemoveFireStation()
			throws Exception {
		try {
			given(fireStationService.deleteFireStationByStationNumber("3")).willReturn(true);

			MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders.delete("/firestation/3"))
					.andReturn().getResponse();

			verify(fireStationService).deleteFireStationByStationNumber(any(String.class));
			assertEquals(HttpStatus.NO_CONTENT.value(), result.getStatus());
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void givenNoExistingFireStationObject_WhenRemoveNoExistingFireStationByStationNumber_ThenReturn404()
			throws Exception {
		try {
			MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders.delete("/firestation/8"))
					.andReturn().getResponse();

			verify(fireStationService).deleteFireStationByStationNumber(any(String.class));
			assertEquals(HttpStatus.NOT_FOUND.value(), result.getStatus());
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class, () -> fireStationService.deleteFireStationByStationNumber("8"));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void givenExistingFireStationObject_WhenRemoveExistingFireStationByAddress_ThenRemoveFireStation()
			throws Exception {
		try {
			given(fireStationService.deleteFireStationByAddress("748 Townings Dr")).willReturn(true);

			MockHttpServletResponse result = mockMvc
					.perform(MockMvcRequestBuilders.delete("/firestation").param("address", "748 Townings Dr"))
					.andReturn().getResponse();

			verify(fireStationService).deleteFireStationByAddress(any(String.class));
			assertEquals(HttpStatus.NO_CONTENT.value(), result.getStatus());
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void givenNoExistingFireStationObject_WhenRemoveNoExistingFireStationByAddress_ThenReturn404()
			throws Exception {
		try {
			MockHttpServletResponse result = mockMvc
					.perform(MockMvcRequestBuilders.delete("/firestation").param("address", "no existing address"))
					.andReturn().getResponse();

			verify(fireStationService).deleteFireStationByAddress(any(String.class));
			assertEquals(HttpStatus.NOT_FOUND.value(), result.getStatus());
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class,
					() -> fireStationService.deleteFireStationByAddress("no existing address"));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

}
