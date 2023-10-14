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

	@Override
	public BigInteger calculateAgeOfResident(String idFirstAndLastName) {

		String birthDateOfPerson = medicalRecordService.getOneMedicalRecordById(idFirstAndLastName).get()
				.getBirthdate();

		// formatage date birthdate
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		Date birthdate = new Date();
		try {
			birthdate = format.parse(birthDateOfPerson);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// calcule de l age
		BigInteger yearInMs = new BigInteger("31536000000");// millisecondes par an
		Long ageOfPerson = new Date().getTime() - birthdate.getTime();
		System.out.println("age " + BigInteger.valueOf(ageOfPerson).divide(yearInMs));

		return BigInteger.valueOf(ageOfPerson).divide(yearInMs);
	}
}
