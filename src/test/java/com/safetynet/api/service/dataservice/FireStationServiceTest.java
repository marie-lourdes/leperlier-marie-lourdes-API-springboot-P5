package com.safetynet.api.service.dataservice;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
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
	void testGenerateId() throws Exception {
		FireStation fireStationCreated = new FireStation("7", "15 rue de Dax");

		fireStationServiceUnderTest.generateId(fireStationCreated);
		fireStations.add(fireStationCreated);

		List<FireStation> resultFireStationsByAddress = fireStationServiceUnderTest
				.getFireStationsByAddress("15 rue de Dax");

		for (FireStation fireStationFoundByAddress : resultFireStationsByAddress) {
			if (fireStationFoundByAddress.getStationNumber() == "7") {
				String resultIdGereratedOfFirestationStationRetrievedByAddress = fireStationFoundByAddress.getId();

				assertAll("assertion generation id of firestation created found by address",
						// checking setting id not null
						() -> assertNotNull(resultIdGereratedOfFirestationStationRetrievedByAddress),
						// checking setting id generated by the method generateId() is equal to id
						// retrieved with getFireStationByAddress()
						() -> assertTrue(fireStationCreated.getId()
								.contains(resultIdGereratedOfFirestationStationRetrievedByAddress)));
			}
		}
	}

	@Test
	void testAddFireStation() throws Exception {
		FireStation fireStationCreated = new FireStation("8", "15 rue de Dax");
		try {
			fireStationServiceUnderTest.addFireStation(fireStationCreated);

			String expectedStationNumber = fireStationCreated.getStationNumber();
			String expectedAddress = fireStationCreated.getAddress();
			assertNotNull(fireStationServiceUnderTest.getFireStationsByStationNumber(expectedStationNumber));
			assertNotNull(fireStationServiceUnderTest.getFireStationsByAddress(expectedAddress));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testAddStationNumberOfFireStationWithExistingAddress() throws Exception {
		FireStation fireStationCreatedWithNewStationNumber = new FireStation("9", "46  rue de la mairie");
		try {
			FireStation fireStationCreatedRetrievedWithNewStationNumber = fireStationServiceUnderTest
					.addStationNumberOfFireStationWithExistingAddress("46  rue de la mairie",
							fireStationCreatedWithNewStationNumber);

			List<FireStation> resultFireStationsByAddress = fireStationServiceUnderTest
					.getFireStationsByAddress("46  rue de la mairie");
			// checking firestation with new station number created with existing address
			assertTrue(resultFireStationsByAddress.contains(fireStationCreatedRetrievedWithNewStationNumber));
			
			// checking firestation with new station number created with existing address
			for (FireStation existingFireStationFoundByAddress : resultFireStationsByAddress) {
				if (existingFireStationFoundByAddress.getStationNumber() == "9") {
					assertAll(
							"assertion of new station number of firestation created firestation found by existing address",
							// checking firestation with new station number is present in list of
							// firestations with existing address
							() -> assertTrue(fireStations.contains(existingFireStationFoundByAddress)),
							// checking setting id
							() -> assertNotNull(existingFireStationFoundByAddress.getId()));
				}
			}
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testAddStationNumberOfFireStationWithExistingAddress_WithNoExistingAddress() throws Exception {
		FireStation fireStationCreatedWithNewStationNumber = new FireStation("8", "45 No existing address");
		try {
			FireStation fireStationCreatedRetrievedWithNewStationNumber =fireStationServiceUnderTest.addStationNumberOfFireStationWithExistingAddress(" 45 No existing address",
					fireStationCreatedWithNewStationNumber);

			List<FireStation> resultFireStationsByAddress = fireStationServiceUnderTest
					.getFireStationsByAddress("45 No existing address");
		
			assertFalse( resultFireStationsByAddress.contains(fireStationCreatedRetrievedWithNewStationNumber));
			assertNull(resultFireStationsByAddress);
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class,
					() -> fireStationServiceUnderTest.addStationNumberOfFireStationWithExistingAddress(
							" 45 No existing address", fireStationCreatedWithNewStationNumber));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test 
	  void  testAddAddressOfFireStationWithExistingStationNumber() throws Exception {
			FireStation fireStationCreatedWithNewAddress = new FireStation("6", "4 boulevard du soldat inconnu");
			try {
				FireStation fireStationCreatedRetrievedWithNewAddress=	fireStationServiceUnderTest.addAddressOfFireStationWithExistingStationNumber("6",
						fireStationCreatedWithNewAddress );

				List<FireStation> resultFireStationsByStationNumber = fireStationServiceUnderTest
						.getFireStationsByStationNumber("6");
				// checking firestation with new address created with existing station number
				assertTrue(resultFireStationsByStationNumber.contains(fireStationCreatedRetrievedWithNewAddress));
				
				// checking firestation with new address created with existing station number
				for (FireStation existingFireStationFoundByStationNumber :  resultFireStationsByStationNumber) {
					if  (existingFireStationFoundByStationNumber.getAddress() == "4 boulevard du soldat inconnu") {
						assertAll(
								"assertion of new station number of firestation created firestation found by existing address",
								// checking firestation with new address  is present in list of firestations with existing station number
								() -> assertTrue(fireStations.contains(existingFireStationFoundByStationNumber)),
						// checking setting id
						() -> assertNotNull(existingFireStationFoundByStationNumber.getId()));
					}
				}
			} catch (AssertionError e) {
				fail(e.getMessage());
			}
 }

}
