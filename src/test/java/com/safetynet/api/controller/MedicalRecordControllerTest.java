package com.safetynet.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
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
@WebMvcTest(controllers =MedicalRecordController.class)
class MedicalRecordControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MedicalRecordService medicalRecordService;

	@Autowired
	private JacksonTester<MedicalRecord> jsonMedicalRecord;
	
	@Test
	public void givenMedicalRecordObject_WhenCreateMedicalRecord_ThenReturnSavedMedical() throws Exception {
		List<String> medications =new ArrayList<String>();
		medications.add("aznol:350mg");
		medications.add("hydrapermazol:100mg");
		List<String> allergies =new ArrayList<String>();
		allergies.add("nillacilan");
		MedicalRecord medicalRecordCreated = new MedicalRecord("John","Boyd","03/06/1984",	medications,allergies);
			
		try {
			MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders.post("/medicalRecord")
					.contentType(MediaType.APPLICATION_JSON).content(jsonMedicalRecord.write(medicalRecordCreated).getJson()))
					.andReturn().getResponse();

			verify(medicalRecordService).addMedicalRecord(any(MedicalRecord .class));
			assertEquals(HttpStatus.CREATED.value(), result.getStatus());
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void givenMedicalRecordObjectWithBirthDateNoValid_WhenCreateMedicalRecord_ThenReturn400() throws Exception {	
		List<String> medications =new ArrayList<String>();
		medications.add("hydrapermazol:100mg");
		List<String> allergies =new ArrayList<String>();
		MedicalRecord medicalRecordCreatedBirthDateNoValid = new MedicalRecord("John","Boyd","03-06-1984",	medications,allergies);
		
		try {
			MockHttpServletResponse result = mockMvc
					.perform(MockMvcRequestBuilders.post("/medicalRecord")
							.contentType(MediaType.APPLICATION_JSON)
							.content(jsonMedicalRecord.write(medicalRecordCreatedBirthDateNoValid).getJson()))
							.andReturn().getResponse();

			// verify if constraint validation in model and in controller with @valid stop
			// the instruction post before call MedicalRecordService
			verify(medicalRecordService,Mockito.times(0)).addMedicalRecord(any(MedicalRecord .class));
			assertEquals(HttpStatus.BAD_REQUEST.value(), result.getStatus());
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}
	
}
