package com.safetynet.api.service.dataservice;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.safetynet.api.model.FireStation;

@SpringBootTest
class FireStationServiceTest {
	@Autowired
	private FireStationService fireStationServiceUnderTest;
	
	private FireStation fireStationTest;
	private FireStation fireStationTest2;
	private List<FireStation> fireStations;

	@BeforeEach
	void setUpPerTest() {
		fireStationTest = new FireStation("5", "46  rue de la mairie");
		fireStationTest2 = new FireStation("6", "16 rue du quartiers des combatants");
		fireStationServiceUnderTest.addFireStation(fireStationTest);
		fireStationServiceUnderTest.addFireStation(fireStationTest2);
		 fireStations = fireStationServiceUnderTest.getAllFireStations();
	}

	@AfterEach
	void reInitPerTest() {
		 fireStations.clear();
	}

	@Test
	void testGenerateId() throws Exception{
	FireStation	 fireStationCreated = new FireStation("7", "15 rue de Dax");
		 
		 fireStationServiceUnderTest.generateId(fireStationCreated);
		 fireStationServiceUnderTest.addFireStation(fireStationCreated);
			FireStation resultFireStationCreatedRetrievedWithIdGenerated = fireStationServiceUnderTest
					.getOneFireStationByAddress("15 rue de Dax");
			String resultFirestationStationIdRetrieved=  resultFireStationCreatedRetrievedWithIdGenerated.getId();
			String expectedStationNumber = fireStationCreated.getStationNumber();
			String[] addressSplit = fireStationCreated.getAddress().split(" ", -1);
			String expectedAddressNumber  = addressSplit[0];
			
			assertAll("assertion genration id of firestation created found by address",
					() -> assertNotNull(resultFireStationCreatedRetrievedWithIdGenerated ),
					// checking setting id with request post
					() -> assertNotNull(resultFirestationStationIdRetrieved),
					() -> assertTrue( resultFirestationStationIdRetrieved.contains(expectedAddressNumber )&& resultFirestationStationIdRetrieved.contains(expectedStationNumber)));
		}
	
	
	@Test
	void testAddFireStation() throws Exception {
		FireStation fireStationCreated = new FireStation("8", "15 rue de Dax");

		fireStationServiceUnderTest.addFireStation(fireStationCreated);

		FireStation resultFireStationCreatedRetrieved = fireStationServiceUnderTest
				.getOneFireStationByAddress("15 rue de Dax");
		String expectedStationNumber = fireStationCreated.getStationNumber();
		String expectedAddress = fireStationCreated.getAddress();
	
		assertAll("assertion all data of firestation created found by address",
				() -> assertNotNull(resultFireStationCreatedRetrieved),
				// checking setting id with request post
				() -> assertNotNull(resultFireStationCreatedRetrieved.getId()),
				() -> assertEquals(expectedStationNumber, resultFireStationCreatedRetrieved.getStationNumber()),
				() -> assertEquals(expectedAddress, resultFireStationCreatedRetrieved.getAddress()));
	}

	/*@Test
	void testAddStationNumberOfExistingFireStation() throws Exception{
		 
	 }*/
}
