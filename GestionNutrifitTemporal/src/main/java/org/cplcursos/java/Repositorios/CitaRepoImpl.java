package org.cplcursos.java.Repositorios;

import jakarta.persistence.EntityManager;
import org.cplcursos.java.Entidades.Albaran;
import org.cplcursos.java.Entidades.Cita;

import java.util.List;

public class CitaRepoImpl implements Repo<Cita>{
    private EntityManager em;

    public CitaRepoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Cita> listar(Integer num) {
        return em.createQuery("SELECT  FROM Cita", Cita.class)
                .setMaxResults(num)
                .getResultList();
    }

    @Override
    public Cita porId(Integer id) {
        return em.find(Cita.class, id);
    }

    @Override
    public void guardar(Cita cita) {
        try {
            em.getTransaction().begin();
            if (cita.getId() != null && cita.getId() > 0) {
                em.merge(cita);
            } else {
                em.persist(cita);
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