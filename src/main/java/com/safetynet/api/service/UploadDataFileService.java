package com.safetynet.api.service;

import java.io.FileNotFoundException;

import javax.json.JsonArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UploadDataFileService {
	@Autowired
	ReadPersonDataFromFileImpl  readPerson;

	public JsonArray getPersonsFromFile() throws FileNotFoundException {
	 return	readPerson.readFile();
	}
}
