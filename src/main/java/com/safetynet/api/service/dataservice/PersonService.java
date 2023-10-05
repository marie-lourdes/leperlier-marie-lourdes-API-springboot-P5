package com.safetynet.api.service.dataservice;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.api.model.Person;
import com.safetynet.api.repository.IPersonCRUDRepository;
import com.safetynet.api.repository.PersonREADONLYRepositoryImpl;

@Service
public class PersonService {
	@Autowired
	private IPersonCRUDRepository personRepository;

	@Autowired
	private PersonREADONLYRepositoryImpl personRepositoryFile;

//--------------------repository avec source de donnéees BDD---------
	/*
	 * public List<Person> getAllPersons() { return (List<Person>)
	 * personRepository.findAll(); }
	 */

//--------------------repository avec source de donnéees fichier Json---------	
	public List<Person> getPersonsFromFile() throws IOException {
		return (List<Person>) personRepositoryFile.findAll();
	}

/*	public Optional<Person> getOnePersonById(String id) {
		Optional<Person> personFoundById = Optional
				.ofNullable(personRepositoryFile.findById(id)).orElseThrow(() -> new NullPointerException(
						" an error has occured,this person" + id + "doesn't exist, try again "));
		return personFoundById;
	}*/
	
	public List<Optional<Person>> getOnePersonById(String id) {
		return personRepositoryFile.findById(id);
	}
	
	public List<Optional<Person>> getOnePersonByFullName(String firstName, String lastName) {
		return personRepositoryFile.findByFirstNameAndLastName(firstName,  lastName );
	}
	 
	public Person savePerson(Person person) {
		return personRepository.save(person);
	}

	public List<Person> updateOnePersonById(List<Person> person, Long id) {
		return (List<Person>) personRepository.saveAll(person);
	}

	public void deleteOnePersonByName(Person person) {
		personRepository.delete(person);
	}

}