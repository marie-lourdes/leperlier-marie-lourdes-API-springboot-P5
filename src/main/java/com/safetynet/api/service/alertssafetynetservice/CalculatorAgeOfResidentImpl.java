package com.safetynet.api.service.alertssafetynetservice;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.safetynet.api.service.dataservice.MedicalRecordService;

public class CalculatorAgeOfResidentImpl implements ICalculatorAgeOfResident {
	@Override
	public BigInteger calculateAgeOfResident(String idFirstAndLastName) throws ParseException {
		MedicalRecordService medicalRecordService = new MedicalRecordService();
		String birthDateOfPerson = medicalRecordService.getOneMedicalRecordById(idFirstAndLastName).get()
				.getBirthdate();

		// formatage date birthdate
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		Date birthdate = format.parse(birthDateOfPerson);

		// calcule de l age
		BigInteger yearInMs = new BigInteger("31536000000");// millisecondes par an
		Long ageOfPerson = new Date().getTime() - birthdate.getTime();
		System.out.println("age " + BigInteger.valueOf(ageOfPerson).divide(yearInMs));

		// getBirthDateOfPersonWithMedicalRecord()
		// calculateAgeOfPerson();
		return BigInteger.valueOf(ageOfPerson).divide(yearInMs);
	}
}
