package com.safetynet.api.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.safetynet.api.model.Person;
@Repository
public interface IPersonCRUDRepository extends CrudRepository<Person, Long>{
	 List<Optional<Person>>findById(String id);
}

