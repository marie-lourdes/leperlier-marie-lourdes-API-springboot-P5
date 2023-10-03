package com.safetynet.api.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.safetynet.api.model.Person;

public class UploadPersonDataFileService {
	@Autowired
	ReadPersonDataFromFileImpl  readPersons;
	
	public List<Person>getPersonsFromFile() throws IOException {
		 return	readPersons.readFile();
		}
}
