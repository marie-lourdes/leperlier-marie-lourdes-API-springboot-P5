package com.safetynet.api.repository;

import java.io.IOException;
import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

@NoRepositoryBean//more generic ,to reuse with other data of BDD
public interface IReadOnlyDatasRepository<T,ID> extends Repository <T,ID> {

	 List<T> findAll() throws IOException;
	
}
