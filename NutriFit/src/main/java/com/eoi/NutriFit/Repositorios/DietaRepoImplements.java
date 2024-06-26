package com.eoi.NutriFit.Repositorios;

import com.eoi.NutriFit.Entidades.Dieta;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class DietaRepoImplements implements Repo<Dieta> {

    private EntityManager em;

    public DietaRepoImplements(EntityManager em) {this.em = em;}

    @Override
    public List<Dieta> list(Integer num) {
        return em.createQuery("SELECT d FROM Dieta d", Dieta.class)
                .setFirstResult(num)
                .getResultList();
    }

    @Override
    public Dieta findby(Integer id) {
        return em.find(Dieta.class, id);
    }

    @Override
    public void save(Dieta dieta) {
        try {
            em.getTransaction().begin();
            if (dieta.getId() != null && dieta.getId() > 0) {
                em.merge(dieta);
            } else {
                em.persist(dieta);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            em.getTransaction().begin();
            Dieta dieta = em.find(Dieta.class, id);
            if (dieta != null) {
                em.remove(dieta);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}
