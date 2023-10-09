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
	private List<Person> listOfPersons;
	private List<Optional<Person>> listOfPersonsFoundByName;
	private Optional<Person> personFoundById;
	private Optional<Person> personFoundByName;

	@Autowired
	ReadPersonDataFromFileImpl readPersons;

	@Override
	public List<Person> findAll() throws IOException {
		listOfPersons = readPersons.readFile();
		return listOfPersons;
	}

	@Override
	public List<Optional<Person>> findByFirstNameAndLastName(String firstName, String lastName) {
		List<Person> persons = new ArrayList<Person>();
		listOfPersonsFoundByName = new ArrayList<Optional<Person>>();
		personFoundByName = Optional.empty();

		try {
			persons = readPersons.readFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		persons.forEach(elem -> {
			String firstNamePerson = elem.getFirstName();
			String lastNamePerson = elem.getLastName();
			if (firstNamePerson.equals(firstName) && lastNamePerson.equals(lastName)) {
				System.out.println("person found by first and last name of person :" + elem);
				personFoundByName = Optional.ofNullable(elem);
				listOfPersonsFoundByName.add(personFoundByName);
			}
		});

		System.out.println("listOfPersonsFoundByFullName :" + listOfPersonsFoundByName);
		return listOfPersonsFoundByName;
	}

	@Override
	public Optional<Person> findById(String id) {
		List<Person> persons = new ArrayList<Person>();
		personFoundById = Optional.empty();
		try {
			persons = readPersons.readFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		persons.forEach(elem -> {
			String idPerson = elem.getId();

			if (idPerson.toString().equals(id.toString())) {
				personFoundById = Optional.ofNullable(elem);
			}
		});

		System.out.println("personFoundById :" + personFoundById);
		return personFoundById;
	}

}
