package com.safetynet.api.service.dataservice;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.api.model.Person;
import com.safetynet.api.repository.IPersonRepository;

@Service
public class PersonService {
	@Autowired
    private IPersonRepository personRepository;

    public List<Person> getAllPersons()   {
        return (List<Person>) personRepository.findAll();
    }

    public  Optional<Person> getOnePersonById(Long id) {
        return  personRepository.findById(id);
    }
    
    public Person savePerson(Person person) {
       return personRepository.save(person);       
    }
    
    public  Person updateOnePersonById(Person person, Long id) {	   
        return personRepository.save(person);       
     }
    
    public void deleteOnePersonByName( Person person) { 		
       personRepository.delete(person);
    }
	    	    
}