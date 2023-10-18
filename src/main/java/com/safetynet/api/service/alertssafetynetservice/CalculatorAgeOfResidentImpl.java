package com.safetynet.api.service.alertssafetynetservice;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.safetynet.api.service.dataservice.MedicalRecordService;

@Component
public class CalculatorAgeOfResidentImpl implements ICalculatorAgeOfResident {
	@Autowired
	MedicalRecordService medicalRecordService;

	private static final String DATE_FORMAT = "MM/dd/yyyy" ;
	private static final String YEAR_IN_MILLISECONDS= "31536000000" ;
	@Override
	public BigInteger calculateAgeOfResident(String idFirstAndLastName) {

		String birthDateOfPerson = medicalRecordService.getOneMedicalRecordById(idFirstAndLastName).getBirthdate();

		// formatage date birthdate
		DateFormat format = new SimpleDateFormat(DATE_FORMAT);
		Date birthdate = new Date();
		try {
			birthdate = format.parse(birthDateOfPerson);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// calcule de l age
		BigInteger yearInMs = new BigInteger(YEAR_IN_MILLISECONDS);// millisecondes par an
		Long ageOfPerson = new Date().getTime() - birthdate.getTime();
		System.out.println("age " + BigInteger.valueOf(ageOfPerson).divide(yearInMs));

		return BigInteger.valueOf(ageOfPerson).divide(yearInMs);
	}
}
