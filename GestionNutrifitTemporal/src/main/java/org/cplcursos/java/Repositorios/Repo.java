package org.cplcursos.java.Repositorios;

import java.util.List;

public interface Repo<T> {
    List<T> listar(Integer num);
    T porId(Integer id);
    void guardar(T t);
    void eliminar(Integer id);
}
