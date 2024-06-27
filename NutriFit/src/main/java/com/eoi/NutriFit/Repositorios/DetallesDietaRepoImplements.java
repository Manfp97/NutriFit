package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.DetallesDieta;
import jakarta.persistence.EntityManager;

import java.util.List;

public class DetallesDietaRepoImplements implements Repo<DetallesDieta> {

    private EntityManager em;

    public DetallesDietaRepoImplements(EntityManager em) {this.em = em;}

    @Override
    public List<DetallesDieta> list(Integer num) {
        return em.createQuery("SELECT d FROM DetallesDieta d", DetallesDieta.class)
                .setFirstResult(num)
                .getResultList();
    }

    @Override
    public DetallesDieta findby(Integer id) {
        return em.find(DetallesDieta.class, id);
    }

    @Override
    public void save(DetallesDieta detallesDieta) {
        try{
            em.getTransaction().begin();
            if (detallesDieta.getId() != null && detallesDieta.getId() > 0) {
                em.merge(detallesDieta);
            } else {
                em.persist(detallesDieta);
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
            DetallesDieta detallesDieta = findby(id);
            if (detallesDieta != null) {
                em.remove(detallesDieta);
            }
            em.getTransaction().commit();
        }  catch (Exception ex) {
            em.getTransaction().rollback();
            ex.printStackTrace();
        }
    }
}
