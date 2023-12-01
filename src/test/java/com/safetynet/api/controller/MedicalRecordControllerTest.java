package com.safetynet.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

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

import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.service.dataservice.MedicalRecordService;

@AutoConfigureJsonTesters
@WebMvcTest(controllers = MedicalRecordController.class)
class MedicalRecordControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MedicalRecordService medicalRecordService;

	@Autowired
	private JacksonTester<MedicalRecord> jsonMedicalRecord;

	@Test
	void givenMedicalRecordObject_WhenCreateMedicalRecord_ThenReturnSavedMedical() throws Exception {
		List<String> medications = new ArrayList<String>();
		medications.add("aznol:350mg");
		medications.add("hydrapermazol:100mg");
		List<String> allergies = new ArrayList<String>();
		allergies.add("nillacilan");
		MedicalRecord medicalRecordCreated = new MedicalRecord("Millie", "Leperlier", "03/06/1996", medications,
				allergies);

		try {
			MockHttpServletResponse result = mockMvc
					.perform(MockMvcRequestBuilders.post("/medicalRecord").contentType(MediaType.APPLICATION_JSON)
							.content(jsonMedicalRecord.write(medicalRecordCreated).getJson()))
					.andReturn().getResponse();

			verify(medicalRecordService).addMedicalRecord(any(MedicalRecord.class));
			assertEquals(HttpStatus.CREATED.value(), result.getStatus());
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void givenMedicalRecordObjectWithBirthDateNoValid_WhenCreateMedicalRecord_ThenReturn400() throws Exception {
		List<String> medications = new ArrayList<String>();
		medications.add("hydrapermazol:100mg");
		List<String> allergies = new ArrayList<String>();
		MedicalRecord medicalRecordCreatedBirthDateNoValid = new MedicalRecord("Millie", "Leperlier", "03-06-1996",
				medications, allergies);

		try {
			MockHttpServletResponse result = mockMvc
					.perform(MockMvcRequestBuilders.post("/medicalRecord").contentType(MediaType.APPLICATION_JSON)
							.content(jsonMedicalRecord.write(medicalRecordCreatedBirthDateNoValid).getJson()))
					.andReturn().getResponse();

			// verify if constraint validation in model and in controller with @valid stop
			// the instruction post before call MedicalRecordService
			verify(medicalRecordService, Mockito.times(0)).addMedicalRecord(any(MedicalRecord.class));
			assertEquals(HttpStatus.BAD_REQUEST.value(), result.getStatus());
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void givenExistingMedicalRecordObject_WhenUpdateMedicationAndAllergiesOfMedicalRecord_ThenReturnUpdatedMedicalRecord()
			throws Exception {
		List<String> medications = new ArrayList<String>();
		medications.add("aznol:50mg");
		medications.add("hydrapermazol:50mg");
		List<String> allergies = new ArrayList<String>();
		allergies.add("shellfish");
		MedicalRecord medicalRecordUpdated = new MedicalRecord("John", " Boyd", "03/06/1984", medications, allergies);

		try {
			MockHttpServletResponse result = mockMvc
					.perform(MockMvcRequestBuilders.put("/medicalRecord").param("id", "John Boyd")
							.contentType(MediaType.APPLICATION_JSON)
							.content(jsonMedicalRecord.write(medicalRecordUpdated).getJson()))
					.andReturn().getResponse();

			verify(medicalRecordService).updateOneMedicalRecordById(any(String.class), any(MedicalRecord.class));
			assertEquals(HttpStatus.OK.value(), result.getStatus());
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void givenExistingMedicalRecordObjectWithDataNoValid_WhenUpdateMedicationAndAllergiesOfMedicalRecord_ThenReturn400()
			throws Exception {
		List<String> medications = new ArrayList<String>();
		medications.add("aznol:50mg");
		medications.add("hydrapermazol:50mg");
		List<String> allergies = new ArrayList<String>();
		allergies.add("shellfish");
		MedicalRecord medicalRecordUpdated = new MedicalRecord(medications, allergies);

		try {
			MockHttpServletResponse result = mockMvc
					.perform(MockMvcRequestBuilders.put("/medicalRecord").param("id", "John Boyd")
							.contentType(MediaType.APPLICATION_JSON)
							.content(jsonMedicalRecord.write(medicalRecordUpdated).getJson()))
					.andReturn().getResponse();

			verify(medicalRecordService, Mockito.times(0)).updateOneMedicalRecordById(any(String.class),
					any(MedicalRecord.class));
			assertEquals(HttpStatus.BAD_REQUEST.value(), result.getStatus());
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void givenExistingMedicalRecordObject_WhenUpdateNoExistingMedicalRecord_ThenReturn404() throws Exception {
		List<String> medications = new ArrayList<String>();
		medications.add("aznol:50mg");
		medications.add("hydrapermazol:50mg");
		List<String> allergies = new ArrayList<String>();
		allergies.add("shellfish");
		MedicalRecord NoExistingMedicalRecordUpdated = new MedicalRecord("John", "Lenon", "00/00/0000", medications,
				allergies);

		try {
			given(medicalRecordService.updateOneMedicalRecordById("John Lenon", NoExistingMedicalRecordUpdated))
					.willThrow(NullPointerException.class);

			MockHttpServletResponse result = mockMvc
					.perform(MockMvcRequestBuilders.put("/medicalRecord").param("id", "John Lenon")
							.contentType(MediaType.APPLICATION_JSON)
							.content(jsonMedicalRecord.write(NoExistingMedicalRecordUpdated).getJson()))
					.andReturn().getResponse();

			verify(medicalRecordService).updateOneMedicalRecordById(any(String.class), any(MedicalRecord.class));
			assertEquals(HttpStatus.NOT_FOUND.value(), result.getStatus());
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class, () -> medicalRecordService.updateOneMedicalRecordById("John Lenon",
					NoExistingMedicalRecordUpdated));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void givenExistingMedicalRecordObject_WhenRemoveNoExistingMedicalRecordObjec_ThenRemoveMedicalRecord()
			throws Exception {
		try {
			given(medicalRecordService.deleteOneMedicalRecordById("John Boyd")).willReturn(true);

			MockHttpServletResponse result = mockMvc
					.perform(MockMvcRequestBuilders.delete("/medicalRecord").param("id", "John Boyd")).andReturn()
					.getResponse();

			verify(medicalRecordService).deleteOneMedicalRecordById(any(String.class));
			assertEquals(HttpStatus.NO_CONTENT.value(), result.getStatus());
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void givenNoExistingMedicalRecordObject_WhenRemoveNoExistingMedicalRecord_ThenReturn404() throws Exception {
		try {
			MockHttpServletResponse result = mockMvc
					.perform(MockMvcRequestBuilders.delete("/medicalRecord").param("id", "John Lenon")).andReturn()
					.getResponse();

			verify(medicalRecordService).deleteOneMedicalRecordById(any(String.class));
			assertEquals(HttpStatus.NOT_FOUND.value(), result.getStatus());
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class,
					() -> medicalRecordService.deleteOneMedicalRecordById("John Lenon"));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}
}