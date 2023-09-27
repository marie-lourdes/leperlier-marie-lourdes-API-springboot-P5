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

<<<<<<< Updated upstream
<<<<<<< Updated upstream
import jakarta.validation.Valid;

@RestController
=======
@RestController // This means that this class is a Controller
>>>>>>> Stashed changes
=======
@RestController // This means that this class is a Controller
>>>>>>> Stashed changes
//@RequestMapping(path = "/api) // This means URL's start with /demo (after Application path)
public class PersonController {

	@Autowired
	private PersonService personService;

<<<<<<< Updated upstream
<<<<<<< Updated upstream
	@PostMapping("/person")
	public ResponseEntity<Person> createPerson(@Valid @RequestBody Person personCreated) {
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
		personService.savePerson(person);
		return ResponseEntity.status(HttpStatus.CREATED).body(person);

=======
=======
>>>>>>> Stashed changes
	@PostMapping(value = "/person") // Map ONLY POST Requests
	public Person addNewUser(@RequestParam String firstName, @RequestParam String lastName,
			@RequestParam String address, @RequestParam String city, @RequestParam int zip, @RequestParam String phone,
			@RequestParam String email) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request,
		// @RequestParam param a indiquer dans postman et les test WebMvcTest
//try {
		Person n = new Person();
		n.setFirstName(firstName);
		n.setLastName(lastName);
		n.setAddress(address);
		n.setCity(city);
		n.setZip(zip);
		n.setPhone(phone);
		n.setEmail(email);
		personService.saveOnePerson(n);
		return n;
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
//}
		/*
		 * catch(jakarta.validation.ConstraintViolationException e) { return
		 * e.getMessage(); }
		 */
<<<<<<< Updated upstream
<<<<<<< Updated upstream
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

	// the id, first and last name cannot be modified
	@PutMapping("/person/{id}")
	public ResponseEntity<Optional<Person>> updateOnePersonById(@RequestBody Person personModified,
			@PathVariable Long id) {
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
		return ResponseEntity.status(HttpStatus.CREATED).body(personFoundById);
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
=======
=======
>>>>>>> Stashed changes

	}
// à implementer dans un service à part?
	@GetMapping("/person")
	public @ResponseBody List<Person> getAllPersons() {
		// This returns a JSON or XML with the users
		return personService.getAllPersons();
	}
	// à implementer dans un service à part?	
	@GetMapping("/person/{id}")
	public Optional<Person>getOnePerson(@PathVariable Long id) {
		// This returns a JSON or XML with the users
		return personService.getOnePersonById(id);
	}
	
	@PutMapping("/person/{id}")
	public Optional<Person> updateOnePersonById(@PathVariable Long id,@RequestBody Person personModified) {
		 Optional<Person>	personFoundById = Optional.ofNullable(personService.getOnePersonById(id).orElseThrow(() -> new NullPointerException(" an error has occured,this person"+id+"don't exist, try again ")));
		 if(id ==personFoundById.get().getId() ) {
		 personFoundById.get().setFirstName(personModified.getFirstName()); 
		 personFoundById.get().setLastName(personModified.getLastName()); 
		 personFoundById.get().setAddress(personModified.getAddress()); 
		 personFoundById.get().setZip(personModified.getZip()); 
		 personFoundById.get().setCity(personModified.getCity());
		 personFoundById.get().setPhone(personModified.getPhone()); 
		 personService.saveOnePerson( personFoundById.get()); 
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

<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
}
