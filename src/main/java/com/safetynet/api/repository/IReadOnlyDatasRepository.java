package com.safetynet.api.repository;

import java.io.IOException;
import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

@NoRepositoryBean
public interface IReadOnlyDatasRepository<T,ID> extends Repository <T,ID> {
	
}
