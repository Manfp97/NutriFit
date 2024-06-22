package org.cplcursos.java.Repositorios;

import jakarta.persistence.EntityManager;
import org.cplcursos.java.Entidades.SesionClientes;

import java.util.List;

public class CitaRepoImpl implements Repo<SesionClientes>{
    private EntityManager em;

    public CitaRepoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<SesionClientes> listar(Integer num) {
        return em.createQuery("SELECT  FROM Cita", SesionClientes.class)
                .setMaxResults(num)
                .getResultList();
    }

    @Override
    public SesionClientes porId(Integer id) {
        return em.find(SesionClientes.class, id);
    }

    @Override
    public void guardar(SesionClientes claseClientes) {
        try {
            em.getTransaction().begin();
            if (claseClientes.getId() != null && claseClientes.getId() > 0) {
                em.merge(claseClientes);
            } else {
                em.persist(claseClientes);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(Integer id) {

    }
}