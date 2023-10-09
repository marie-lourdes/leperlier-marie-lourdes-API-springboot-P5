package com.safetynet.api.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.safetynet.api.model.Person;
import com.safetynet.api.service.ReadPersonDataFromFileImpl;

@Component
public class PersonRepositoryImpl implements IPersonRepository {	
	@Autowired
	ReadPersonDataFromFileImpl readPersons;

	@Override
	public List<Person> findAll() throws IOException {
		 return readPersons.readFile();		
	}


}
