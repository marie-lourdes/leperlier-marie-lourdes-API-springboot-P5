package com.safetynet.api.service.alertssafetynetservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
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
class ChildAlertServiceTest {
	@Autowired
	ChildAlertService childAlertServiceUnderTest;

	@MockBean
	SearchingFullInfoOfResidentsByAddressImpl fullInfoOfResidentsByAddress;

	private static Map<String, String> residentFoundByAddressTest1;
	private static Map<String, String> residentFoundByAddressTest2;
	private static List<Map<String, String>> listOfChildsAndMembersOfHouseHoldTest;

	@BeforeAll
	static void setUp() {
		residentFoundByAddressTest1 = new HashMap<String, String>();
		residentFoundByAddressTest1.put("firstName", "Millie");
		residentFoundByAddressTest1.put("lastName", "Leperlier");
		residentFoundByAddressTest1.put("address", "112 address");
		residentFoundByAddressTest1.put("city", "City");
		residentFoundByAddressTest1.put("zip", "45896");
		residentFoundByAddressTest1.put("email", "Millie@email.com");
		residentFoundByAddressTest1.put("phone", "841-874-6512");
		residentFoundByAddressTest1.put("age", "34");
		residentFoundByAddressTest2 = new HashMap<String, String>();
		residentFoundByAddressTest2.put("firstName", "Maelys");
		residentFoundByAddressTest2.put("lastName", "Leperlier");
		residentFoundByAddressTest2.put("address", "112 address");
		residentFoundByAddressTest2.put("phone", "841-874-6512");
		residentFoundByAddressTest2.put("age", "8");
		listOfChildsAndMembersOfHouseHoldTest = new ArrayList<Map<String, String>>();
		listOfChildsAndMembersOfHouseHoldTest.add(residentFoundByAddressTest1);
		listOfChildsAndMembersOfHouseHoldTest.add(residentFoundByAddressTest2);
	}

	@Test
	void testGetChildsAndMembersOfHouseHold() throws Exception {
		when(fullInfoOfResidentsByAddress.searchInfoOfResident("112 address")).thenReturn(listOfChildsAndMembersOfHouseHoldTest);
	
		try {
			List<Map<String, String>> listOfResidentChildAndMembersOfHouseHold=childAlertServiceUnderTest.getChildsAndMembersOfHouseHold("112 address");
			
			verify(fullInfoOfResidentsByAddress).searchInfoOfResident(any(String.class));
			assertEquals(listOfChildsAndMembersOfHouseHoldTest,listOfResidentChildAndMembersOfHouseHold);
		
			} catch (AssertionError e) {
				fail(e.getMessage());
			}
	}

	@Test
	void testGetChildsAndMembersOfHouseHold_WithResidentsNotFoundByAddress() throws Exception {
		when(fullInfoOfResidentsByAddress.searchInfoOfResident("112 address")).thenReturn(new ArrayList<Map<String, String>>());
	
		try {
			List<Map<String, String>> listOfResidentChildAndMembersOfHouseHold=childAlertServiceUnderTest.getChildsAndMembersOfHouseHold("112 address");
			
			verify(fullInfoOfResidentsByAddress).searchInfoOfResident(any(String.class));
			assertTrue(listOfResidentChildAndMembersOfHouseHold.isEmpty());
		}  catch (NullPointerException e) {
			assertThrows(NullPointerException.class,
					() ->childAlertServiceUnderTest.getChildsAndMembersOfHouseHold("112 address"));
	     }catch (AssertionError e) {
				fail(e.getMessage());
			
	    }
	}
}
