package com.safetynet.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.SpringVersion;

@SpringBootTest
class SafetyNetAlertsApplicationTests {

	@Test
	void contextLoads() {
		assertEquals("6.0.14",SpringVersion.getVersion());
	}

}
