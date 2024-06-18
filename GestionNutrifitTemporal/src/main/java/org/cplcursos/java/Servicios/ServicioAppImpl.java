package org.cplcursos.java.Servicios;

import jakarta.persistence.EntityManager;
import org.cplcursos.java.Entidades.Albaran;
import org.cplcursos.java.Entidades.Clase_Clientes;
import org.cplcursos.java.Entidades.Cliente;
import org.cplcursos.java.Repositorios.AlabranRepoImpl;
import org.cplcursos.java.Repositorios.CitaRepoImpl;
import org.cplcursos.java.Repositorios.ClienteRepoImpl;
import org.cplcursos.java.Repositorios.Repo;

import java.util.List;
import java.util.Optional;

public class ServicioAppImpl implements ServicioApp{
    private Repo<Cliente> repoCli;
    private Repo<Albaran> repoAlb;
    private Repo<Clase_Clientes> repoCita;

    public ServicioAppImpl(EntityManager em) {
        this.repoCli = new ClienteRepoImpl(em);
        this.repoAlb = new AlabranRepoImpl(em);
        this.repoCita = new CitaRepoImpl(em);
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

    @Override
    public List<Albaran> listaAlb(Integer num) {
        return repoAlb.listar(num);
    }

    @Override
    public Optional<Albaran> porIdAlbaran(Integer id) {
        return Optional.ofNullable(repoAlb.porId(id));
    }

    @Override
    public void guardarAlb(Albaran alb) {
        repoAlb.guardar(alb);
    }

    @Override
    public void eliminarAlb(Integer id) {
        repoAlb.eliminar(id);
    }

    @Override
    public Optional<Clase_Clientes> porIdCita(Integer id) {
        return Optional.ofNullable(repoCita.porId(id));
    }

    @Override
    public void guardarCita(Clase_Clientes claseClientes) {
        repoCita.guardar(claseClientes);
    }
}