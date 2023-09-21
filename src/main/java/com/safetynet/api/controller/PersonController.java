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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.api.model.Person;
import com.safetynet.api.service.dataservice.PersonService;

import jakarta.validation.Valid;

@RestController
//@RequestMapping(path = "/api) // This means URL's start with /demo (after Application path)
public class PersonController {

	@Autowired
	private PersonService personService;

	@PostMapping(value = "/person")
	public Person createPerson( @RequestBody  Person personCreated) {
//try {
		Person n = new Person();
		n.setFirstName(personCreated.getFirstName());
		n.setLastName(personCreated.getLastName());
		n.setAddress(personCreated.getAddress());
		n.setCity(personCreated.getCity());
		n.setZip(personCreated.getZip());
		n.setPhone(personCreated.getPhone());
		n.setEmail(personCreated.getEmail());
	return	personService.savePerson(n);
		
//}
		/*
		 * catch(jakarta.validation.ConstraintViolationException e) { return
		 * e.getMessage(); }
		 */

	}

	@GetMapping("/person")
	public @ResponseBody List<Person> getAllPersons() {
		List<Person> allPersons =null;
		try {
			allPersons  = personService.getAllPersons();
		}catch (NullPointerException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return allPersons;
	}

	@GetMapping("/person/{id}")
	public Optional<Person> getOnePerson(@PathVariable Long id) {
		Optional<Person> personFoundById  = Optional.ofNullable(personService.getOnePersonById(id).orElseThrow(
				() -> new NullPointerException(" an error has occured,this person" + id + "don't exist, try again ")));
		return personFoundById;
	}

	// l id , le nom et pénom ne sont pas modifiable
	@PutMapping("/person/{id}")
	public Optional<Person> updateOnePersonById(@PathVariable Long id, @RequestBody Person personModified) {
		Optional<Person> personFoundById = Optional.ofNullable(personService.getOnePersonById(id).orElseThrow(
				() -> new NullPointerException(" an error has occured,this person" + id + "don't exist, try again ")));
		if (id == personFoundById.get().getId()) {
			personFoundById.get().setAddress(personModified.getAddress());
			personFoundById.get().setZip(personModified.getZip());
			personFoundById.get().setCity(personModified.getCity());
			personFoundById.get().setPhone(personModified.getPhone());
			personService.savePerson(personFoundById.get());
		}
		
		return personFoundById;
	}

	@DeleteMapping("/person")
	public ResponseEntity<Long> deleteOnePersonByName(@RequestParam String firstName, @RequestParam String lastName) {		 
		 List<Person> persons = (List<Person>) personService.getAllPersons();
			persons.forEach(elem -> {
				String firstNamePerson = elem.getFirstName();
				String lastNamePerson = elem.getLastName();
				if (firstNamePerson.contains(firstName) && lastNamePerson.contains(lastName)) {
					personService.deleteOnePersonByName(elem);
				
				}	
	});
			return new ResponseEntity<Long>(HttpStatus.NO_CONTENT);
	}
}		
