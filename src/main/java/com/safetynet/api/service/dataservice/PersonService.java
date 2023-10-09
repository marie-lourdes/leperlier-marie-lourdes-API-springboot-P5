package com.safetynet.api.service.dataservice;

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
	 
    public Person addPerson(Person person){
    	person.setId(
    			person.getFirstName() + " " +person.getLastName() ); 
       	persons.add(person);
        return person;
    }
    
    public Person updatePerson(String id,Person updatedPerson) {
        return persons.stream()
                .filter(person -> person.getId().equals(id) )
                .findFirst()
                .map(existingPerson-> {
                   existingPerson.setAddress(updatedPerson.getAddress());
                   existingPerson.setCity(updatedPerson.getCity());
                   existingPerson.setZip(updatedPerson.getZip());
                   existingPerson.setPhone(updatedPerson.getPhone());
                   existingPerson.setEmail(updatedPerson.getEmail());
                    return existingPerson;
                })
                .orElse(null);
    }
    
    public boolean deleteOnePersonById(String id) {
        //   log.debug("Deleting medical record for {} {}", firstName, lastName);
           boolean result = persons.removeIf(person -> person.getId().equals(id));
           if (result) {
            //   log.info("Person deleted successfully for {} {}", id);
           } else {
              // log.error("Failed to delete person for {} {}", id);
           }
           return result;
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