package org.cplcursos.java.Servicios;

import org.cplcursos.java.Entidades.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteSrvc {
    List<Cliente> listaCli(Integer num);
    Optional<Cliente> porId(Integer id);
    void guardar(Cliente cli);
    void eliminar(Integer id);
}
