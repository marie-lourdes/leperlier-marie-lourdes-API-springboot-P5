package com.safetynet.api.service.dataservice;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.safetynet.api.model.Person;
import com.safetynet.api.utils.Constants;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonService {
	private static final Logger log = LogManager.getLogger(PersonService.class);
	
	private List<Person> persons = new ArrayList<>();

	public Person addPerson(Person person) throws NullPointerException {
		log.debug("Adding person: {}", person.getFirstName() + " " + person.getLastName());

		person.setId(person.getFirstName() + " " + person.getLastName());
		persons.add(person);

		log.info("Person added successfully: {}", person);
		return person;
	}

	public Person updateOnePersonById(String id, Person updatedPerson) throws NullPointerException {
		log.debug("Updating person for: {}", id);
		Person existingPersonUpdated = new Person();
		existingPersonUpdated = persons.stream().filter(person -> person.getId().equals(id)).findFirst()
				.map(existingPerson -> {
					existingPerson.setAddress(updatedPerson.getAddress());
					existingPerson.setCity(updatedPerson.getCity());
					existingPerson.setZip(updatedPerson.getZip());
					existingPerson.setPhone(updatedPerson.getPhone());
					existingPerson.setEmail(updatedPerson.getEmail());
					return existingPerson;
				}).orElseThrow(
						() -> new NullPointerException("Failed to update person,the id: " + id + Constants.NOT_FOUND));

		log.info("Person updated successfully for: {}", updatedPerson);
		return existingPersonUpdated;
	}

	public boolean deleteOnePersonById(String id) throws NullPointerException {
		log.debug("Deleting person for {}", id);

		boolean result = false;
		result = persons.removeIf(person -> person.getId().equals(id));
		if (!result) {
			log.error("Failed to delete person for {}", id);
		} else {
			log.info("Person deleted successfully for {}", id);
		}

		return result;
	}

	public Person getOnePersonById(String id) {
		log.debug("Retrieving  one person for id {}", id);

		Person personFoundById = new Person();

		personFoundById = persons.stream().filter(person -> person.getId().equals(id)).findFirst()
				.map(existingPerson -> {
					return existingPerson;
				}).orElse(null);

		if (personFoundById == null) {
			throw new NullPointerException("Person for id: " + id + Constants.NOT_FOUND);
		}

		log.info("Person retrieved successfully for id : {}", id);
		return personFoundById;

	}

	public List<Person> getPersonsByLastName(String lastName) {
		log.debug("Retrieving  person(s)  for last name {}", lastName);

		List<Person> personsFoundByLastName = new ArrayList<>();
		try {
			Iterator<Person> itrPersons = persons.listIterator();
			while (itrPersons.hasNext()) {
				Person itrPerson = itrPersons.next();

				if (itrPerson.getLastName().equals(lastName)) {
					personsFoundByLastName.add(itrPerson);
				}
			}

			if (personsFoundByLastName.isEmpty()) {
				log.error("Failed to retrieve person  for last name {}", lastName);
				throw new NullPointerException("Person(s)  for lastName: " + lastName + Constants.NOT_FOUND);
			} else {
				log.info("Person retrieved  successfully for last name  {}", lastName);
			}

		} catch (NullPointerException e) {
			log.error(e.getMessage());
		}

		log.info("List of persons retrieved by last name successfully : {}", personsFoundByLastName);
		return personsFoundByLastName;
	}

	public List<Person> getPersonsByAddress(String address) {
		log.debug("Retrieving  person(s)  for address {}", address);

		List<Person> personsFoundByAddress = new ArrayList<>();
		try {
			Iterator<Person> itrPersons = persons.listIterator();
			while (itrPersons.hasNext()) {
				Person itrPerson = itrPersons.next();
				if (itrPerson.getAddress().equals(address)) {
					personsFoundByAddress.add(itrPerson);
				}
			}

			if (personsFoundByAddress.isEmpty()) {
				log.error("Failed to retrieve person  for address {}", address);
				throw new NullPointerException("Person(s) by address: " + address + Constants.NOT_FOUND);
			} else {
				log.info("Person retrieved  successfully for address {}", address);
			}
		} catch (NullPointerException e) {
			log.error(e.getMessage());
		}

		log.info("List of persons retrieved by address successfully : {}", personsFoundByAddress);
		return personsFoundByAddress;
	}

	public List<Person> getPersonsByCity(String city) throws NullPointerException {
		log.debug("Retrieving  person(s) for city {}", city);

		List<Person> personsFoundByCity = new ArrayList<>();
		try {
			Iterator<Person> itrPersons = persons.listIterator();
			while (itrPersons.hasNext()) {
				Person itrPerson = itrPersons.next();
				if (itrPerson.getCity().equals(city)) {
					personsFoundByCity.add(itrPerson);
				}
			}

			if (personsFoundByCity.isEmpty()) {
				log.error("Failed to retrieve person for city {}", city);
				throw new NullPointerException("Person(s)  by city :" + city + Constants.NOT_FOUND);
			} else {
				log.info("Person retrieved successfully for city {}", city);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return personsFoundByCity;
	}

	public List<Person> getAllPersons() throws NullPointerException {
		log.debug("Retrieving all persons");

		if (persons.isEmpty()) {
			log.error("Failed to retrieve all  persons");
			throw new NullPointerException("None person registered!");
		} else {
			log.info("All persons retrieved successfully: {}", persons);
		}

		return persons;
	}
}
