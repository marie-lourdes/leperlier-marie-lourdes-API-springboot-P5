package com.safetynet.api.service.alertssafetynetservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.safetynet.api.utils.Constants;

@SpringBootTest
class CalculatorAgeOfResidentImplTest {
@Autowired
CalculatorAgeOfResidentImpl calculatorAge; 
	
	
	@ParameterizedTest(name = "the String birthdate ({0})   should return this date 01/01/2001 formatted and parsed ")
	@ValueSource(strings = { "01/01/2001"})
	void testformatDate(String arg)  throws Exception {
		Date expectedDate=new SimpleDateFormat(Constants.DATE_FORMAT).parse(arg);
		System.out.println("expectedDate"+expectedDate);
		Date resultDate= calculatorAge.formatAndParseDate(arg);
		assertEquals(expectedDate, resultDate);
	}

	@ParameterizedTest(name = "the String birthdate({0})   should return error parsing date")
	@ValueSource(strings = { "01-01-2001"})
	void testformatDate_WithUnparseableDate_shouldReturnError(String arg) throws Exception {
		try {
		 calculatorAge.formatAndParseDate(arg);
		}catch(ParseException e) {
			assertThrows(ParseException.class,
					() -> calculatorAge.formatAndParseDate(arg));
		}catch(AssertionError e) {
			fail(e.getMessage());
		}
	}
}
