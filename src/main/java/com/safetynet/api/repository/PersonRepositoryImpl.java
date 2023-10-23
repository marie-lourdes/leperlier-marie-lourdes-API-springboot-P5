package com.safetynet.api.repository;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.safetynet.api.model.Person;
import com.safetynet.api.service.ReadPersonDataFromFileImpl;
import com.safetynet.api.service.dataservice.PersonService;

@Component
public class PersonRepositoryImpl implements IPersonRepository {
	private static final Logger log = LogManager.getLogger(PersonRepositoryImpl.class);
	@Autowired
	ReadPersonDataFromFileImpl readPersons;

	@Override
	public List<Person> findAll() throws IOException {
		log.debug("Reading data Persons from file");
		return readPersons.readFile();
	}

}
