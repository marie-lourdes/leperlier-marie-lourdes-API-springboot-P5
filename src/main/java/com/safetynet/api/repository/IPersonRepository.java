package com.safetynet.api.repository;

import java.io.IOException;
import java.util.List;

import com.safetynet.api.model.Person;

public interface IPersonRepository extends ICRUDDatasRepository<Person, String> {
	@Override
	List<Person> findAll() throws IOException;
}
