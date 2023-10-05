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
	private List<Optional<Person>>  listOfPersonsFoundByFullName;
	private	List<Person> persons;
	private Optional<Person> personFoundByFullName;
	
	@Autowired
	ReadPersonDataFromFileImpl readPersons;

	@Override
	public List<Person> findAll() throws IOException{
		return readPersons.readFile();
	}

	@Override
	public  List<Optional<Person>> findByFirstNameAndLastName(String firstName, String lastName ){
		 persons = new ArrayList< Person>();
		 listOfPersonsFoundByFullName = new ArrayList< Optional<Person>>();
		personFoundByFullName= Optional.empty();
		try {
			persons = readPersons.readFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 persons.forEach(elem -> {
			String firstNamePerson = elem.getFirstName();
			String lastNamePerson = elem.getLastName();
			if (firstNamePerson.equals(firstName ) && lastNamePerson.equals(lastName)) {
				System.out.println("person found by first and last name of person" + elem);
				
				personFoundByFullName=Optional.ofNullable(elem);
				 listOfPersonsFoundByFullName.add(personFoundByFullName); 
			}		 	
	});
		 System.out.println("listOfPersonsFoundByFullName :" + listOfPersonsFoundByFullName);
		 return listOfPersonsFoundByFullName;
	 }
	/*@Override
	public Optional<Person> findByName() {
		// TODO Auto-generated method stub
		return Optional.empty();
	}*/
}
