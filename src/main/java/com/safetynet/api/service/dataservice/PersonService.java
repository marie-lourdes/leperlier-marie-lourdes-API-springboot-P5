package com.safetynet.api.service.dataservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.api.model.Person;
import com.safetynet.api.repository.IPersonRepository;

@Service
public class PersonService {
	 @Autowired
	    private IPersonRepository personRepository;

	 
	    public Iterable<Person> getEmployees() {
	        return personRepository.findAll();
	    }

	    
	    public Person savePerson(Person person) {
	        Person savedPerson = personRepository.save(person);
	        return savedPerson;
	    }
}
