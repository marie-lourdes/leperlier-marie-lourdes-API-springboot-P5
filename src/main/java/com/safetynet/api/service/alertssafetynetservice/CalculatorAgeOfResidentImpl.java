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
		log.debug("calculating age of person");
		// calcul age
		try {
			String birthDateOfPerson = medicalRecordService.getOneMedicalRecordById(idFirstAndLastName).getBirthdate();

			// format date birthdate
			log.debug("formating birthdate of person");
			Date birthDateOfPersonFormatted = new Date();
			try {
				birthDateOfPersonFormatted = this.formatAndParseDate(birthDateOfPerson);
				log.debug("Birthdate of person formatted succesfully: {}", birthDateOfPersonFormatted);
			} catch (ParseException e) {
				log.error(e.getMessage());
			}

			BigInteger yearInMs = new BigInteger(Constants.YEAR_IN_MILLISECONDS);// millisecondes par an
			Long ageOfPerson = new Date().getTime() - birthDateOfPersonFormatted.getTime();
			age = BigInteger.valueOf(ageOfPerson).divide(yearInMs);
			if (age == BigInteger.valueOf(0)) {
				throw new Exception(" Birthdate of person provided is incorrect");
			}

			log.debug("Age of person calculated successfully {}", age);
		} catch (Exception e) {
			log.error("An error has occured in calculating age");
			log.error(e.getMessage());
		}

		return age;
	}

	public Date formatAndParseDate(String birthDateOfPerson) throws ParseException {
		DateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);
		Date birthdate = new Date();
		try {
			birthdate = format.parse(birthDateOfPerson);
		} catch (Exception e) {
			log.error(e.getMessage());
			birthdate = null;
			throw new ParseException("An error has occured in  parsing birthdate", 0);
		}
		return birthdate;
	}

}
