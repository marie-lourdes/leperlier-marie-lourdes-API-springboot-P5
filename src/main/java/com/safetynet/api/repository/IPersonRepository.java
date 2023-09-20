package com.safetynet.api.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.safetynet.api.model.Person;
@Repository
public interface IPersonRepository extends CrudRepository<Person, Long>{
 /* default  Person findOnePerson(Iterable<Person> persons) {
	  // return 
   };*/

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete



}