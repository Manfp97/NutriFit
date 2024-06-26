package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.DetalleUsuario;
import jakarta.persistence.EntityManager;

import java.util.List;

public class DetalleUsuarioRepoImplements implements Repo<DetalleUsuario> {

    private EntityManager em;

    public DetalleUsuarioRepoImplements(EntityManager em) {this.em = em;}

    @Override
    public List<DetalleUsuario> list(Integer num) {
        return em.createQuery("SELECT a FROM DetalleUsuario a", DetalleUsuario.class)
                .setMaxResults(num)
                .getResultList();
    }

    @Override
    public DetalleUsuario findby(Integer id) {
        return em.find(DetalleUsuario.class, id);

    }

    @Override
    public void save(DetalleUsuario detalleUsuario) {
        try {
            em.getTransaction().begin();
            if (detalleUsuario.getId() != null && detalleUsuario.getId() > 0) {
                em.merge(detalleUsuario);
            } else {
                em.persist(detalleUsuario);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            em.getTransaction().begin();
            DetalleUsuario detalleUsuario = em.find(DetalleUsuario.class, id);
            if (detalleUsuario != null) {
                em.remove(detalleUsuario);
                em.getTransaction().commit();
            }
        } catch (Exception ex) {
            em.getTransaction().rollback();
            ex.printStackTrace();
        }
    }
}
