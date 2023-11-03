package com.safetynet.api.service.dataservice;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
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

	private FireStation fireStationTest1;
	private FireStation fireStationTest2;
	private List<FireStation> fireStations;
	private FireStation fireStationTest1Updated = new FireStation("11", "46  rue de la mairie");

	@BeforeEach
	void setUpPerTest() {
		fireStationTest1 = new FireStation("5", "46  rue de la mairie");
		fireStationTest2 = new FireStation("6", "16 rue du quartiers des combatants");
		fireStationServiceUnderTest.addFireStation(fireStationTest1);
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
			FireStation fireStationCreatedRetrievedWithNewStationNumber = fireStationServiceUnderTest
					.addStationNumberOfFireStationWithExistingAddress(" 45 No existing address",
							fireStationCreatedWithNewStationNumber);

			List<FireStation> resultFireStationsByAddress = fireStationServiceUnderTest
					.getFireStationsByAddress("45 No existing address");

			assertFalse(resultFireStationsByAddress.contains(fireStationCreatedRetrievedWithNewStationNumber));
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
	void testAddAddressOfFireStationWithExistingStationNumber() throws Exception {
		FireStation fireStationCreatedWithNewAddress = new FireStation("6", "4 boulevard du soldat inconnu");
		try {
			FireStation fireStationCreatedRetrievedWithNewAddress = fireStationServiceUnderTest
					.addAddressOfFireStationWithExistingStationNumber("6", fireStationCreatedWithNewAddress);

			List<FireStation> resultFireStationsByStationNumber = fireStationServiceUnderTest
					.getFireStationsByStationNumber("6");
			// checking firestation with new address created with existing station number
			assertTrue(resultFireStationsByStationNumber.contains(fireStationCreatedRetrievedWithNewAddress));

			// checking firestation with new address created with existing station number
			for (FireStation existingFireStationFoundByStationNumber : resultFireStationsByStationNumber) {
				if (existingFireStationFoundByStationNumber.getAddress() == "4 boulevard du soldat inconnu") {
					assertAll(
							"assertion of new station number of firestation created firestation found by existing address",
							// checking firestation with new address is present in list of firestations with
							// existing station number
							() -> assertTrue(fireStations.contains(existingFireStationFoundByStationNumber)),
							// checking setting id
							() -> assertNotNull(existingFireStationFoundByStationNumber.getId()));
				}
			}
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testAddAddressOfFireStationWithExistingStationNumber_WithNoExistingStationNumber() throws Exception {
		FireStation fireStationCreatedWithNewAddress = new FireStation("10", "4 boulevard du soldat inconnu");
		try {
			FireStation fireStationCreatedRetrievedWithNewAddress = fireStationServiceUnderTest
					.addAddressOfFireStationWithExistingStationNumber("10", fireStationCreatedWithNewAddress);

			List<FireStation> resultFireStationsByStationNumber = fireStationServiceUnderTest
					.getFireStationsByStationNumber("6");

			assertFalse(resultFireStationsByStationNumber.contains(fireStationCreatedRetrievedWithNewAddress));
			assertNull(resultFireStationsByStationNumber);
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class, () -> fireStationServiceUnderTest
					.addAddressOfFireStationWithExistingStationNumber("10", fireStationCreatedWithNewAddress));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testUpdateFireStation() throws Exception {
		try {
			// existing fireStationTest1 before updating
			List<FireStation> existingFireStationsByAddress = fireStationServiceUnderTest
					.getFireStationsByAddress("46  rue de la mairie");

			String expectedAddress = " ";
			String idOfExistingFireStation = " ";
			String expectedStationNumber = " ";
			FireStation existingFireStation = new FireStation();
			for (FireStation existingFireStationFoundByAddress : existingFireStationsByAddress) {
				if (existingFireStationFoundByAddress.getStationNumber() == "5") {
					idOfExistingFireStation = existingFireStationFoundByAddress.getId();
					expectedStationNumber = existingFireStationFoundByAddress.getStationNumber();
					expectedAddress = existingFireStationFoundByAddress.getAddress();
					existingFireStation = existingFireStationFoundByAddress;
				}
			}

			FireStation resultFireStationUpdatedRetrieved = fireStationServiceUnderTest
					.updateFireStation("46  rue de la mairie", fireStationTest1Updated);

			// existing fireStationTest1 after updating
			List<FireStation> existingFireStationsUpdatedByAddress = fireStationServiceUnderTest
					.getFireStationsByAddress("46  rue de la mairie");

			assertNotNull(resultFireStationUpdatedRetrieved);
			// checking firestation updated with new station number is present in list of
			// existing firestations found by address
			assertTrue(existingFireStationsUpdatedByAddress.contains(resultFireStationUpdatedRetrieved));
			assertNotNull(fireStationServiceUnderTest.getFireStationsByStationNumber("11"));

			for (FireStation existingFireStationFoundByAddress : existingFireStationsUpdatedByAddress) {
				if (existingFireStationFoundByAddress.getStationNumber() == "11") {
					assertSame(existingFireStationFoundByAddress, resultFireStationUpdatedRetrieved);
					// checking if id of existing firestation updated don't change when updating
					assertEquals(idOfExistingFireStation, existingFireStationFoundByAddress.getId());
					// checking if address of existing firestation don'change when updating
					// station number
					assertEquals(expectedAddress, existingFireStationFoundByAddress.getAddress());
					// checking station number of existing firestation change when updating
					assertNotEquals(expectedStationNumber, existingFireStationFoundByAddress.getStationNumber());
				}
			}
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testUpdateFireStation_WithNoExistingFireStationByAddress() throws Exception {
		try {
			FireStation resultFireStationUpdatedRetrieved = fireStationServiceUnderTest
					.updateFireStation("45 No existing address", fireStationTest1Updated);

			assertNull(resultFireStationUpdatedRetrieved);
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class, () -> fireStationServiceUnderTest
					.updateFireStation("45 No existing address", fireStationTest1Updated));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testDeleteFireStationByStationNumber() throws Exception {
		try {
			boolean resultFireStationRemoved = fireStationServiceUnderTest.deleteFireStationByStationNumber("6");
			assertTrue(resultFireStationRemoved);
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	void testDeleteFireStationByStationNumber_WithNoExistingFireStationByStationNumber() throws Exception {
		try {
			boolean resultPersonRemoved = fireStationServiceUnderTest.deleteFireStationByStationNumber("12");
			assertFalse(resultPersonRemoved);
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class,
					() -> fireStationServiceUnderTest.deleteFireStationByStationNumber("12"));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

@Test
void testDeleteFireStationByAddress() throws Exception {
	try {
		boolean resultFireStationRemoved = fireStationServiceUnderTest.deleteFireStationByAddress("16 rue du quartiers des combatants");
		assertTrue(resultFireStationRemoved);
	} catch (AssertionError e) {
		fail(e.getMessage());
	}
}

@Test
void testDeleteFireStationByAddress_WithNoExistingFireStationByAddress() throws Exception {
	try {
		boolean resultFireStationRemoved = fireStationServiceUnderTest.deleteFireStationByAddress("16 rue du quartiers des combatants");
		assertTrue(resultFireStationRemoved);
	} catch (AssertionError e) {
		fail(e.getMessage());
	}
}
}
