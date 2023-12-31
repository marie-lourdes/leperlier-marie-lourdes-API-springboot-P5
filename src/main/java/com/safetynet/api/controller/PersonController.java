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
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.api.model.Person;
import com.safetynet.api.service.dataservice.PersonService;
import com.safetynet.api.utils.ConstantsRequestResponseHttp;
import com.safetynet.api.utils.IResponseHTTPEmpty400;
import com.safetynet.api.utils.IResponseHTTPEmpty404;

import jakarta.validation.Valid;

@RestController
public class PersonController implements IResponseHTTPEmpty404<Person>, IResponseHTTPEmpty400<Person> {
	private static final Logger log = LogManager.getLogger(PersonController.class);

	@Autowired
	private PersonService personService;

	@PostMapping("/person")
	public ResponseEntity<Person> createPerson(@Valid @RequestBody Person person) {
		log.info(ConstantsRequestResponseHttp.REQUEST_POST_PERSON, person);

		Person personCreated = new Person();
		try {
			personCreated = personService.addPerson(person);
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());

			ResponseEntity<Person> responseEntityNoValid = this.returnResponseEntityEmptyAndCode400();
			log.error(ConstantsRequestResponseHttp.RESPONSE_POST_PERSON, responseEntityNoValid);
			return responseEntityNoValid;
		}
		
		ResponseEntity<Person> responseEntityValid = ResponseEntity.status(HttpStatus.CREATED).body(personCreated);
		log.info(ConstantsRequestResponseHttp.RESPONSE_POST_PERSON, responseEntityValid);
		return responseEntityValid;
	}

	@PutMapping("/person")
	public ResponseEntity<Person> updateOnePersonById(@Valid @RequestBody Person person, @RequestParam String id) {
		log.info(ConstantsRequestResponseHttp.REQUEST_PUT_PERSON, person);

		Person personFoundById = new Person();
		try {
			personFoundById = personService.updateOnePersonById(id, person);
		} catch (NullPointerException e) {
			log.error(e.getMessage());

			ResponseEntity<Person> responseEntityNoValid = this.returnResponseEntityEmptyAndCode404();
			log.error(ConstantsRequestResponseHttp.RESPONSE_PUT_PERSON, responseEntityNoValid);
			return responseEntityNoValid;
		}
		
		ResponseEntity<Person> responseEntityValid = ResponseEntity.status(HttpStatus.OK).body(personFoundById);
		log.info(ConstantsRequestResponseHttp.RESPONSE_PUT_PERSON, responseEntityValid);
		return ResponseEntity.status(HttpStatus.OK).body(personFoundById);
	}

	@DeleteMapping("/person")
	public ResponseEntity<Long> deleteOnePersonById(@RequestParam String id) {
		log.info(ConstantsRequestResponseHttp.REQUEST_DELETE_PERSON, id);

		try {
			boolean personIsRemoved = personService.deleteOnePersonById(id);
			if (!personIsRemoved) {
				throw new NullPointerException(" Person " + id + "  to delete not found");
			}
		} catch (NullPointerException e) {
			log.error(e.getMessage());

			ResponseEntity<Long> responseEntityNoValid = new ResponseEntity<Long>(HttpStatus.NOT_FOUND);
			log.error(ConstantsRequestResponseHttp.RESPONSE_DELETE_PERSON, responseEntityNoValid);
			return responseEntityNoValid;
		}
		
		ResponseEntity<Long> responseEntityValid = new ResponseEntity<Long>(HttpStatus.NO_CONTENT);
		log.info(ConstantsRequestResponseHttp.RESPONSE_DELETE_PERSON, responseEntityValid);
		return responseEntityValid;
	}

	@Override
	public ResponseEntity<Person> returnResponseEntityEmptyAndCode404() {
		return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
	}

	@Override
	public ResponseEntity<Person> returnResponseEntityEmptyAndCode400() {
		return new ResponseEntity<Person>(HttpStatus.BAD_REQUEST);
	}
}
