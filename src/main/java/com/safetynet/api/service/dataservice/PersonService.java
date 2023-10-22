package com.safetynet.api.service.dataservice;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.safetynet.api.model.Person;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonService {
	private static final Logger log = LogManager.getLogger(PersonService.class);
	private final List<Person> persons = new ArrayList<>();

	public Person addPerson(Person person) {
		log.debug("Adding person: {}", person.getFirstName() + " " + person.getLastName());
		
		person.setId(person.getFirstName() + " " + person.getLastName());
		persons.add(person);
		

		log.info("Person added successfully: {}",
				person.getFirstName() + " " + person.getLastName());
		return person;
	}

	public Person updatePerson(String id, Person updatedPerson) throws NullPointerException {
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
				}).orElseThrow(() -> new NullPointerException(
						"Failed to update person, " +updatedPerson.getId() + " not found"));
		
		log.info("Person updated successfully for: {}", id);
		return existingPersonUpdated;
	}

	public boolean deleteOnePersonById(String id) throws NullPointerException {
		 log.debug("Deleting person for {}", id);
		boolean result = persons.removeIf(person -> person.getId().equals(id));

		if (!result) {
			 log.error("Failed to delete person for {}", id);
			throw new NullPointerException(" Person :"+ id +"  to delete not found");
		} else {
			 log.info("Person deleted successfully for {}", id);
		}
		return result;
	}

	public Person getOnePersonById(String id) throws NullPointerException {
		log.debug("Retrieving  one person for {}", id);
		
		Person personFoundById = new Person();
		personFoundById = persons.stream().filter(person -> person.getId().equals(id)).findFirst()
				.map(existingPerson -> {
					return existingPerson;
				}).orElseThrow(() -> new NullPointerException("Person not found by id for "+id));


		log.info("Person retrieved successfully for: {}", id);
		return personFoundById;
	}

	public List<Person> getPersonsLastName(String lastName) throws NullPointerException {
		log.debug("Retrieving  person(s)  by last name for {}",lastName);
		
		List<Person> personsFoundByLastName = new ArrayList<>();
		Iterator<Person> itrPersons = persons.listIterator();
		while (itrPersons.hasNext()) {
			Person itrPerson = itrPersons.next();

			if (itrPerson.getLastName().equals(lastName)) {
				personsFoundByLastName.add(itrPerson);
				 log.info("Person retrieved by last name successfully for {}", lastName);
			}else {
				log.error("Failed to retrieve person by last name for {}", lastName);
			}
		}

		if (personsFoundByLastName.isEmpty()) {
			throw new NullPointerException("Person(s) not found by lastName: " + lastName);
		}
		
		log.info("List of persons retrieved by last name successfully : {}", personsFoundByLastName);
		return personsFoundByLastName;
	}

	public List<Person> getPersonsByAddress(String address) throws NullPointerException {
		log.debug("Retrieving  person(s)  by address for {}", address);
		
		List<Person> personsFoundByAddress = new ArrayList<>();
		Iterator<Person> itrPersons = persons.listIterator();
		while (itrPersons.hasNext()) {
			Person itrPerson = itrPersons.next();
			if (itrPerson.getAddress().equals(address)) {
				 log.info("Person retrieved by address successfully for {}", address);
				personsFoundByAddress.add(itrPerson);
			}else {
				log.error("Failed to retrieve person by address for {}", address);
			}
		}
		
		if (personsFoundByAddress.isEmpty()) {
			throw new NullPointerException("Person(s) not found by address" + address);
		}
		
		log.info("List of persons retrieved by address successfully : {}", personsFoundByAddress);
		return personsFoundByAddress;
	}

	public List<Person> getPersonsByCity(String city) throws NullPointerException {
		log.debug("Retrieving  person(s)  by city for {}", city);
		
		List<Person> personsFoundByCity = new ArrayList<>();
		Iterator<Person> itrPersons = persons.listIterator();
		while (itrPersons.hasNext()) {
			Person itrPerson = itrPersons.next();
			if (itrPerson.getCity().equals(city)) {
				 log.info("Person retrieved by city successfully for {}", city);
				personsFoundByCity.add(itrPerson);
			}else {
				log.error("Failed to retrieve person by city for {}", city);
			}
		}
		
		if (personsFoundByCity.isEmpty()) {
			throw new NullPointerException("Person(s) not found by city" + city);
		}
		
		log.info("List of persons retrieved by city successfully : {}", personsFoundByCity);
		return personsFoundByCity;
	}

	public List<Person> getAllPersons() throws NullPointerException {
		 log.debug("Retrieving all persons");
		if (persons.isEmpty()) {
			throw new NullPointerException("None person registered!");
		}
		
		log.info("All medical records retrieved successfully: {}", persons);
		return persons;
	}
}