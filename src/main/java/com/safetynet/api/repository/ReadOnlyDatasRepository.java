package com.safetynet.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;

@NoRepositoryBean//more generic ,to reuse with other data of BDD
public interface ReadOnlyDatasRepository<T,ID> extends Repository <T,ID> {
	 Optional<T> findById(ID id);
	 List<T> findAll();
	 T findByName(T entity) ;
}
