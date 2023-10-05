package com.safetynet.api.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

import org.springframework.stereotype.Component;

import com.safetynet.api.model.Person;

@Component
public class ReadPersonDataFromFileImpl implements IDatasFileReader<Person> {
	private String path = "src/main/resources/datasSafetyNetAlerts.json";
	private JsonArray datasJsonPersons;
	private List<Person> listOfPersons;
	// JsonObject dataJson;
	// @SuppressWarnings("rawtypes")

	@Override
	public List<Person> readFile() throws IOException {
		InputStream is = new FileInputStream(path);
		JsonReader jsonReader = Json.createReader(is);
		listOfPersons = new LinkedList<Person>();
		
		// get JsonObject from JsonReader
		JsonObject datasJsonPersonsObject = jsonReader.readObject();
		datasJsonPersons = datasJsonPersonsObject.getJsonArray("persons");
		System.out.println("all datas Json Person parsed with its arrays:" + datasJsonPersons);
		
		jsonReader.close();
		is.close();
		
		//create list linked of persons
		for( JsonValue elem : datasJsonPersons) {
			Person person =new Person(); 
			person.setId( elem.asJsonObject().getString("firstName") + " " + elem.asJsonObject().getString("lastName"));
			person.setFirstName( elem.asJsonObject().getString("firstName"));
			person.setLastName( elem.asJsonObject().getString("lastName"));
			person.setAddress( elem.asJsonObject().getString("address"));
			person.setCity( elem.asJsonObject().getString("city"));
			person.setZip( elem.asJsonObject().getString("zip"));
			person.setPhone( elem.asJsonObject().getString("phone"));
			person.setEmail( elem.asJsonObject().getString("email"));
			
			listOfPersons.add(person);
			System.out.println("element of persons"+elem.asJsonObject());
			
		}
		//listOfPersons.add();
		System.out.println("list of persons"+listOfPersons);

		return listOfPersons ;
		// return datasJsonPersonParsed ;
	}
}
