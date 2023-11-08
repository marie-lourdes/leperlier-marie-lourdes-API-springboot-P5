package com.safetynet.api.service.alertssafetynetservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.service.dataservice.MedicalRecordService;

@SpringBootTest
class CalculatorAgeOfResidentImplTest {
	@Autowired
	private CalculatorAgeOfResidentImpl calculatorAgeUnderTest;

	@MockBean
	private MedicalRecordService medicalRecordService;

	@ParameterizedTest(name = "the String birthdate ({0})   should return this date 01/01/2001 formatted and parsed ")
	@ValueSource(strings = { "01/01/2001" })
	void testformatDate(String arg) throws Exception {
		try {
			Date resultDate = calculatorAgeUnderTest.formatDate(arg);

			assertNotNull(resultDate);
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@ParameterizedTest(name = "the String birthdate({0})   should return error parsing date")
	@ValueSource(strings = { "01-01-2001" })
	void testformatDate_WithUnparseableDate_ShouldReturnError(String arg) throws Exception {
		try {
			Date resultDate = calculatorAgeUnderTest.formatDate(arg);

			assertNull(resultDate);
		} catch (Exception e) {
			assertThrows(ParseException.class, () -> calculatorAgeUnderTest.formatDate(arg));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	@DisplayName("Given The birthdate 09/04/1989 when calculate age of resident  the method should return 34")
	void testCalculateAgeOfResident() throws Exception {
		List<String> medications = new ArrayList<String>();
		medications.add("aznol:350mg");
		medications.add("hydrapermazol:100mg");
		List<String> allergies = new ArrayList<String>();
		allergies.add("nillacilan");
		MedicalRecord medicalRecordTest1 = new MedicalRecord("John", "Leperlier", "09/04/1989", medications, allergies);
		when(medicalRecordService.getOneMedicalRecordById(any(String.class))).thenReturn(medicalRecordTest1);

		try {
			BigInteger resultAge = calculatorAgeUnderTest.calculateAgeOfResident(medicalRecordTest1.getBirthdate());

			BigInteger expectedAge = BigInteger.valueOf(34);
			verify(medicalRecordService).getOneMedicalRecordById(any(String.class));
			assertEquals(expectedAge, resultAge);
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	@DisplayName("Given The birthdate when calculate age of no existing resident  the method should return 0 and error")
	void testCalculateAgeOfResident_WithResidentNotFound() throws Exception {
		try {
			medicalRecordService = new MedicalRecordService();
			when(medicalRecordService.getOneMedicalRecordById("Minnie Cooper")).thenThrow(NullPointerException.class);

			BigInteger resultAge = calculatorAgeUnderTest.calculateAgeOfResident(null);

			BigInteger expectedAge = BigInteger.valueOf(0);
			verify(medicalRecordService, Mockito.times(0)).getOneMedicalRecordById(any(String.class));
			assertEquals(expectedAge, resultAge);
		} catch (NullPointerException e) {
			assertThrows(NullPointerException.class,
					() -> medicalRecordService.getOneMedicalRecordById("Minnie Cooper"));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	@DisplayName("Given The birthdate with incorrect format when calculate age of resident  the method should return 0 and error ")
	void testCalculateAgeOfResident_WithIncorrectFormatOfBirthDate() throws Exception {
		List<String> medications = new ArrayList<String>();
		List<String> allergies = new ArrayList<String>();
		MedicalRecord medicalRecordTest2 = new MedicalRecord("Minnie", "Cooper", "02-12-1996", medications, allergies);
		when(medicalRecordService.getOneMedicalRecordById(any(String.class))).thenReturn(medicalRecordTest2);
		try {
			BigInteger resultAge = calculatorAgeUnderTest.calculateAgeOfResident(medicalRecordTest2.getBirthdate());

			BigInteger expectedAge = BigInteger.valueOf(0);
			verify(medicalRecordService).getOneMedicalRecordById(any(String.class));
			assertEquals(expectedAge, resultAge);
		} catch (Exception e) {
			assertThrows(Exception.class,
					() ->calculatorAgeUnderTest.calculateAgeOfResident(medicalRecordTest2.getBirthdate()));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}

	@Test
	@DisplayName("Given birthdate after date actual when calculate age of resident  the method should return 0 and error ")
	void testCalculateAgeOfResident_WithBirthdateAfterDateActual() throws Exception {
		List<String> medications = new ArrayList<String>();
		List<String> allergies = new ArrayList<String>();
		MedicalRecord medicalRecordTest3 = new MedicalRecord("Rachel", "Friends", "01/12/2023", medications, allergies);
		when(medicalRecordService.getOneMedicalRecordById(any(String.class))).thenReturn(medicalRecordTest3);
		try {
			BigInteger resultAge = calculatorAgeUnderTest.calculateAgeOfResident(medicalRecordTest3.getBirthdate());

			BigInteger expectedAge = BigInteger.valueOf(0);
			verify(medicalRecordService).getOneMedicalRecordById(any(String.class));
			assertEquals(expectedAge, resultAge);
		} catch (Exception e) {
			assertThrows(Exception.class,
					() -> calculatorAgeUnderTest.calculateAgeOfResident(medicalRecordTest3.getBirthdate()));
		} catch (AssertionError e) {
			fail(e.getMessage());
		}
	}
}
