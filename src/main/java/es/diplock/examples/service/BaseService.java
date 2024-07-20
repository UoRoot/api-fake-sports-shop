package es.diplock.examples.service;

import java.util.List;

public interface BaseService<T, ID> {

    T findById(ID id);

    List<T> findAll();

    T save(T entity);

    T update(ID id, T entity);

    void deleteById(ID id);
}
