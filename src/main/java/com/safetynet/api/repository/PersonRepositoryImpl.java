package com.safetynet.api.repository;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.safetynet.api.model.Person;

@Component
public class PersonRepositoryImpl implements IPersonRepository {
	@Autowired
	private ReadPersonDataFromFileImpl readPersons;

	@Override
	public List<Person> findAll() throws IOException {
		return readPersons.readFile();
	}

}
