package com.epam.idea.core.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

@NoRepositoryBean
interface BaseRepository<T, ID extends Serializable> extends Repository<T, ID> {

	List<T> findAll();

	<S extends T> S save(S entity);

	Optional<T> findById(ID primaryKey);

	void delete(T entity);
}
