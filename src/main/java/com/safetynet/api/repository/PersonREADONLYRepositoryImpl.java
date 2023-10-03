package com.safetynet.api.repository;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.safetynet.api.model.Person;
import com.safetynet.api.service.ReadPersonDataFromFileImpl;

@Component
public class PersonREADONLYRepositoryImpl implements IPersonREADONLYRepository{
	@Autowired
	ReadPersonDataFromFileImpl readPersons;

	public List<Person> getPersonsFromFile() throws IOException {
		return findAll();
	}
	@Override
	public List<Person> findAll() throws IOException{
		return readPersons.readFile();
	}
}
