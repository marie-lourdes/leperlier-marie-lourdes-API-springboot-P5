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
public class PersonREADONLYRepositoryImpl implements IPersonREADONLYRepository{
	private List<Optional<Person>>  listOfPersonsFoundByName;
	private	List<Person> persons;
	private Optional<Person> personFoundByName;
	
	@Autowired
	ReadPersonDataFromFileImpl readPersons;

	@Override
	public List<Person> findAll() throws IOException{
		return readPersons.readFile();
	}

	@Override
	public  List<Optional<Person>> findByName(String firstName, String lastName ){
		 persons = new ArrayList< Person>();
		 listOfPersonsFoundByName = new ArrayList< Optional<Person>>();
		personFoundByName= Optional.empty();
		try {
			persons = readPersons.readFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 persons.forEach(elem -> {
			String firstNamePerson = elem.getFirstName();
			String lastNamePerson = elem.getLastName();
			if (firstNamePerson.contains(firstName )&& lastNamePerson.contains(lastName)) {
				System.out.println("element found by first and last name of person" + elem);
				
				personFoundByName=Optional.ofNullable(elem);
				 listOfPersonsFoundByName.add(personFoundByName); 
			}		 	
	});
		 System.out.println("listOfPersonsFoundByName :" + listOfPersonsFoundByName);
		 return listOfPersonsFoundByName;
	 }
	/*@Override
	public Optional<Person> findByName() {
		// TODO Auto-generated method stub
		return Optional.empty();
	}*/
}
