package com.safetynet.api.repository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.Person;

public interface IPersonREADONLYRepository extends IReadOnlyDatasRepository<Person,String>{
	@Override
	List<Person> findAll() throws IOException;
	
	 List<Optional<Person>> findByFirstNameAndLastName(String firstName, String lastName );
}
