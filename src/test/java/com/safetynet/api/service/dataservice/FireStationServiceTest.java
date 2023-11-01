package com.safetynet.api.service.dataservice;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.safetynet.api.model.FireStation;

@SpringBootTest
class FireStationServiceTest {
	@Autowired
	private FireStationService fireStationServiceUnderTest;
	private static FireStation fireStation;

	@BeforeAll
	static void setUp() throws IOException {
		fireStation = new FireStation("3 ", "1509 Culver St");
	}

	@Test
	void testAddFireStation() throws Exception {
		FireStation fireStationCreated = new FireStation("8", "15 rue de Dax");

		fireStationServiceUnderTest.addFireStation(fireStationCreated);

		FireStation resultFireStationCreatedRetrieved = fireStationServiceUnderTest
				.getOneFireStationByAddress("15 rue de Dax");
		String expectedStationNumber = fireStationCreated.getStationNumber();
		String expectedAddress = fireStationCreated.getAddress();
	
		assertAll("assertion all data of person created found by address",
				() -> assertNotNull(resultFireStationCreatedRetrieved),
				// checking setting id with request post
				() -> assertNotNull(resultFireStationCreatedRetrieved.getId()),
				() -> assertEquals(expectedStationNumber, resultFireStationCreatedRetrieved.getStationNumber()),
				() -> assertEquals(expectedAddress, resultFireStationCreatedRetrieved.getAddress()));
	}
}
