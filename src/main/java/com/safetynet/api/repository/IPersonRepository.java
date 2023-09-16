package com.safetynet.api.repository;
import org.springframework.data.repository.CrudRepository;

import com.safetynet.api.model.PersonEntity;
public interface IPersonRepository extends CrudRepository<PersonEntity, Integer>{




// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete



}

