package com.safetynet.api.controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.json.JsonArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.api.model.Person;
import com.safetynet.api.service.ReadPersonDataFromFileImpl;
import com.safetynet.api.service.dataservice.PersonService;

import jakarta.validation.Valid;

@RestController
//@RequestMapping(path = "/api) // This means URL's start with /demo (after Application path)
public class PersonController {

	@Autowired
	private PersonService personService;

	@PostMapping("/person")
	public ResponseEntity<Person> createPerson(@Valid @RequestBody Person person) {
//try {	
		personService.savePerson(person);
		System.out.println(person);
		return ResponseEntity.status(HttpStatus.CREATED).body(person);

//}
		/*
		 * catch(jakarta.validation.ConstraintViolationException e) { return
		 * e.getMessage(); }
		 */
	}

	@GetMapping("/person")
	public @ResponseBody JsonArray getAllPersons() throws FileNotFoundException {
		JsonArray persons = new ReadPersonDataFromFileImpl().readFile();
		List<Person> allPersons = new ArrayList<Person>();	
		try {
			allPersons = personService.getAllPersons();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return persons;
	}

	@GetMapping("/person/{id}")
	public Optional<Person> getOnePerson(@PathVariable Long id) {
		return personService.getOnePersonById(id);
	}

	// the id, first and last name cannot be modified
	@PutMapping("/person/{id}")
	public ResponseEntity<Optional<Person>> updateOnePersonById(@RequestBody Person person, @PathVariable Long id) {
		Optional<Person> personFoundById = personService.getOnePersonById(id);

		if (id.toString().equals(personFoundById.get().getId().toString())) {
			personFoundById.get().setAddress(person.getAddress());
			personFoundById.get().setZip(person.getZip());
			personFoundById.get().setCity(person.getCity());
			personFoundById.get().setPhone(person.getPhone());
			personFoundById.get().setEmail(person.getEmail());

			personService.savePerson(personFoundById.get());
			System.out.println(personFoundById);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(personFoundById);
	}

	@DeleteMapping("/person/")
	public ResponseEntity<Long> deleteOnePersonByName(@RequestParam String firstName, @RequestParam String lastName) {
		List<Person> persons = personService.getAllPersons();

		persons.forEach(elem -> {
			String firstNamePerson = elem.getFirstName();
			String lastNamePerson = elem.getLastName();
			if (firstNamePerson.contains(firstName) && lastNamePerson.contains(lastName)) {
				System.out.println("element to remove" + elem);
				personService.deleteOnePersonByName(elem);
			}
		});
		return new ResponseEntity<Long>(HttpStatus.NO_CONTENT);
	}
}