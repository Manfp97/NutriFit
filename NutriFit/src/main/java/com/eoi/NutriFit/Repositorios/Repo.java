package com.eoi.NutriFit.Repositorios;


import java.util.List;

public interface Repo<T> {

    List<T> findAll(Integer num);
    T findBy(Integer id);
    void save(T entity);
    void delete(Integer id);
}
