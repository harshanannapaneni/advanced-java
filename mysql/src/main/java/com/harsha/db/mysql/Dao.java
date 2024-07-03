package com.harsha.db.mysql;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
	void save(T t);
	Optional<T> findOne(int id);
	void update(T t);
	void delete(T t);
	List<User> getAll();
}
