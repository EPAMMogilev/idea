package com.epam.idea.core.service;

import java.io.Serializable;
import java.util.List;

public interface BaseService<T, ID extends Serializable> {
	void delete(T deleted);

	List<T> findAll();

	T findOne(ID id);

	T save(T persisted);
}
