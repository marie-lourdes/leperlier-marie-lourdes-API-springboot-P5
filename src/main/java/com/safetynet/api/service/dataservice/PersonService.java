package com.safetynet.api.service.dataservice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.safetynet.api.model.Person;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonService {
	 private final List<Person> persons = new ArrayList<>();
	

    public Person addMedicalRecord(Person person){
    	person.setId(
    			person.getFirstName() + " " +person.getLastName() ); 
       	persons.add(person);
        return person;
    }
    
    public Optional<Person> getOneMedicalRecordById(String id) {
  	  return persons.stream()
                .filter(person -> person.getId().equals(id))
                .findFirst()
                .map(existingPerson -> { 
              	  return existingPerson;
                } );
   }
	 
  public List<Person> getAllMedicalRecords() {
  	System.out.println("Retrieving all persons"+persons);
      return persons;
  }
  /*  public Person updatePerson(String firstName, String lastName,Person updatedPerson) {
       // log.debug("Updating medical record for: {}", firstName + " " + lastName);
        return persons.stream()
                .filter(person -> person.getFirstName().equals(firstName) && person.getLastName().equals(lastName))
                .findFirst()
                .map(existingPerson-> {
                   existingPerson.setBirthdate(updatedPerson.getBirthdate());
                   existingPerson.setMedications(updatedPerson.getMedications());
                   existingPerson.setAllergies(updatedPerson.getAllergies());
                    return existingPerson;
                })
                .orElse(null);
    }*/

/*	public Optional<Person> getOnePersonById(String id) {
		Optional<Person> personFoundById = Optional
				.ofNullable(personRepositoryFile.findById(id)).orElseThrow(() -> new NullPointerException(
						" an error has occured,this person" + id + "doesn't exist, try again "));
		return personFoundById;
	}*/
	
/*	public Optional<Person> getOnePersonById(String id) {
		return personRepositoryFile.findById(id);
	}*/
	
/*	public List<Optional<Person>> getOnePersonByFullName(String firstName, String lastName) {
		return personRepositoryFile.findByFirstNameAndLastName(firstName,  lastName );
	}
/*	 
	public Person savePerson(Person person) {
		return personRepository.save(person);
	}

	public List<Person> updateOnePersonById(List<Person> person, Long id) {
		return (List<Person>) personRepository.saveAll(person);
	}

	public void deleteOnePersonByName(Person person) {
		personRepository.delete(person);
	}*/

}