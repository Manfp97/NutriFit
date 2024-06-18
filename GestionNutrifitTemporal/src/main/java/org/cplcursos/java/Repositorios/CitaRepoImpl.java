package org.cplcursos.java.Repositorios;

import jakarta.persistence.EntityManager;
import org.cplcursos.java.Entidades.Clase_Clientes;

import java.util.List;

public class CitaRepoImpl implements Repo<Clase_Clientes>{
    private EntityManager em;

    public CitaRepoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Clase_Clientes> listar(Integer num) {
        return em.createQuery("SELECT  FROM Cita", Clase_Clientes.class)
                .setMaxResults(num)
                .getResultList();
    }

    @Override
    public Clase_Clientes porId(Integer id) {
        return em.find(Clase_Clientes.class, id);
    }

    @Override
    public void guardar(Clase_Clientes claseClientes) {
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