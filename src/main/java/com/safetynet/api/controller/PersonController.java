package com.safetynet.api.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.api.model.Person;
import com.safetynet.api.service.dataservice.PersonService;
import com.safetynet.api.utils.IResponseHTTPEmpty;

import jakarta.validation.Valid;

@RestController
public class PersonController implements IResponseHTTPEmpty {
	private static final Logger log = LogManager.getLogger(PersonController.class);
	@Autowired
	private PersonService personService;

	@PostMapping("/person")
	@ResponseBody
	public ResponseEntity<Person> createPerson(@Valid @RequestBody Person person) {
		Person personCreated = new Person();
		personCreated = personService.addPerson(person);
		return ResponseEntity.status(HttpStatus.CREATED).body(personCreated);
	}

	@PutMapping("/person")
	@ResponseBody
	public ResponseEntity<Object>updateOnePersonById(@Valid @RequestBody Person person, @RequestParam String id) {
		Person personFoundById = new Person();
		try {
			personFoundById = personService.updateOnePersonById(id, person);
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return returnResponseEntityEmptyAndCode404();
		}
		return ResponseEntity.status(HttpStatus.OK).body(personFoundById);
	}

	@DeleteMapping("/person")
	public ResponseEntity<Long> deleteOnePersonById(@RequestParam String id) {
		try {
			boolean personIsRemoved = personService.deleteOnePersonById(id);
			if (!personIsRemoved) {
				throw new NullPointerException(" Person " + id + "  to delete not found");
			}
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return new ResponseEntity<Long>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Long>(HttpStatus.NO_CONTENT);
	}
}

