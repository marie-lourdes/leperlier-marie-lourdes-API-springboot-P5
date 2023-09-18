package com.safetynet.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.api.model.Person;
import com.safetynet.api.service.dataservice.PersonService;

@RestController // This means that this class is a Controller
@RequestMapping(path="/demo") // This means URL's start with /demo (after Application path)
public class PersonController {

	  @Autowired
	    private  PersonService personService;

	  @PostMapping(value="/add") // Map ONLY POST Requests
	  public @ResponseBody Person addNewUser (
			  @RequestParam String id,
			  @RequestParam String name,
			  @RequestParam String name2, 
			  @RequestParam String address,
			  @RequestParam String city,
			  @RequestParam int zip,
			  @RequestParam String phone,
			  @RequestParam String email 
	    ) {
	    // @ResponseBody means the returned String is the response, not a view name
	    // @RequestParam means it is a parameter from the GET or POST request

	   Person n = new Person();
	    n.setId(id);
	    n.setFirstName(name);
	    n.setLastName(name2);
	    n.setAddress(address);
	    n.setCity(city);
	    n.setZip(zip);
	    n.setPhone(phone);
	    n.setEmail(email);
	    personService.saveEmployee(n);
	    return n;
	  }

	  @GetMapping("/all")
	  public @ResponseBody Iterable<Person> getAllUsers() {
	    // This returns a JSON or XML with the users
	    return     personService.getEmployees();
	  }

}
