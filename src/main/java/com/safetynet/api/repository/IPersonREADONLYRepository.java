package com.safetynet.api.repository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.safetynet.api.model.Person;

public interface IPersonREADONLYRepository extends IReadOnlyDatasRepository<Person,String>{
	@Override
	List<Person> findAll() throws IOException;	
	 Optional<Person>findById(String id);
	 List<Optional<Person>> findByFirstNameAndLastName(String firstName, String lastName );
}
