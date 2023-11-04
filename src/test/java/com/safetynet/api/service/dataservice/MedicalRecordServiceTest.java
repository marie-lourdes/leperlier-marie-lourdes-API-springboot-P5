package com.safetynet.api.service.dataservice;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.Person;

@SpringBootTest
class MedicalRecordServiceTest {
	@Autowired
	private MedicalRecordService medicalRecordServiceUnderTest;
	private MedicalRecord medicalRecordTest1;
	private List<MedicalRecord> medicalRecords;
	private List<String> medicationsTest1Updated = new ArrayList<String>();
	private List<String> allergiesTest1Updated = new ArrayList<String>();
	private MedicalRecord medicalRecordTest1Updated = new MedicalRecord(medicationsTest1Updated, allergiesTest1Updated);

	@BeforeEach
	void setUpPerTest() {
		List<String> medications = new ArrayList<String>();
		medications.add("aznol:350mg");
		medications.add("hydrapermazol:100mg");
		List<String> allergies = new ArrayList<String>();
		allergies.add("nillacilan");
		medicalRecordTest1= new MedicalRecord("John", "Leperlier", "05/01/1965", medications, allergies);
		medicalRecordServiceUnderTest.addMedicalRecord(medicalRecordTest1);
		medicalRecords = medicalRecordServiceUnderTest.getAllMedicalRecords();
	}

	@AfterEach
	void reInitPerTest() {
		medicalRecords.clear();
	}
	
	@Test
	void testAddMedicalRecord() throws Exception {
		List<String> medications = new ArrayList<String>();
		medications.add("tetracyclaz:650mg");
		List<String> allergies = new ArrayList<String>();
		allergies.add("nillacilan");
		MedicalRecord medicalRecordCreated = new MedicalRecord("Minnie", "Cooper", "01/01/2001", medications,
				allergies);

		medicalRecordServiceUnderTest.addMedicalRecord(medicalRecordCreated);
		
		try {
			MedicalRecord resultmedicalRecordCreatedRetrieved = medicalRecordServiceUnderTest
					.getOneMedicalRecordById("Minnie Cooper");
			
			String expectedFirstName = medicalRecordCreated.getFirstName();
			String expectedLastName = medicalRecordCreated.getLastName();
			String expectedBirthDate = medicalRecordCreated.getBirthdate();
			List<String> expectedMedications= medicalRecordCreated.getMedications();
			List<String> expectedAllergies = medicalRecordCreated.getAllergies();
			assertAll("assertion all data of medicalRecordTest1created found by id full name",
					() -> assertNotNull(resultmedicalRecordCreatedRetrieved),
					// checking setting id with fullname in method addMedicalRecord() of
					// MedicalRecordService
					() -> assertEquals(expectedFirstName + " " + expectedLastName,
							resultmedicalRecordCreatedRetrieved.getId(),
							"the id should  be the first name and last name of personTest1"),
					() -> assertEquals(expectedFirstName, resultmedicalRecordCreatedRetrieved.getFirstName()),
					() -> assertEquals(expectedLastName, resultmedicalRecordCreatedRetrieved.getLastName()),
					() -> assertEquals(expectedBirthDate, resultmedicalRecordCreatedRetrieved.getBirthdate()),
					() -> assertEquals(expectedMedications, resultmedicalRecordCreatedRetrieved.getMedications()),
					() -> assertEquals( expectedAllergies, resultmedicalRecordCreatedRetrieved.getAllergies()));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}


/*	@Test
	void testUpdateMedicalRecord() throws Exception {
	
		medicationsTest1Updated.add("aznol:50mg");
		medicationsTest1Updated.add("hydrapermazol:50mg");
		
		medicationsTest1Updated.add("nillacilan");
	}*/
		

}
