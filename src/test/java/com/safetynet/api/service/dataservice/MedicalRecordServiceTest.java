package com.safetynet.api.service.dataservice;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Collections;
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
		medicalRecordTest1 = new MedicalRecord("John", "Leperlier", "05/01/1965", medications, allergies);
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
			List<String> expectedMedications = medicalRecordCreated.getMedications();
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
					() -> assertEquals(expectedAllergies, resultmedicalRecordCreatedRetrieved.getAllergies()));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testAddMedicalRecord_WithMedicalRecordDuplicated() throws Exception {
		try {
			MedicalRecord resultMedicalRecordCreated=	medicalRecordServiceUnderTest.addMedicalRecord(medicalRecordTest1);	
			medicalRecords = medicalRecordServiceUnderTest.getAllMedicalRecords();
			Integer countMedicalRecordCreatedDuplicated = Collections.frequency(medicalRecords ,resultMedicalRecordCreated);
			
			assertTrue(countMedicalRecordCreatedDuplicated >1);
		}catch (IllegalArgumentException e) {
				assertThrows(IllegalArgumentException.class,
						() -> medicalRecordServiceUnderTest.addMedicalRecord(medicalRecordTest1));
			} catch (AssertionError e) {
				fail(e.getMessage());
			}
	}
	
	@Test
	void testUpdateMedicationsMedicalRecord() throws Exception {
		medicationsTest1Updated.add("aznol:50mg");

		try {
			// existing medicalRecordTest1 before updating
			MedicalRecord medicalRecordRetrievedById = medicalRecordServiceUnderTest
					.getOneMedicalRecordById("John Leperlier");

			// existing medicalRecordTest1 after updating
			MedicalRecord resultMedicalRecordUpdatedRetrieved = medicalRecordServiceUnderTest
					.updateOneMedicalRecordById("John Leperlier", medicalRecordTest1Updated);

			String expectedFirstName = medicalRecordRetrievedById.getFirstName();
			String expectedLastName = medicalRecordRetrievedById.getLastName();
			String expectedBirthDate = medicalRecordRetrievedById.getBirthdate();
			List<String> expectedMedications = medicalRecordTest1Updated.getMedications();
			List<String> expectedAllergies = medicalRecordRetrievedById.getAllergies();
			assertAll("assertion all data of medicalRecordTest1updated found by id full name",
					() -> assertNotNull(resultMedicalRecordUpdatedRetrieved),
					() -> assertSame(medicalRecordRetrievedById, resultMedicalRecordUpdatedRetrieved),

					// checking if id with fullname and all datas except medications of existing
					// medicalRecordtest1
					// don't change when updating
					() -> assertEquals(medicalRecordRetrievedById.getId(), resultMedicalRecordUpdatedRetrieved.getId(),
							"the id should  be the first name and last name of medicalRecordTest1"),
					() -> assertEquals(expectedFirstName, resultMedicalRecordUpdatedRetrieved.getFirstName()),
					() -> assertEquals(expectedLastName, resultMedicalRecordUpdatedRetrieved.getLastName()),
					() -> assertEquals(expectedBirthDate, resultMedicalRecordUpdatedRetrieved.getBirthdate()),
					// checking if medications of existing medicalrecordTest1 change when updating
					// new address
					() -> assertEquals(expectedMedications, resultMedicalRecordUpdatedRetrieved.getMedications()),
					() -> assertEquals(expectedAllergies, resultMedicalRecordUpdatedRetrieved.getAllergies()));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testUpdateMedicationsMedicalRecord_WithIncorrectId() throws Exception {
		medicationsTest1Updated.add("aznol:50mg");

		try {
			MedicalRecord resultMedicalRecordUpdated = medicalRecordServiceUnderTest
					.updateOneMedicalRecordById("John Lenon", medicalRecordTest1Updated);

			assertNull(resultMedicalRecordUpdated);
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class, () -> medicalRecordServiceUnderTest
					.updateOneMedicalRecordById("John Lenon", medicalRecordTest1Updated));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testDeleteOneMedicalRecordById() throws Exception {
		try {
			boolean resultMedicalRecordRemoved = medicalRecordServiceUnderTest
					.deleteOneMedicalRecordById("John Leperlier");

			assertTrue(resultMedicalRecordRemoved);
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testDeleteOneMedicalRecordById_WithIncorrectId() throws Exception {
		try {
			boolean resultMedicalRecordRemoved = medicalRecordServiceUnderTest.deleteOneMedicalRecordById("John Lenon");

			assertFalse(resultMedicalRecordRemoved);
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class,
					() -> medicalRecordServiceUnderTest.deleteOneMedicalRecordById("John Lenon"));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testGetOneMedicalRecordById() throws Exception {
		try {
			MedicalRecord resultMedicalRecord = medicalRecordServiceUnderTest.getOneMedicalRecordById("John Leperlier");

			String expectedFirstName = medicalRecordTest1.getFirstName();
			String expectedLastName = medicalRecordTest1.getLastName();
			String expectedBirthDate = medicalRecordTest1.getBirthdate();
			List<String> expectedMedications = medicalRecordTest1.getMedications();
			List<String> expectedAllergies = medicalRecordTest1.getAllergies();
			assertAll("assertion all data of medicalRecordTest1 found by id", () -> assertNotNull(resultMedicalRecord),
					() -> assertEquals(expectedFirstName + " " + expectedLastName, resultMedicalRecord.getId()),
					() -> assertEquals(expectedFirstName, resultMedicalRecord.getFirstName()),
					() -> assertEquals(expectedLastName, resultMedicalRecord.getLastName()),
					() -> assertEquals(expectedBirthDate, resultMedicalRecord.getBirthdate()),
					() -> assertEquals(expectedMedications, resultMedicalRecord.getMedications()),
					() -> assertEquals(expectedAllergies, resultMedicalRecord.getAllergies()));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testGetOneMedicalRecordById_WithIdNotFound() throws Exception {
		try {
			MedicalRecord resultMedicalRecord = medicalRecordServiceUnderTest.getOneMedicalRecordById("John Lenon");

			assertNull(resultMedicalRecord);
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class,
					() -> medicalRecordServiceUnderTest.getOneMedicalRecordById("John Lenon"));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testGetAllMedicalRecords() throws Exception {
		try {
			List<MedicalRecord> resultAllMedicalRecords = medicalRecordServiceUnderTest.getAllMedicalRecords();

			assertAll("assertion of all medicalRecord retrieved", () -> assertNotNull(resultAllMedicalRecords),
					() -> assertTrue(resultAllMedicalRecords.contains(medicalRecordTest1)));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testGetAllMedicalRecords_NotFound() throws Exception {
		medicalRecords.clear();
		try {
			medicalRecordServiceUnderTest.getAllMedicalRecords();
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class, () -> medicalRecordServiceUnderTest.getAllMedicalRecords());
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}
}