package com.safetynet.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.api.model.Person;
import com.safetynet.api.service.IResponseHTTPEmpty;
import com.safetynet.api.service.dataservice.PersonService;

import jakarta.validation.Valid;

@RestController
public class PersonController implements  IResponseHTTPEmpty   {
	@Autowired
	private PersonService personService;

	@PostMapping("/person/")
	public ResponseEntity<Person> createPerson(@Valid @RequestBody Person person) {
		Person personCreated = personService.addPerson(person);
		System.out.println(person);
		return ResponseEntity.status(HttpStatus.CREATED).body(personCreated);
	}

	@PutMapping("/person/{id}")
	public ResponseEntity<Person> updateOnePersonById(@RequestBody Person person, @PathVariable String id) {
		Person personFoundById = personService.updatePerson(id, person);
		if (personFoundById != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(personFoundById);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(personFoundById);// ajouter objet json vide
		}

	}

	@DeleteMapping("/person/{id}")
	public ResponseEntity<Long> deleteOnePersonById(@PathVariable String id) {
		boolean isPersonRemoved = personService.deleteOnePersonById(id);
		return isPersonRemoved ? new ResponseEntity<Long>(HttpStatus.NO_CONTENT)
				: new ResponseEntity<Long>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/person/{id}")
	public ResponseEntity<Optional<Person>> getOnePerson(@PathVariable String id) {
		Optional<Person> personFoundById = personService.getOnePersonById(id);
		if (personFoundById != null) {
			return ResponseEntity.status(HttpStatus.OK).body(personFoundById);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(personFoundById);// ajouter objet json vide
		}
	}

	@GetMapping("/person/")
	public @ResponseBody ResponseEntity<List<Person>> getAllPersons() {
		List<Person> allPersons = personService.getAllPersons();
		if (allPersons != null) {
			return ResponseEntity.status(HttpStatus.OK).body(allPersons);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(allPersons);
		}
	}
}