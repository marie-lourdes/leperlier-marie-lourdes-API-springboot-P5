package com.safetynet.api.service.alertssafetynetservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class CommunityEmailServiceTest {
	@Autowired
	private CommunityEmailService communityEmailServiceUnderTest;

	@MockBean
	private SearchingInfoEmailOfResidentsByCityImpl infoEmailOfResidentsByCity;

	private static Map<String, String> emailOfResidentFoundByCityTest1;
	private static Map<String, String> emailOfResidentFoundByCityTest2;
	private static List<Map<String, String>> listOfemailsOfResidentFoundByCityTest1;

	@BeforeAll
	static void setUp() {
		emailOfResidentFoundByCityTest1 = new HashMap<String, String>();
		emailOfResidentFoundByCityTest1.put("email", "jaboyd@email.com");
		emailOfResidentFoundByCityTest2 = new HashMap<String, String>();
		emailOfResidentFoundByCityTest2.put("email", "drk@email.com");
		listOfemailsOfResidentFoundByCityTest1 = new ArrayList<Map<String, String>>();
		listOfemailsOfResidentFoundByCityTest1.add(emailOfResidentFoundByCityTest1);
		listOfemailsOfResidentFoundByCityTest1.add(emailOfResidentFoundByCityTest2);
	}

	@Test
	void testGetEmailOfResidentsOfCity() {
		when(infoEmailOfResidentsByCity.searchInfoOfResident("Dax")).thenReturn(listOfemailsOfResidentFoundByCityTest1);
		
		try {
			List<Map<String, String>> resultlistOfemailsOfResidentFoundByCity=communityEmailServiceUnderTest.getEmailOfResidentsOfCity("Dax");
			
            verify(infoEmailOfResidentsByCity).searchInfoOfResident(any(String.class));
            assertEquals(listOfemailsOfResidentFoundByCityTest1,resultlistOfemailsOfResidentFoundByCity);
   
			} catch (AssertionError e) {
				fail(e.getMessage());
			}
	}

	@Test
	void testGetEmailOfResidentsOfCity_WithEmailNotFoundByCity() {
		when(infoEmailOfResidentsByCity.searchInfoOfResident("No Existing City registered")).thenReturn( new ArrayList<Map<String, String>>());
		
		try {
			List<Map<String, String>> resultlistOfemailsOfResidentFoundByCity=communityEmailServiceUnderTest.getEmailOfResidentsOfCity("No Existing City registered");
			
            verify(infoEmailOfResidentsByCity).searchInfoOfResident(any(String.class));
            assertTrue(resultlistOfemailsOfResidentFoundByCity.isEmpty());
   
			} catch (NullPointerException e) {
				assertThrows(NullPointerException.class,
						() ->communityEmailServiceUnderTest.getEmailOfResidentsOfCity("No Existing City registered"));
			} catch (AssertionError e) {
				fail(e.getMessage());
			}
	}
}
