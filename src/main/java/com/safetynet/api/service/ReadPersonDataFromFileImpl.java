package com.safetynet.api.service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.json.JsonArray;
import javax.json.JsonValue;

import org.springframework.stereotype.Component;

import com.safetynet.api.model.Person;

@Component
public class ReadPersonDataFromFileImpl implements IDatasFileReader<Person> {
	private JsonArray datasJsonPersons;
	private List<Person> listOfPersons;

	@Override
	public List<Person> readFile() throws IOException {
		listOfPersons = new LinkedList<Person>();
		//listOfPersons = new ArrayList<Person>();
		// get JsonArray of data entity from JsonReader of Interface IDatasFileReader
		datasJsonPersons = readDataJson("persons");

		// create list linked of persons
		for (JsonValue elem : datasJsonPersons) {
			Person person = new Person();
			person.setId(elem.asJsonObject().getString("firstName") + " " + elem.asJsonObject().getString("lastName"));
			person.setFirstName(elem.asJsonObject().getString("firstName"));
			person.setLastName(elem.asJsonObject().getString("lastName"));
			person.setAddress(elem.asJsonObject().getString("address"));
			person.setCity(elem.asJsonObject().getString("city"));
			person.setZip(elem.asJsonObject().getString("zip"));
			person.setPhone(elem.asJsonObject().getString("phone"));
			person.setEmail(elem.asJsonObject().getString("email"));

			listOfPersons.add(person);
			System.out.println("element of persons" + elem.asJsonObject());
			
		}
		// listOfPersons.add();
		System.out.println("list of persons" + listOfPersons);

		return listOfPersons;
		// return datasJsonPersonParsed ;
	}
public List<Person>	getListOfPersons(){
		return listOfPersons;
	}
}
