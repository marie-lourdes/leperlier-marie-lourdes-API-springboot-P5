package com.safetynet.api.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import com.safetynet.api.service.dataservice.PersonService;
import com.safetynet.api.utils.IResponseHTTPEmpty;

import jakarta.validation.Valid;

@RestController
public class PersonController implements IResponseHTTPEmpty {
	private static final Logger log = LogManager.getLogger(PersonController.class);
	@Autowired
	private PersonService personService;

	@PostMapping("/person/")
	@ResponseBody
	public ResponseEntity<Person> createPerson(@Valid @RequestBody Person person) {
		Person personCreated = new Person();
		personCreated = personService.addPerson(person);
		return ResponseEntity.status(HttpStatus.CREATED).body(personCreated);
	}

	@PutMapping("/person/{id}")
	@ResponseBody
	public ResponseEntity<?> updateOnePersonById(@Valid @RequestBody Person person, @PathVariable String id) {
		Person personFoundById;

		try {
			personFoundById = personService.updatePerson(id, person);
			return ResponseEntity.status(HttpStatus.CREATED).body(personFoundById);
		} catch (NullPointerException e) {
			// e.printStackTrace();
			log.error(e.getMessage());
			return returnResponseEntityEmptyAndCode404();
		}
	}

	@DeleteMapping("/person/{id}")
	public ResponseEntity<Long> deleteOnePersonById(@PathVariable String id) {

		try {
			personService.deleteOnePersonById(id);
			return new ResponseEntity<Long>(HttpStatus.NO_CONTENT);
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return new ResponseEntity<Long>(HttpStatus.NOT_FOUND);
		}
		
	}

	@GetMapping("/person")
	@ResponseBody
	public ResponseEntity<?> getOnePerson(@RequestParam String id) {
	
		//personFoundById = personService.getOnePersonById(id);
		try {
			Person personFoundById= new Person();
			personFoundById = personService.getOnePersonById(id);
			if(personFoundById==null) {
				throw new NullPointerException("Person not found for id: " + id);
			}
			return ResponseEntity.status(HttpStatus.OK).body(personFoundById);
		
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return returnResponseEntityEmptyAndCode404();
		}
		
	/*	if(personFoundById==null) {
			//log.error(e.getMessage());
			return returnResponseEntityEmptyAndCode404();
		}else {
			return ResponseEntity.status(HttpStatus.OK).body(personFoundById);
		}*/
	}

/*	@GetMapping("/person/")
	@ResponseBody
	public ResponseEntity<?> getAllPersons() {
		List<Person> allPersons;

		try {
			allPersons = personService.getAllPersons();
			return ResponseEntity.status(HttpStatus.OK).body(allPersons);
		} catch (NullPointerException e) {
			log.error(e.getMessage());
			return returnResponseEntityEmptyAndCode404();
		}
	}*/
}