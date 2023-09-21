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

@RestController 
//@RequestMapping(path = "/api) // This means URL's start with /demo (after Application path)
public class PersonController {

	@Autowired
	private PersonService personService;

	@PostMapping(value = "/person") 
	public Person createPerson(@RequestParam String firstName, @RequestParam String lastName,
			@RequestParam String address, @RequestParam String city, @RequestParam int zip, @RequestParam String phone,
			@RequestParam String email) {
//try {
		Person n = new Person();
		n.setFirstName(firstName);
		n.setLastName(lastName);
		n.setAddress(address);
		n.setCity(city);
		n.setZip(zip);
		n.setPhone(phone);
		n.setEmail(email);
		personService.savePerson(n);
		return n;
//}
		/*
		 * catch(jakarta.validation.ConstraintViolationException e) { return
		 * e.getMessage(); }
		 */

	}

	@GetMapping("/person")
	public @ResponseBody List<Person> getAllPersons() {
		// This returns a JSON or XML with the users
		return personService.getAllPersons();
	}
	
	@GetMapping("/person/{id}")
	public Optional<Person>getOnePerson(@PathVariable Long id) {
		return personService.getOnePersonById(id);
	}
	
	@PutMapping("/person/{id}")
	public Optional<Person> updateOnePersonById(@PathVariable Long id,@RequestBody Person personModified) {
		 Optional<Person>	personFoundById = Optional.ofNullable(personService.getOnePersonById(id).orElseThrow(() -> new NullPointerException(" an error has occured,this person"+id+"don't exist, try again ")));
		 if(id ==personFoundById.get().getId() ) {
		 personFoundById.get().setAddress(personModified.getAddress()); 
		 personFoundById.get().setZip(personModified.getZip()); 
		 personFoundById.get().setCity(personModified.getCity());
		 personFoundById.get().setPhone(personModified.getPhone()); 
		 personService.savePerson( personFoundById.get()); 
		 }
		 return personFoundById; 
	}
	
	@DeleteMapping("/person")
	public 	ResponseEntity<Long> deleteOnePersonByName (@RequestParam String firstName, @RequestParam String lastName) {
		List <Person> persons =(List<Person>) personService.getAllPersons();
		persons.forEach(elem->{ 			
			    String firstNamePerson =elem.getFirstName(); 
			    String lastNamePerson =elem.getLastName()	;
		    if(firstNamePerson.contains(firstName) &&  lastNamePerson.contains(lastName)  ) {
		    	personService.deleteOnePersonByName(elem);     
		    }	
			});
		
		return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
	}

}