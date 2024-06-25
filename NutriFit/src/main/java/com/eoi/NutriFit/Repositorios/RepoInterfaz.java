package com.eoi.NutriFit.Repositorios;


import java.util.List;
import java.util.Optional;

public interface RepoInterfaz<T> {

    List<T> findAll(Integer num);
    T findBy(Integer id);
    void save(T entity);
    void delete(Integer id);
}
