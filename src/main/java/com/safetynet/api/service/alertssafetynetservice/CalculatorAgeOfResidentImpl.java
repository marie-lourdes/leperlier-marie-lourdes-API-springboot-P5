package com.safetynet.api.service.alertssafetynetservice;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.safetynet.api.service.dataservice.MedicalRecordService;
import com.safetynet.api.utils.Constants;

@Component
public class CalculatorAgeOfResidentImpl implements ICalculatorAgeOfResident {
	private static final Logger log = LogManager.getLogger(CalculatorAgeOfResidentImpl.class);
	private BigInteger age;
	@Autowired
	MedicalRecordService medicalRecordService;

	@Override
	public BigInteger calculateAgeOfResident(String idFirstAndLastName) {
		System.out.println("calculating age of person");
		String birthDateOfPerson = medicalRecordService.getOneMedicalRecordById(idFirstAndLastName).getBirthdate();

		// format date birthdate
		Date birthDateOfPersonFormatted = this.formatDate(birthDateOfPerson);

		// calcul age
		try {
			BigInteger yearInMs = new BigInteger(Constants.YEAR_IN_MILLISECONDS);// millisecondes par an
			Long ageOfPerson = new Date().getTime() -  birthDateOfPersonFormatted.getTime();
			age = BigInteger.valueOf(ageOfPerson).divide(yearInMs);
			System.out.println("age calculated " + BigInteger.valueOf(ageOfPerson).divide(yearInMs));
		} catch (Exception e) {
			log.error("An error has occured in calculating age");
		}

		return age;
	}

	public Date formatDate(String birthDateOfPerson) {
		DateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);
		Date birthdate = new Date();
		try {
			birthdate = format.parse(birthDateOfPerson);
		} catch (ParseException e) {
			log.error(e.getMessage());
		} catch (Exception e) {
			log.error("An error has occured in formating birthdate of person");
		}
		return birthdate;
	}

}
