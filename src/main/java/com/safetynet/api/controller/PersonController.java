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

	@PostMapping("/person")
	public Person createPerson(@Valid @RequestBody Person personCreated) {
//try {
		Person person = new Person();
		person.setFirstName(personCreated.getFirstName());
		person.setLastName(personCreated.getLastName());
		person.setAddress(personCreated.getAddress());
		person.setCity(personCreated.getCity());
		person.setZip(personCreated.getZip());
		person.setPhone(personCreated.getPhone());
		person.setEmail(personCreated.getEmail());
		System.out.println(person);
		return personService.savePerson(person);

//}
		/*
		 * catch(jakarta.validation.ConstraintViolationException e) { return
		 * e.getMessage(); }
		 */
	}

	@GetMapping("/person")
	public @ResponseBody List<Person> getAllPersons() {
		List<Person> allPersons = null;
		try {
			allPersons = personService.getAllPersons();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allPersons;
	}

	@GetMapping("/person/{id}")
	public Optional<Person> getOnePerson(@PathVariable Long id) {
		Optional<Person> personFoundById = Optional
				.ofNullable(personService.getOnePersonById(id).orElseThrow(() -> new NullPointerException(
						" an error has occured,this person" + id + "doesn't exist, try again ")));
		return personFoundById;
	}

	// l id , le nom et prénom ne sont pas modifiables

	@PutMapping("/person/{id}")
	public Optional<Person> updateOnePersonById(@RequestBody Person personModified, @PathVariable Long id) {
		Optional<Person> personFoundById = this.getOnePerson(id);

		if (id == personFoundById.get().getId()) {
			if (personModified.getAddress() != null)
				personFoundById.get().setAddress(personModified.getAddress());
			if (personModified.getZip() != null)
				personFoundById.get().setZip(personModified.getZip());
			if (personModified.getCity() != null)
				personFoundById.get().setCity(personModified.getCity());
			if (personModified.getPhone() != null)
				personFoundById.get().setPhone(personModified.getPhone());
			if (personModified.getEmail() != null)
				personFoundById.get().setEmail(personModified.getEmail());
			personService.savePerson(personFoundById.get());
		}
		return personFoundById;
	}

	@DeleteMapping("/person/")
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
