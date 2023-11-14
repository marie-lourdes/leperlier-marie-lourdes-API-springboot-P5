package com.safetynet.api.service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.json.JsonArray;
import javax.json.JsonValue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.safetynet.api.model.Person;

@Component
public class ReadPersonDataFromFileImpl implements IDatasFileReader<Person> {
	private static final Logger log = LogManager.getLogger( ReadPersonDataFromFileImpl.class);
	
	private JsonArray datasJsonPersons;
	private List<Person> listOfPersons;

	@Override
	public List<Person> readFile() throws IOException {
		listOfPersons = new LinkedList<Person>();

		try {
			// get JsonArray of data entity from JsonReader of Interface IDatasFileReader
			// use method astract readDataJson from interface generic IDatasFileReader
			datasJsonPersons = readDataJson("persons");

			// create list linked of persons
			for (JsonValue elem : datasJsonPersons) {
				Person person = new Person();
				person.setFirstName(elem.asJsonObject().getString("firstName"));
				person.setLastName(elem.asJsonObject().getString("lastName"));
				person.setAddress(elem.asJsonObject().getString("address"));
				person.setCity(elem.asJsonObject().getString("city"));
				person.setZip(elem.asJsonObject().getString("zip"));
				person.setPhone(elem.asJsonObject().getString("phone"));
				person.setEmail(elem.asJsonObject().getString("email"));

				listOfPersons.add(person);
			}
		} catch (ClassCastException e) {
			log.error(e.getMessage());
		} catch (NullPointerException e) {
			log.error("Missing data persons from file Json");
		} catch (Exception e) {
			log.error("An error has occured in reading datas persons from Json");
		}

		log.debug("List of persons parsed from Json {}", listOfPersons);
		return listOfPersons;
	}
}
