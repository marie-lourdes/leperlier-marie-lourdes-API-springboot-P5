package com.safetynet.api.repository;

import java.util.List;

import com.safetynet.api.model.Person;

public interface IPersonREADONLYRepository extends IReadOnlyDatasRepository<Person,Long>{
	List<Person> findAll();
}
