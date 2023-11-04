package com.safetynet.api.service.dataservice;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.safetynet.api.model.MedicalRecord;

@SpringBootTest
class MedicalRecordServiceTest {
	@Autowired
	private MedicalRecordService medicalRecordServiceUnderTest;

	@Test
	void testAddMedicalRecord() throws Exception {
		List<String> medications = new ArrayList<String>();
		medications.add("pharmacol:5000mg");
		medications.add("terazine:10mg");
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

}
