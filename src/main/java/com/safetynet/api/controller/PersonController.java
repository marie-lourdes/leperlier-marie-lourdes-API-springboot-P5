package com.safetynet.api.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.api.model.Person;
import com.safetynet.api.service.dataservice.PersonService;

@RestController // This means that this class is a Controller
//@RequestMapping(path = "/api) // This means URL's start with /demo (after Application path)
public class PersonController {

	@Autowired
	private PersonService personService;

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
	
	@DeleteMapping("/person")
	public String deleteOnePersonByName (@RequestParam String firstName, @RequestParam String lastName) {
		List <Person> persons =(List<Person>) personService.getAllPersons();
		Person element ;
		persons.forEach(elem->{ 
			
			    String firstNamePerson =elem.getFirstName(); 
			    String lastNamePerson =elem.getLastName()	;
		    if(firstNamePerson.contains(firstName) &&  lastNamePerson.contains(lastName)  ) {
		    	personService.deleteOnePersonByName((long) elem.getId()); 
		       // element = elem;
		    }
		
			});
		return " sucessfull request, person deleted";
	}

}
