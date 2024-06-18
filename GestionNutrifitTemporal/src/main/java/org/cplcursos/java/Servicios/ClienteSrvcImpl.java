package org.cplcursos.java.Servicios;

import jakarta.persistence.EntityManager;
import org.cplcursos.java.Repositorios.ClienteRepoImpl;
import org.cplcursos.java.Repositorios.Repo;
import org.cplcursos.java.Entidades.Cliente;

import java.util.List;
import java.util.Optional;

public class ClienteSrvcImpl implements ClienteSrvc{
    private EntityManager em;
    private Repo<Cliente> repoCli;

    public ClienteSrvcImpl(EntityManager em) {
        this.em = em;
        this.repoCli = new ClienteRepoImpl(em);
    }

    @Override
    public List<Cliente> listaCli(Integer num) {
        return  repoCli.listar(num);
    }

    @Override
    public Optional<Cliente> porId(Integer id) {
        return Optional.ofNullable(repoCli.porId(id));
    }

    @Override
    public void guardar(Cliente cli) {
        repoCli.guardar(cli);
    }

    @Override
    public void eliminar(Integer id) {
        repoCli.eliminar(id);
    }
}
