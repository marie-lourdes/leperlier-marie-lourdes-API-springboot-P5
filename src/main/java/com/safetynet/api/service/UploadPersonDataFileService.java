package com.safetynet.api.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.api.model.Person;
import com.safetynet.api.repository.IPersonREADONLYRepository;

@Service
public class UploadPersonDataFileService implements IPersonREADONLYRepository{
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
