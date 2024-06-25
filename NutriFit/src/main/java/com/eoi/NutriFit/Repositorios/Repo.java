package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.Cliente;

import java.util.List;
import java.util.Optional;

public interface Repo<T> {
    List<T> list(Integer num);
    T findby(Integer id);
    void save(T t);
    void delete(Integer id);

}
